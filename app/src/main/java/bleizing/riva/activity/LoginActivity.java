package bleizing.riva.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.HashMap;

import bleizing.riva.R;
import bleizing.riva.fragment.EdukasiFragment;
import bleizing.riva.fragment.LoginFragment;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private String last_title;

    private HashMap<Integer, String> hashMapTitle;

    private int countFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        hashMapTitle = new HashMap<>();

        last_title = "";

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_biru_biru));
        actionBar.setTitle("LOGIN");
//        actionBar.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            LoginFragment loginFragment = new LoginFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, loginFragment, TAG).commit();
        }
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
//            Intent intent = new Intent(this, HomeActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
            finish();
        }
    }
}
