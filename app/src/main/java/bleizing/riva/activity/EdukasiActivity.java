package bleizing.riva.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import bleizing.riva.R;
import bleizing.riva.fragment.ArticleFragment;
import bleizing.riva.fragment.BonusReferensiFragment;
import bleizing.riva.fragment.DetailArticleFragment;
import bleizing.riva.fragment.DetailLukaFragment;
import bleizing.riva.fragment.EdukasiFragment;
import bleizing.riva.fragment.KakiFragment;
import bleizing.riva.fragment.LukaFragment;
import bleizing.riva.model.Artikel;
import bleizing.riva.model.Model;
import bleizing.riva.model.NETApi;

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

    private ArrayList<Artikel> artikelArrayList;

    private RequestQueue requestQueue;

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

        requestQueue = Volley.newRequestQueue(this);

        artikelArrayList = Model.getArtikelArrayList();
        if (artikelArrayList == null) {
            artikelArrayList = new ArrayList<>();

            getArticles();
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

    public void changeToDetailArticleFragment(Artikel artikel) {
        last_title = getSupportActionBar().getTitle().toString();

        hashMapTitle.put(countFragment, last_title);
        countFragment++;

        DetailArticleFragment detailArticleFragment = new DetailArticleFragment();
        Bundle args = new Bundle();
        args.putParcelable("article", artikel);
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

    private void getArticles() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, NETApi.BASE_URL + NETApi.GET_ARTICLE + NETApi.ID_USER + "1", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "getArticles response = " + response.toString());

                try {
                    JSONArray parameter = response.getJSONArray("parameter");

                    for(int i = 0; i < parameter.length(); i++) {
                        JSONObject jsonObject = parameter.getJSONObject(i);

                        int id = jsonObject.getInt("id");
                        String judul = jsonObject.getString("judul");
                        String isi = jsonObject.getString("txt");
                        String foto = jsonObject.optString("foto", "");
                        String created = jsonObject.getString("created_at");

                        String creates[] = created.split(" ");
                        String screates[] = creates[0].split("-");

                        String tanggal = screates[2];
                        String bulan = convertMonth(screates[1]);

                        Artikel artikel = new Artikel(id, judul, isi, foto, tanggal, bulan);

                        artikelArrayList.add(artikel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Model.setArtikelArrayList(artikelArrayList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        jsonObjectRequest.setTag(TAG);
        requestQueue.add(jsonObjectRequest);
    }

    private String convertMonth(String month) {
        String bulan = "";
        switch (month) {
            case "01":
                bulan = "Januari";
                break;
            case "02":
                bulan = "Februari";
                break;
            case "03":
                bulan = "Maret";
                break;
            case "04":
                bulan = "April";
                break;
            case "05":
                bulan = "Mei";
                break;
            case "06":
                bulan = "Juni";
                break;
            case "07":
                bulan = "Juli";
                break;
            case "08":
                bulan = "Agustus";
                break;
            case "09":
                bulan = "September";
                break;
            case "10":
                bulan = "Oktober";
                break;
            case "11":
                bulan = "November";
                break;
            case "12":
                bulan = "Desember";
                break;

            default:
                bulan = "NaN";
                break;
        }

        return bulan.substring(0, 3);
    }
}
