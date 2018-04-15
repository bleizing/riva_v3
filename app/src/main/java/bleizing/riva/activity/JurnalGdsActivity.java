package bleizing.riva.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;

import bleizing.riva.R;
import bleizing.riva.fragment.EdukasiFragment;
import bleizing.riva.fragment.GrafikFragment;
import bleizing.riva.fragment.PengingatFragment;

public class JurnalGdsActivity extends AppCompatActivity {
    private static final String TAG = "JurnalGdsActivity";

    private static final String FRAGMENT_GRAFIK_TAG = "GrafikFragment";
    private static final String FRAGMENT_PENGINGAT_TAG = "PengingatFragment";

    private String last_title;

    private HashMap<Integer, String> hashMapTitle;

    private int countFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jurnal_gds);

        hashMapTitle = new HashMap<>();

        last_title = "";

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_biru_biru));
        actionBar.setTitle("DIABETIC JOURNAL");
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            GrafikFragment grafikFragment = new GrafikFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, grafikFragment, FRAGMENT_GRAFIK_TAG).commit();
        }

        LinearLayout linear_grafik = (LinearLayout) findViewById(R.id.linear_grafik);
        linear_grafik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GrafikFragment grafikFragment = (GrafikFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_GRAFIK_TAG);

                if (grafikFragment == null || !grafikFragment.isInLayout()) {
                    grafikFragment = new GrafikFragment();
                }

                if (!grafikFragment.isVisible()) {
                    last_title = getSupportActionBar().getTitle().toString();

                    hashMapTitle.put(countFragment, last_title);
                    countFragment++;

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, grafikFragment, FRAGMENT_GRAFIK_TAG);
                    transaction.commit();
                }
            }
        });

        LinearLayout linear_pengingat = (LinearLayout) findViewById(R.id.linear_pengingat);
        linear_pengingat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PengingatFragment pengingatFragment = (PengingatFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_PENGINGAT_TAG);

                if (pengingatFragment == null || !pengingatFragment.isInLayout()) {
                    pengingatFragment = new PengingatFragment();
                }

                if (!pengingatFragment.isVisible()) {
                    last_title = getSupportActionBar().getTitle().toString();

                    hashMapTitle.put(countFragment, last_title);
                    countFragment++;

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, pengingatFragment, FRAGMENT_PENGINGAT_TAG);
                    transaction.commit();
                }
            }
        });
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

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
