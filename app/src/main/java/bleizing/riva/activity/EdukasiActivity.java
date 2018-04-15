package bleizing.riva.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.HashMap;

import bleizing.riva.R;
import bleizing.riva.fragment.ArticleFragment;
import bleizing.riva.fragment.BonusReferensiFragment;
import bleizing.riva.fragment.DetailArticleFragment;
import bleizing.riva.fragment.DetailLukaFragment;
import bleizing.riva.fragment.EdukasiFragment;
import bleizing.riva.fragment.KakiFragment;
import bleizing.riva.fragment.LukaFragment;

public class EdukasiActivity extends AppCompatActivity {
    private static final String TAG = "EdukasiActivity";

    private static final String FRAGMENT_ARTICLE_TAG = "ArticleFragment";
    private static final String FRAGMENT_DETAIL_ARTICLE_TAG = "DetailArticleFragment";
    private static final String FRAGMENT_LUKA_TAG = "LukaFragment";
    private static final String FRAGMENT_DETAIL_LUKA_TAG = "DetailLukaFragment";
    private static final String FRAGMENT_KAKI_TAG = "KakiFragment";
    private static final String FRAGMENT_DETAIL_KAKI_TAG = "DetailKakiFragment";

    private HashMap<Integer, String> hashMapTitle;

    private int countFragment = 0;

    private String last_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edukasi);

        hashMapTitle = new HashMap<>();

        last_title = "";

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_biru_biru));
        actionBar.setTitle(getResources().getString(R.string.untuk_anda));
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            EdukasiFragment edukasiFragment = new EdukasiFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, edukasiFragment, TAG).commit();
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

    public void changeToArticleFragment() {
        last_title = getSupportActionBar().getTitle().toString();

        hashMapTitle.put(countFragment, last_title);
        countFragment++;

        ArticleFragment articleFragment = new ArticleFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, articleFragment, FRAGMENT_ARTICLE_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeToDetailArticleFragment(int article_id) {
        last_title = getSupportActionBar().getTitle().toString();

        hashMapTitle.put(countFragment, last_title);
        countFragment++;

        DetailArticleFragment detailArticleFragment = new DetailArticleFragment();
        Bundle args = new Bundle();
        args.putInt("article_id", article_id);
        detailArticleFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, detailArticleFragment, FRAGMENT_DETAIL_ARTICLE_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeToLukaFragment() {
        last_title = getSupportActionBar().getTitle().toString();

        hashMapTitle.put(countFragment, last_title);
        countFragment++;

        LukaFragment lukaFragment = new LukaFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, lukaFragment, FRAGMENT_LUKA_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeToDetailLukaFragment(int i) {
        last_title = getSupportActionBar().getTitle().toString();

        hashMapTitle.put(countFragment, last_title);
        countFragment++;

        DetailLukaFragment detailLukaFragment = new DetailLukaFragment();
        Bundle args = new Bundle();
        args.putInt("luka", i);
        detailLukaFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, detailLukaFragment, FRAGMENT_DETAIL_LUKA_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeToKakiFragment() {
        last_title = getSupportActionBar().getTitle().toString();

        hashMapTitle.put(countFragment, last_title);
        countFragment++;

        KakiFragment kakiFragment = new KakiFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, kakiFragment, FRAGMENT_KAKI_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
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
}
