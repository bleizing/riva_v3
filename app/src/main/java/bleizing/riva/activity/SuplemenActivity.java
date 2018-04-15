package bleizing.riva.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

import bleizing.riva.R;

public class SuplemenActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private SliderLayout sliderLayout;
    private HashMap<String,String> hashFileMaps;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suplemen);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_biru_biru));
//        actionBar.setTitle("SUPLEMEN");
//        actionBar.setDisplayHomeAsUpEnabled(true);

        setSupportActionBar(toolbar);
        setTitle("SUPLEMEN");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_biru_biru));

        hashFileMaps = new HashMap<String, String>();

        sliderLayout = (SliderLayout)findViewById(R.id.slider);

        hashFileMaps.put("Supplement 1", "https://cdn-a.william-reed.com/var/wrbm_gb_food_pharma/storage/images/5/9/1/7/2827195-1-eng-GB/UK-on-diabetic-supplement-Blood-sugar-link-ok-but-not-diabetes.jpg");
        hashFileMaps.put("Supplement 2", "http://www.healingtherapies.info/DiabetesSupplements2.jpg");
        hashFileMaps.put("Supplement 3", "http://www.bionova.co.in/images/nutraceuticals/020.jpg");
        hashFileMaps.put("Supplement 4", "http://britishbiologicals.com/BBAdmin/Uploads/b-protin-banner201521919421960.png");
        hashFileMaps.put("Supplement 5", "https://5.imimg.com/data5/RO/NN/MY-15577004/protein-powder-500x500.jpg");

        for(String name : hashFileMaps.keySet()){

            TextSliderView textSliderView = new TextSliderView(SuplemenActivity.this);
            textSliderView
//                    .description(name)
                    .image(hashFileMaps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(5000);
        sliderLayout.addOnPageChangeListener(this);
    }

    @Override
    protected void onStop() {

        sliderLayout.stopAutoCycle();

        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

//        Toast.makeText(this,slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {

        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

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
        } else {
            // app icon in action bar clicked; go home
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
