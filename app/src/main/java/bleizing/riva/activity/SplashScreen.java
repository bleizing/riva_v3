package bleizing.riva.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

import bleizing.riva.R;
import bleizing.riva.model.Lokasi;
import bleizing.riva.model.Model;
import bleizing.riva.model.NETApi;

public class SplashScreen extends AppCompatActivity {
    private static final String TAG = "SplashScreen";

    private final int SPLASH_TIME = 5000;

    private RequestQueue requestQueue;

    private ArrayList<Lokasi> lokasiArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        requestQueue = Volley.newRequestQueue(this);

        lokasiArrayList = new ArrayList<>();

        getLokasiRumatList();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME);
    }

    private void getLokasiRumatList() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, NETApi.BASE_URL + NETApi.GET_LOKASI_RUMAT +  NETApi.ID_USER + "1", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("parameter");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString("id");
                        String nama = jsonObject.getString("nama");
                        String alamat = jsonObject.getString("alamat");
                        String latString = jsonObject.optString("lat");
                        String lngString = jsonObject.optString("lng");
                        String type = jsonObject.optString("type");
                        String noTelp = "";
                        noTelp = jsonObject.optString("telp");

                        if (!noTelp.equals("")) {
                            if (noTelp.split("/").length != 0) {
                                noTelp = noTelp.split("/")[0];
                            }

                            if (noTelp.split(" ").length != 0) {
                                String noTelpArr[] = noTelp.split(" ");
                                noTelp = "";
                                for (String aNoTelpArr : noTelpArr) {
                                    noTelp += aNoTelpArr;
                                }
                            }
                        }

                        if (!latString.equals("null") && !lngString.equals("null")) {
                            Double lat = Double.parseDouble(latString);
                            Double lng = Double.parseDouble(lngString);

                            Lokasi lokasi = new Lokasi(Integer.parseInt(id), nama, alamat, lat, lng, type, noTelp);
                            lokasiArrayList.add(lokasi);
                        }
                    }
                    Model.setLokasiArrayList(lokasiArrayList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        jsonObjectRequest.setTag(TAG);
        requestQueue.add(jsonObjectRequest);
    }
}
