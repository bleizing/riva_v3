package bleizing.riva.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;

import bleizing.riva.R;
import bleizing.riva.fragment.BookingFragment;
import bleizing.riva.fragment.DetailRumatFragment;
import bleizing.riva.fragment.EdukasiFragment;
import bleizing.riva.fragment.KakiFragment;
import bleizing.riva.fragment.PaymentFragment;
import bleizing.riva.fragment.PemesananFragment;
import bleizing.riva.fragment.RegistrasiFragment;
import bleizing.riva.fragment.RumatFragment;
import bleizing.riva.model.Model;

public class RumatActivity extends AppCompatActivity {
    private static final String TAG = "RumatActivity";

    private static final String FRAGMENT_DETAIL_RUMAT_TAG = "DetailRumatFragment";
    private static final String FRAGMENT_BOOKING_TAG = "BookingFragment";
    private static final String FRAGMENT_REGISTRASI_TAG = "RegistrasiFragment";
    private static final String FRAGMENT_PAYMENT_TAG = "PaymentFragment";
    private static final String FRAGMENT_PEMESANAN_TAG = "PemesananFragment";

    private final static int REQUEST_CHECK_SETTINGS = 0x1;

    private FusedLocationProviderClient mFusedLocationClient;

    private String last_title;

    private HashMap<Integer, String> hashMapTitle;

    private int countFragment = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rumat);

        hashMapTitle = new HashMap<>();

        last_title = "";

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_biru_biru));
        actionBar.setTitle("LOKASI");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Model.setRumatActivity(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        displayLocationSettingsRequest(this);

        if (savedInstanceState == null) {
            RumatFragment rumatFragment = new RumatFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, rumatFragment, TAG).commit();
        }

//        String url_article_rumat = "https://www.rumat-indonesia.com/menu/JABODETABEK.html";
//
//        WebView webView = (WebView) findViewById(R.id.webview);
//
//        WebSettings webSettings = webView.getSettings();
//
//        webSettings.setJavaScriptEnabled(true);
//
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.clearCache(true);
//        webView.clearHistory();
//        webView.loadUrl(url_article_rumat);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goBack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private void goBack() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
//            setActionBarTitle(last_title);

            countFragment--;
            setActionBarTitle(hashMapTitle.get(countFragment));
        } else {
            // app icon in action bar clicked; go home
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Model.clearRumatActivity();
    }

    public void changeToDetailRumatFragment() {
        last_title = getSupportActionBar().getTitle().toString();

        hashMapTitle.put(countFragment, last_title);
        countFragment++;

        DetailRumatFragment detailRumatFragment = new DetailRumatFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, detailRumatFragment, FRAGMENT_DETAIL_RUMAT_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeToRegistrasiFragment(View view) {
        last_title = getSupportActionBar().getTitle().toString();

        hashMapTitle.put(countFragment, last_title);
        countFragment++;

        RegistrasiFragment registrasiFragment = new RegistrasiFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, registrasiFragment, FRAGMENT_REGISTRASI_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeToBookingFragment() {
        last_title = getSupportActionBar().getTitle().toString();

        hashMapTitle.put(countFragment, last_title);
        countFragment++;

        BookingFragment bookingFragment = new BookingFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, bookingFragment, FRAGMENT_BOOKING_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeToPaymentFragment() {
        last_title = getSupportActionBar().getTitle().toString();

        hashMapTitle.put(countFragment, last_title);
        countFragment++;

        PaymentFragment paymentFragment = new PaymentFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, paymentFragment, FRAGMENT_PAYMENT_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeToPemesananFragment() {
        last_title = getSupportActionBar().getTitle().toString();

        hashMapTitle.put(countFragment, last_title);
        countFragment++;

        PemesananFragment pemesananFragment = new PemesananFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, pemesananFragment, FRAGMENT_PEMESANAN_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
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
                            status.startResolutionForResult(RumatActivity.this, REQUEST_CHECK_SETTINGS);
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
                }
            }
        });
    }
}
