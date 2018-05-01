package bleizing.riva.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import bleizing.riva.PrefManager;
import bleizing.riva.R;
import bleizing.riva.activity.HomeActivity;
import bleizing.riva.activity.LoginActivity;
import bleizing.riva.model.Model;
import bleizing.riva.model.NETApi;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";

    private EditText editUsername;
    private EditText editPassword;

    private RequestQueue requestQueue;

    private int status;

    private PrefManager prefManager;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        status = ((LoginActivity) getActivity()).getStatus();

        prefManager = new PrefManager(getContext());

        requestQueue = Volley.newRequestQueue(getContext());

        final LinearLayout linRegis = (LinearLayout) getActivity().findViewById(R.id.linRegis);
        final LinearLayout linVerif = (LinearLayout) getActivity().findViewById(R.id.linVerif);
        final TextView tvKirimUlang = (TextView) getActivity().findViewById(R.id.tvKirimUlang);

        if (status == 0) {
            linRegis.setVisibility(View.VISIBLE);
            linVerif.setVisibility(View.GONE);
            tvKirimUlang.setVisibility(View.GONE);
        } else {
            linRegis.setVisibility(View.GONE);
            linVerif.setVisibility(View.VISIBLE);
            tvKirimUlang.setVisibility(View.VISIBLE);
        }

        Button btnLogin = (Button) getActivity().findViewById(R.id.btnLogin);
        editUsername = (EditText) getActivity().findViewById(R.id.editUsername);
        editPassword = (EditText) getActivity().findViewById(R.id.editPassword);

        tvKirimUlang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, NETApi.BASE_URL + NETApi.REGISTRASI, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), "Kode Verifikasi Telah Dikirim Ulang!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            JSONArray jsonArray = jsonObject1.getJSONArray("parameter");

                            JSONObject jsonObject = jsonArray.getJSONObject(0);

                            int id = jsonObject.getInt("id");
                            String no_hp = jsonObject.getString("no_hp");

                            prefManager.setRegister(id, no_hp, 1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();

                        Toast.makeText(getContext(), "Kirim Ulang Kode Verifikasi Gagal!", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        Map<String, String> pars = new HashMap<String, String>();
                        pars.put("Content-Type", "application/x-www-form-urlencoded");
                        //return pars;
                        return "application/x-www-form-urlencoded";
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> pars = new HashMap<String, String>();

                        pars.put("rqid", NETApi.RQID);
                        pars.put("no_hp", prefManager.getNoHp());

                        Log.wtf(TAG, "pars = " + pars.toString());

                        return pars;
                    }
                };

                stringRequest.setTag(TAG);
                requestQueue.add(stringRequest);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status == 0) {
                    final String username = editUsername.getText().toString();

                    if (!username.equals("")) {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, NETApi.BASE_URL + NETApi.REGISTRASI, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d(TAG, response.toString());
                                try {
                                    JSONObject jsonObject1 = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject1.getJSONArray("parameter");

                                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                                    int id = jsonObject.getInt("id");
                                    String no_hp = jsonObject.getString("no_hp");

                                    prefManager.setRegister(id, no_hp, 1);

                                    status = 1;

                                    linRegis.setVisibility(View.GONE);
                                    linVerif.setVisibility(View.VISIBLE);
                                    tvKirimUlang.setVisibility(View.VISIBLE);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();

                                Toast.makeText(getContext(), "Register Gagal!", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            public String getBodyContentType() {
                                Map<String, String> pars = new HashMap<String, String>();
                                pars.put("Content-Type", "application/x-www-form-urlencoded");
                                //return pars;
                                return "application/x-www-form-urlencoded";
                            }

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> pars = new HashMap<String, String>();

                                pars.put("rqid", NETApi.RQID);
                                pars.put("no_hp", username);

                                Log.wtf(TAG, "pars = " + pars.toString());

                                return pars;
                            }
                        };

                        stringRequest.setTag(TAG);
                        requestQueue.add(stringRequest);
                    } else {
                        Toast.makeText(getContext(), "Masukkan No HP!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    final String password = editPassword.getText().toString();

                    if (!password.equals("")) {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, NETApi.BASE_URL + NETApi.REGISTRASI, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d(TAG, response.toString());
                                try {
                                    JSONObject jsonObject1 = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject1.getJSONArray("parameter");

                                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                                    int id = jsonObject.getInt("id");
                                    String no_hp = jsonObject.getString("no_hp");

                                    prefManager.setRegister(id, no_hp, 2);

                                    Model.setId(id);

                                    status = 2;

                                    Intent intent = new Intent(getContext(), HomeActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();

                                Toast.makeText(getContext(), "Register Gagal!", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            public String getBodyContentType() {
                                Map<String, String> pars = new HashMap<String, String>();
                                pars.put("Content-Type", "application/x-www-form-urlencoded");
                                //return pars;
                                return "application/x-www-form-urlencoded";
                            }

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> pars = new HashMap<String, String>();

                                pars.put("rqid", NETApi.RQID);
                                pars.put("no_hp", prefManager.getNoHp());
                                pars.put("verif_code", password);

                                Log.wtf(TAG, "pars = " + pars.toString());

                                return pars;
                            }
                        };

                        stringRequest.setTag(TAG);
                        requestQueue.add(stringRequest);
                    } else {
                        Toast.makeText(getContext(), "Masukkan Kode Verifikasi!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

//    private void prosesLogin(String username, String password) {
//        if (username.equals("admin") && password.equals("admin")) {
//            Intent intent = new Intent(getContext(), HomeActivity.class);
//            startActivity(intent);
//            getActivity().finish();
//        } else {
//            Toast.makeText(getContext(), "Username Atau Password Salah!", Toast.LENGTH_SHORT).show();
//        }
//    }
}
