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

public class DressingActivity extends AppCompatActivity implements
        BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private SliderLayout sliderLayout;
    private HashMap<String,String> hashFileMaps;

    private String last_title;

    private Toolbar toolbar;

    private HashMap<Integer, String> hashMapTitle;

    private int countFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dressing);

        hashMapTitle = new HashMap<>();

        last_title = "";

        toolbar = (Toolbar) findViewById(R.id.toolbar);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_biru_biru));
//        actionBar.setTitle("DRESSING");
//        actionBar.setDisplayHomeAsUpEnabled(true);

        setSupportActionBar(toolbar);
        setTitle("DRESSING");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_biru_biru));

        hashFileMaps = new HashMap<String, String>();

        sliderLayout = (SliderLayout)findViewById(R.id.slider);

        hashFileMaps.put("Diabetic Tools 1", "https://www.longevitynetwork.org/wp-content/uploads/2015/06/iStock_Diabetic-supplies-000001225308XSmall.jpg");
        hashFileMaps.put("Diabetic Tools 2", "http://www.healthmagazine.ae/wp-content/uploads/2013/09/Diabetic-Supplies.jpg");
        hashFileMaps.put("Diabetic Tools 3", "https://i.pinimg.com/736x/99/4a/21/994a21bb2ba4f6656edf329bda7064dd--diabetic-living-ice-packs.jpg");
        hashFileMaps.put("Diabetic Tools 4", "http://www.desang.net/wp-content/uploads/2010/10/5-kitbag.jpg");
        hashFileMaps.put("Diabetic Tools 5", "https://www.diabeticpick.com/media/catalog/product/cache/1/thumbnail/600x600/9df78eab33525d08d6e5fb8d27136e95/a/c/accu-chek-active-glucometer.jpg");

        for(String name : hashFileMaps.keySet()){

            TextSliderView textSliderView = new TextSliderView(DressingActivity.this);
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
}
