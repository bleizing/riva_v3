package bleizing.riva.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.HashMap;

import bleizing.riva.R;
import bleizing.riva.fragment.ArticleFragment;
import bleizing.riva.fragment.BonusReferensiFragment;
import bleizing.riva.fragment.DetailArticleFragment;

public class BonusReferensiActivity extends AppCompatActivity {
    private static final String TAG = "BonusReferensi";

    private HashMap<Integer, String> hashMapTitle;

    private int countFragment = 0;

    private String last_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus_referensi);

        hashMapTitle = new HashMap<>();

        last_title = "";

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_biru_biru));
        actionBar.setTitle(getResources().getString(R.string.bonus_refferal));
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            BonusReferensiFragment bonusReferensiFragment = new BonusReferensiFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, bonusReferensiFragment, TAG).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
//                    setActionBarTitle(last_title);

                    countFragment--;
                    setActionBarTitle(hashMapTitle.get(countFragment));
                } else {
                    // app icon in action bar clicked; go home
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
