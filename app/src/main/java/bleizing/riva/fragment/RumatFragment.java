package bleizing.riva.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bleizing.riva.MyClickListener;
import bleizing.riva.R;
import bleizing.riva.RecyclerTouchListener;
import bleizing.riva.activity.RumatActivity;
import bleizing.riva.adapter.RumatAdapter;
import bleizing.riva.model.Lokasi;
import bleizing.riva.model.Model;
import bleizing.riva.model.NETApi;
import bleizing.riva.onGPSEnabled;

/**
 * A simple {@link Fragment} subclass.
 */
public class RumatFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        onGPSEnabled {

    private static final String TAG = "RumatFragment";

    GoogleMap mMap;
//    private Double lat, lng;

//    private LocationManager locationManager;

    private LatLng latLng;

    private ArrayList<Lokasi> lokasiArrayList;
    private RumatAdapter rumatAdapter;

    private RequestQueue requestQueue;

    public RumatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        lat = -6.175206;
//        lng = 106.827131;
//
//        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//        getCurrentLocation();

        lokasiArrayList = Model.getLokasiArrayList();
        if (lokasiArrayList == null) {
            lokasiArrayList = new ArrayList<>();

            requestQueue = Volley.newRequestQueue(getContext());

            getLokasiRumatList();
        }

        latLng = Model.getLatLng();
        if (latLng == null) {
            latLng = new LatLng(-6.175206,106.827131);
        }

        ((RumatActivity) getActivity()).setOnGPSEnabled(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rumat, container, false);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SupportMapFragment fragment = new SupportMapFragment();
        transaction.add(R.id.mapView, fragment);
        transaction.commit();
        fragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.rumat_recycler_view);
        rumatAdapter = new RumatAdapter(lokasiArrayList, getContext());
        recyclerView.setAdapter(rumatAdapter);
        rumatAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new MyClickListener() {
            @Override
            public void onClick(View view, int position) {
                Lokasi lokasi = rumatAdapter.getLokasiArrayList().get(position);
                ((RumatActivity) getActivity()).changeToDetailRumatFragment(lokasi);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

//        RelativeLayout relative_1 = (RelativeLayout) getActivity().findViewById(R.id.relative_1);
//        relative_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((RumatActivity) getActivity()).changeToDetailRumatFragment();
//            }
//        });
//
//        RelativeLayout relative_2 = (RelativeLayout) getActivity().findViewById(R.id.relative_2);
//        relative_2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((RumatActivity) getActivity()).changeToDetailRumatFragment();
//            }
//        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        if (lokasiArrayList.size() != 0) {
            for (Lokasi l : lokasiArrayList) {
                LatLng latLng = new LatLng(l.getLat(), l.getLng());
                mMap.addMarker(new MarkerOptions().position(latLng).title(l.getNama()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }
        }

        setCenterPoint();

//        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker);

//        // Add a marker in Sydney, Australia, and move the camera.
//        LatLng b1 = new LatLng(-6.179158, 106.876802);
//        mMap.addMarker(new MarkerOptions().position(b1).title("Jual Baju Second").icon(icon));
//
//        LatLng b2 = new LatLng(-6.180150, 106.875021);
//        mMap.addMarker(new MarkerOptions().position(b2).title("Mobil").icon(icon));
//
//        LatLng b3 = new LatLng(-6.176712, 106.873415);
//        mMap.addMarker(new MarkerOptions().position(b3).title("Motor").icon(icon));
//
//        LatLng b4 = new LatLng(-6.175871, 106.876963);

//        mMap.addMarker(new MarkerOptions().position(b4).title("Kerete Bayi").icon(icon));

//        mMap.moveCamera(CameraUpdateFactory.newLatLng(b1));
//        updateBarangLokasi();

//        mMap.addMarker(new MarkerOptions().position(b3).title("Rumat").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
//        mMap.addMarker(new MarkerOptions().position(b4).title("Rumat").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        mMap.setMinZoomPreference(10.0f);
        mMap.setMaxZoomPreference(20.0f);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
//        lat = location.getLatitude();
//        lng = location.getLongitude();
    }

//    private void getCurrentLocation() {
//        if (locationManager != null) {
//            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (location != null) {
//                lat = location.getLatitude();
//                lng = location.getLongitude();
//
////                getAddress();
//            }
//        }
//        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, locationListener);
//        }
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, locationListener);
//    }

//    private android.location.LocationListener locationListener = new android.location.LocationListener() {
//        @Override
//        public void onLocationChanged(Location location) {
//            lat = location.getLatitude();
//            lng = location.getLongitude();
//
////            getAddress();
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(intent);
//        }
//    };

    private void setCenterPoint() {
        if (mMap != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera( CameraUpdateFactory.zoomTo( 12.0f ) );
        }
    }

    @Override
    public void onEnabled() {
        if (mMap != null) {
            latLng = Model.getLatLng();
            setCenterPoint();
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