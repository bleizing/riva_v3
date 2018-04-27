package bleizing.riva.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bleizing.riva.R;
import bleizing.riva.model.Lokasi;
import bleizing.riva.model.Model;
import bleizing.riva.model.NETApi;

public class PilihLokasiActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = PilihLokasiActivity.class.getSimpleName();

    private final static int REQUEST_CHECK_SETTINGS = 0x1;

    private FusedLocationProviderClient mFusedLocationClient;

    private GoogleMap mMap;
    private Double lat;
    private Double lng;

    private String lokasiRumat;

    private Button btnPilihLokasi;

    private ArrayList<Lokasi> lokasiArrayList;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_lokasi);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        lokasiRumat = "";

        lokasiArrayList = Model.getLokasiArrayList();
        if (lokasiArrayList == null) {
            Log.d(TAG, "lokasiArrayList == null");
            lokasiArrayList = new ArrayList<>();

            requestQueue = Volley.newRequestQueue(this);

            getLokasiRumatList();
        }

        btnPilihLokasi = (Button) findViewById(R.id.btnPilihLokasi);
        btnPilihLokasi.setEnabled(false);
        btnPilihLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
//                String latlng = lat + "," + lng;
                intent.setData(Uri.parse(lokasiRumat));
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        lat = 0.0;
        lng = 0.0;

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        displayLocationSettingsRequest(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setCenterPoint();

        if (lokasiArrayList.size() != 0) {
            for (Lokasi l : lokasiArrayList) {
                LatLng latLng = new LatLng(l.getLat(), l.getLng());
                mMap.addMarker(new MarkerOptions().position(latLng).title(l.getNama()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.isInfoWindowShown()) {
                    marker.hideInfoWindow();
                } else {
                    marker.showInfoWindow();
                }

                lokasiRumat = marker.getTitle();

//                lat = marker.getPosition().latitude;
//                lng = marker.getPosition().longitude;

                btnPilihLokasi.setEnabled(true);

                return true;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
//                lat = 0.0;
//                lng = 0.0;

                btnPilihLokasi.setEnabled(false);
            }
        });
    }

    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                Log.d(TAG, result.toString());
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        startLocationUpdates();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(PilihLokasiActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    // Logic to handle location object
                    Log.d(TAG, "lat = " + location.getLatitude() + ", lng = " + location.getLongitude());
                    lat = location.getLatitude();
                    lng = location.getLongitude();

                    setCenterPoint();
                }
            }
        });
    }

    private void setCenterPoint() {
        if (mMap != null) {
            if (lat != 0.0 && lng != 0.0) {
                LatLng latLng = new LatLng(lat, lng);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera( CameraUpdateFactory.zoomTo( 13.0f ) );
            }
        }
    }

    private void getLokasiRumatList() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, NETApi.BASE_URL + NETApi.GET_LOKASI_RUMAT +  NETApi.ID_USER + "=1", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("parameter");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString("id");
                        String nama = jsonObject.getString("nama");
                        String alamat = jsonObject.getString("alamat");
                        String latString = jsonObject.optString("lat");
                        String lngString = jsonObject.optString("lng");
                        String type = jsonObject.optString("type");
                        String noTelp = jsonObject.getString("telp");

                        if (noTelp.split("/").length != 0) {
                            noTelp = noTelp.split("/")[0];
                        }

                        if (noTelp.split(" ").length != 0) {
                            String noTelpArr[] = noTelp.split(" ");
                            noTelp = "";
                            for (String aNoTelpArr : noTelpArr) {
                                noTelp += aNoTelpArr;
                            }
                        }

                        if (!latString.equals("null") && !lngString.equals("null")) {
                            Double lat = Double.parseDouble(latString);
                            Double lng = Double.parseDouble(lngString);

                            Lokasi lokasi = new Lokasi(Integer.parseInt(id), nama, alamat, lat, lng, type, noTelp);
                            lokasiArrayList.add(lokasi);
                        }
                    }
                    Model.setLokasiArrayList(lokasiArrayList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        jsonObjectRequest.setTag(TAG);
        requestQueue.add(jsonObjectRequest);
    }
}
