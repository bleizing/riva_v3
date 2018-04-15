package bleizing.riva.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import bleizing.riva.R;
import bleizing.riva.fragment.ArticleFragment;
import bleizing.riva.fragment.DetailTrainingFragment;
import bleizing.riva.fragment.TrainingFragment;

public class TrainingActivity extends AppCompatActivity {
    private static final String TAG = "TrainingActivity";

    private static final String FRAGMENT_DETAIL_TRAINING_TAG = "DetailTrainingFragment";

    private String last_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        frameLayout.setVisibility(View.GONE);

        last_title = "";

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_biru_biru));
        actionBar.setTitle(getResources().getString(R.string.untuk_anda));
        actionBar.setDisplayHomeAsUpEnabled(true);

        RelativeLayout relative_1 = (RelativeLayout) findViewById(R.id.relative_1);
        relative_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToDetailTrainingFragment();
            }
        });

        RelativeLayout relative_2 = (RelativeLayout) findViewById(R.id.relative_2);
        relative_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToDetailTrainingFragment();
            }
        });

//        if (savedInstanceState == null) {
//            TrainingFragment trainingFragment = new TrainingFragment();
//            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, trainingFragment, TAG).commit();
//        }
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
            setActionBarTitle(last_title);
        } else {

            LinearLayout linear_training = (LinearLayout) findViewById(R.id.linear_training);

            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fragment_container);

            if (linear_training.getVisibility() == View.VISIBLE && frameLayout.getVisibility() == View.GONE) {
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                linear_training.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
            }
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

//    public void changeToArticleFragment() {
//        last_title = getSupportActionBar().getTitle().toString();
//
//        ArticleFragment articleFragment = new ArticleFragment();
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container, articleFragment, FRAGMENT_DETAIL_TRAINING_TAG);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }

    public void changeToDetailTrainingFragment() {
        last_title = getSupportActionBar().getTitle().toString();

        LinearLayout linear_training = (LinearLayout) findViewById(R.id.linear_training);
        linear_training.setVisibility(View.GONE);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        frameLayout.setVisibility(View.VISIBLE);
//
        DetailTrainingFragment detailTrainingFragment = new DetailTrainingFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, detailTrainingFragment, TAG).commit();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container, detailTrainingFragment, FRAGMENT_DETAIL_TRAINING_TAG);
//        transaction.addToBackStack(null);
//        transaction.commit();
    }
}
