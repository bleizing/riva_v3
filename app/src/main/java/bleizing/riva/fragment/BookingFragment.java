package bleizing.riva.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import bleizing.riva.R;
import bleizing.riva.activity.HomecareActivity;
import bleizing.riva.activity.RumatActivity;
import bleizing.riva.model.Model;
import bleizing.riva.model.NETApi;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends Fragment {
    private static final String TAG = "BookingFragment";

    private HomecareActivity homecareActivity;
    private RumatActivity rumatActivity;

    private LinearLayout linear_perawat;
    private LinearLayout linear_laki;
    private LinearLayout linear_perempuan;

    private EditText edit_nama;
    private EditText edit_tgl_lahir;
    private EditText edit_foto_luka;
    private EditText edit_jadwal_tgl;
    private EditText edit_jadwal_waktu;
    private EditText edit_lokasi;
    private EditText edit_no_telp;
    private EditText edit_kode_referensi;

    private String nama;
    private String tgl_lahir;
    private String foto_luka;
    private String jadwal_tgl;
    private String jadwal_waktu;
    private String lokasi;
    private String no_telp;
    private String kode_referensi;
    private String jk_perawat;

    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;

    private RequestQueue requestQueue;

    public BookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Request Queue Volley Network Connection
        requestQueue = Volley.newRequestQueue(getContext());

        if (Model.getHomecareActivity() != null) {
            homecareActivity = Model.getHomecareActivity();
        } else {
            if (Model.getRumatActivity() != null) {
                rumatActivity = Model.getRumatActivity();
            }
        }

        if (rumatActivity != null) {
            ((RumatActivity) getActivity()).setActionBarTitle(getActivity().getResources().getString(R.string.homecare));
        }

        if (homecareActivity != null) {
            ((HomecareActivity) getActivity()).setActionBarTitle(getActivity().getResources().getString(R.string.homecare));
        }

        edit_nama = (EditText) getActivity().findViewById(R.id.edit_nama);
        edit_tgl_lahir = (EditText) getActivity().findViewById(R.id.edit_tgl_lahir);
        edit_foto_luka = (EditText) getActivity().findViewById(R.id.edit_foto_luka);
        edit_jadwal_tgl = (EditText) getActivity().findViewById(R.id.edit_jadwal_tgl);
        edit_jadwal_waktu = (EditText) getActivity().findViewById(R.id.edit_jadwal_waktu);
        edit_lokasi = (EditText) getActivity().findViewById(R.id.edit_lokasi);
        edit_no_telp = (EditText) getActivity().findViewById(R.id.edit_no_telp);
        edit_kode_referensi = (EditText) getActivity().findViewById(R.id.edit_kode_referensi);

        edit_foto_luka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        Button btn_order = (Button) getActivity().findViewById(R.id.btn_order);
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bitmap != null) {
                    foto_luka = getStringImage(bitmap);
                }

                nama = edit_nama.getText().toString();
                tgl_lahir = edit_tgl_lahir.getText().toString();
                jadwal_tgl = edit_jadwal_tgl.getText().toString();
                jadwal_waktu = edit_jadwal_waktu.getText().toString();
                lokasi = edit_lokasi.getText().toString();
                no_telp = edit_no_telp.getText().toString();
                kode_referensi = edit_kode_referensi.getText().toString();

                if (kode_referensi.equals("")) {
                    kode_referensi = "";
                }

                if (linear_laki.getVisibility() == View.VISIBLE) {
                    jk_perawat = "L";
                }

                if (linear_perempuan.getVisibility() == View.VISIBLE) {
                    jk_perawat = "P";
                }

                if (!nama.equals("") && !tgl_lahir.equals("") && !jadwal_tgl.equals("") && !jadwal_waktu.equals("") && !lokasi.equals("") && !no_telp.equals("")) {
                    sendDataToServer(nama, tgl_lahir, foto_luka, jadwal_tgl, jadwal_waktu, lokasi, no_telp, kode_referensi, jk_perawat);

                    // TODO : INI MAU KEMANA?
                    if (rumatActivity != null) {
                        ((RumatActivity) getActivity()).changeToPaymentFragment();
                    }

                    if (homecareActivity != null) {
                        ((HomecareActivity) getActivity()).changeToPaymentFragment();
                    }
                } else {
                    Toast.makeText(getActivity(), "Lengkapi Form Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        linear_perawat = (LinearLayout) getActivity().findViewById(R.id.linear_perawat);
        linear_laki = (LinearLayout) getActivity().findViewById(R.id.linear_laki);
        linear_perempuan = (LinearLayout) getActivity().findViewById(R.id.linear_perempuan);

        linear_perawat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear_perawat.setVisibility(View.GONE);
                linear_laki.setVisibility(View.VISIBLE);
                linear_perempuan.setVisibility(View.VISIBLE);
            }
        });

        linear_laki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linear_perempuan.getVisibility() == View.GONE) {
                    linear_perempuan.setVisibility(View.VISIBLE);
                } else {
                    linear_perempuan.setVisibility(View.GONE);
                }
            }
        });

        linear_perempuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linear_laki.getVisibility() == View.GONE) {
                    linear_laki.setVisibility(View.VISIBLE);
                } else {
                    linear_laki.setVisibility(View.GONE);
                }
            }
        });
    }

    private void sendDataToServer(String nama, String tgl_lahir, String foto_luka, String jadwal_tgl, String jadwal_waktu, String lokasi, String no_telp, String kode_referensi, String jk_perawat) {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("rqid", NETApi.RQID);
            jsonObject.put("nama", nama);
            jsonObject.put("alamat", "");
            jsonObject.put("ttl", tgl_lahir);
            jsonObject.put("no_telp", no_telp);
            jsonObject.put("waktu_dikunjungi", jadwal_tgl + jadwal_waktu);
            jsonObject.put("foto_luka", foto_luka);
            jsonObject.put("lokasi", lokasi);
            jsonObject.put("jk_perawat", jk_perawat);
            jsonObject.put("kode_referal", kode_referensi);
            jsonObject.put("no_mr", "");
            jsonObject.put("id_perawat", "");

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, NETApi.BASE_URL + NETApi.POST_HOMECARE, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, response.toString());

                    // TODO : DATANYA MAU DIAPAIN?
                    try {
                        JSONObject jsonObject1 = response.getJSONObject("parameter");

                        String booking_code = jsonObject1.getString("booking_code");
                        String nama = jsonObject1.getString("nama");
                        String alamat = jsonObject1.getString("alamat");
                        String ttl = jsonObject1.getString("ttl");
                        String no_telp = jsonObject1.getString("no_telp");
                        String waktu_dikunjungi = jsonObject1.getString("waktu_dikunjungi");
                        String foto_luka = jsonObject1.getString("foto_luka");
                        String lokasi = jsonObject1.getString("lokasi");
                        String jk_perawat = jsonObject1.getString("jk_perawat");
                        String kode_referal = jsonObject1.getString("kode_referal");
                        String no_mr = jsonObject1.getString("no_mr");
                        String id_perawat = jsonObject1.getString("id_perawat");
                        int status = jsonObject1.getInt("status");
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // membuka gallery
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //convert image bitmap to string
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 15, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
