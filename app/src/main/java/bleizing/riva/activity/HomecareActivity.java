package bleizing.riva.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.HashMap;

import bleizing.riva.R;
import bleizing.riva.fragment.ArticleFragment;
import bleizing.riva.fragment.BookingFragment;
import bleizing.riva.fragment.EdukasiFragment;
import bleizing.riva.fragment.HomecareFragment;
import bleizing.riva.fragment.PaymentFragment;
import bleizing.riva.fragment.PemesananFragment;
import bleizing.riva.fragment.RegistrasiFragment;
import bleizing.riva.model.Model;

public class HomecareActivity extends AppCompatActivity {
    private static final String TAG = "HomecareActivity";

    private static final String FRAGMENT_BOOKING_TAG = "BookingFragment";
    private static final String FRAGMENT_REGISTRASI_TAG = "RegistrasiFragment";
    private static final String FRAGMENT_PAYMENT_TAG = "PaymentFragment";
    private static final String FRAGMENT_PEMESANAN_TAG = "PemesananFragment";

    private String last_title;

    private Toolbar toolbar;

    private HashMap<Integer, String> hashMapTitle;

    private int countFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homecare);

        hashMapTitle = new HashMap<>();

        last_title = "";

        toolbar = (Toolbar) findViewById(R.id.toolbar);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_biru_biru));
//        actionBar.setTitle(getResources().getString(R.string.homecare));
//        actionBar.setDisplayHomeAsUpEnabled(true);

        setSupportActionBar(toolbar);
        setTitle(getResources().getString(R.string.homecare));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_biru_biru));

        Model.setHomecareActivity(this);

        if (savedInstanceState == null) {
            HomecareFragment homecareFragment = new HomecareFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, homecareFragment, TAG).commit();
        }
    }

    public void setActionBarTitle(String title) {
        toolbar.setTitle(title);
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

        Model.clearHomecareActivity();
    }

    public void changeToBookingFragment() {
        last_title = toolbar.getTitle().toString();

        hashMapTitle.put(countFragment, last_title);
        countFragment++;

        BookingFragment bookingFragment = new BookingFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, bookingFragment, FRAGMENT_BOOKING_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeToRegistrasiFragment() {
        last_title = toolbar.getTitle().toString();

        hashMapTitle.put(countFragment, last_title);
        countFragment++;

        RegistrasiFragment registrasiFragment = new RegistrasiFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, registrasiFragment, FRAGMENT_REGISTRASI_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeToPaymentFragment() {
        last_title = toolbar.getTitle().toString();

        hashMapTitle.put(countFragment, last_title);
        countFragment++;

        PaymentFragment paymentFragment = new PaymentFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, paymentFragment, FRAGMENT_PAYMENT_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeToPemesananFragment() {
        last_title = toolbar.getTitle().toString();

        hashMapTitle.put(countFragment, last_title);
        countFragment++;

        PemesananFragment pemesananFragment = new PemesananFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, pemesananFragment, FRAGMENT_PEMESANAN_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
