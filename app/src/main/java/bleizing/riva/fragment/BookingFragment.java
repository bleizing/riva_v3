package bleizing.riva.fragment;


import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import bleizing.riva.R;
import bleizing.riva.activity.HomecareActivity;
import bleizing.riva.activity.PilihLokasiActivity;
import bleizing.riva.activity.RumatActivity;
import bleizing.riva.model.Model;
import bleizing.riva.model.NETApi;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends Fragment {
    private static final String TAG = "BookingFragment";
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

    private static final int REQUEST_CODE_LOKASI = 99;

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
    private String waktu_kunjungan;

    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;

    private RequestQueue requestQueue;

    final Calendar calx = Calendar.getInstance();
    final Calendar caly = Calendar.getInstance();

    private int year_x, month_x, day_x;   // untuk tgl lahir
    private int year_y, month_y, day_y;   // untuk tgl kunjungan

    private TimePickerDialog timePickerDialog;

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

        tgl_lahir = "";
        jadwal_tgl = "";
        jadwal_waktu = "";
        foto_luka = "";

        year_x = calx.get(Calendar.YEAR);
        month_x = calx.get(Calendar.MONTH);
        day_x = calx.get(Calendar.DAY_OF_MONTH);
        calx.set(year_x, month_x, day_x);

        year_y = caly.get(Calendar.YEAR);
        month_y = caly.get(Calendar.MONTH);
        day_y = caly.get(Calendar.DAY_OF_MONTH);
        caly.set(year_y, month_y, day_y);

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

        edit_tgl_lahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                calx.set(year, monthOfYear, dayOfMonth);
                                day_x = dayOfMonth;
                                month_x = monthOfYear;
                                year_x = year;
                                Locale.setDefault(new Locale("in", "ID"));
                                tgl_lahir = DateFormat.format("MM-dd-yyyy", calx).toString();
                                String str = DateFormat.format("dd MMMM yyyy", calx).toString();
                                edit_tgl_lahir.setText(str);
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(year_x, month_x, day_x)
                        .setDoneText("Pilih")
                        .setCancelText("Cancel");
                cdp.show(getActivity().getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
            }
        });

        edit_jadwal_tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                caly.set(year, monthOfYear, dayOfMonth);
                                day_y = dayOfMonth;
                                month_y = monthOfYear;
                                year_y = year;
                                Locale.setDefault(new Locale("in", "ID"));
                                jadwal_tgl = DateFormat.format("MM-dd-yyyy", caly).toString();
                                String str = DateFormat.format("dd MMMM yyyy", caly).toString();
                                edit_jadwal_tgl.setText(str);
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(year_y, month_y, day_y)
                        .setDoneText("Pilih")
                        .setCancelText("Batal");
                cdp.show(getActivity().getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
            }
        });

        edit_jadwal_waktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jadwal_waktu = "";
                edit_jadwal_waktu.setText("");
                showTimeDialog();
            }
        });

        edit_lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), PilihLokasiActivity.class), REQUEST_CODE_LOKASI);
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
//                tgl_lahir = edit_tgl_lahir.getText().toString();
//                jadwal_tgl = edit_jadwal_tgl.getText().toString();
//                jadwal_waktu = edit_jadwal_waktu.getText().toString();
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

    private void sendDataToServer(final String nama, final String tgl_lahir, final String foto_luka, final String jadwal_tgl, final String jadwal_waktu, final String lokasi, final String no_telp, final String kode_referensi, final String jk_perawat) {
//        final JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("rqid", NETApi.RQID);
//            jsonObject.put("nama", nama);
//            jsonObject.put("alamat", "");
//            jsonObject.put("ttl", tgl_lahir);
//            jsonObject.put("no_telp", no_telp);
//            jsonObject.put("waktu_dikunjungi", jadwal_tgl + jadwal_waktu);
//            jsonObject.put("foto_luka", foto_luka);
//            jsonObject.put("lokasi", lokasi);
//            jsonObject.put("jk_perawat", jk_perawat);
//            jsonObject.put("kode_referal", kode_referensi);
//            jsonObject.put("no_mr", "");
//            jsonObject.put("id_perawat", "");
//
//            Log.d(TAG, "jsonObject = " + jsonObject.toString());
//
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, NETApi.BASE_URL + NETApi.POST_HOMECARE, jsonObject, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    Log.d(TAG, response.toString());
//
//                    // TODO : DATANYA MAU DIAPAIN?
//                    try {
//                        JSONObject jsonObject1 = response.getJSONObject("parameter");
//
//                        String booking_code = jsonObject1.getString("booking_code");
//                        String nama = jsonObject1.getString("nama");
//                        String alamat = jsonObject1.getString("alamat");
//                        String ttl = jsonObject1.getString("ttl");
//                        String no_telp = jsonObject1.getString("no_telp");
//                        String waktu_dikunjungi = jsonObject1.getString("waktu_dikunjungi");
//                        String foto_luka = jsonObject1.getString("foto_luka");
//                        String lokasi = jsonObject1.getString("lokasi");
//                        String jk_perawat = jsonObject1.getString("jk_perawat");
//                        String kode_referal = jsonObject1.getString("kode_referal");
//                        String no_mr = jsonObject1.getString("no_mr");
//                        String id_perawat = jsonObject1.getString("id_perawat");
//                        int status = jsonObject1.getInt("status");
//
//                        moveToNextFragment();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//            }) {
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    Map<String, String> pars = new HashMap<String, String>();
//                    pars.put("Content-Type", "multipart/form-data");
//                    return pars;
//                }
//            };
//
//            jsonObjectRequest.setTag(TAG);
//            requestQueue.add(jsonObjectRequest);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }



        StringRequest stringRequest = new StringRequest(Request.Method.POST, NETApi.BASE_URL + NETApi.POST_HOMECARE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                // TODO : DATANYA MAU DIAPAIN?
                try {
                    JSONObject jsonObject1 = new JSONObject(response).getJSONObject("parameter");

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

                    moveToNextFragment();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Proses Order Gagal", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                Map<String, String> pars = new HashMap<String, String>();
                pars.put("Content-Type", "multipart/form-data");
//                return pars;
                return "application/x-www-form-urlencoded";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pars = new HashMap<String, String>();

                pars.put("rqid", NETApi.RQID);
                pars.put("nama", nama);
                pars.put("alamat", lokasi);
                pars.put("ttl", tgl_lahir);
                pars.put("no_telp", no_telp);
                pars.put("waktu_dikunjungi", waktu_kunjungan);
                pars.put("lokasi", lokasi);
                pars.put("jk_perawat", jk_perawat);
                pars.put("kode_referal", kode_referensi);
                pars.put("no_mr", "");
                pars.put("id_perawat", "");
                pars.put("foto_luka", foto_luka);

                Log.d(TAG, "pars = " + pars.toString());

                return pars;
            }
        };
        stringRequest.setTag(TAG);
        requestQueue.add(stringRequest);

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
                edit_foto_luka.setText(data.getData().getPath());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == REQUEST_CODE_LOKASI) {
            if (resultCode == RESULT_OK) {
                String latlng = data.getData().toString();
                edit_lokasi.setText(latlng);
            }
        }
    }

    //convert image bitmap to string
    private String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 15, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void moveToNextFragment() {
        // TODO : INI MAU KEMANA?
        if (rumatActivity != null) {
            ((RumatActivity) getActivity()).changeToPaymentFragment();
        }

        if (homecareActivity != null) {
            ((HomecareActivity) getActivity()).changeToPaymentFragment();
        }
    }

    private void showTimeDialog() {

        /**
         * Calendar untuk mendapatkan waktu saat ini
         */
        Calendar calendar = Calendar.getInstance();

        /**
         * Initialize TimePicker Dialog
         */

        timePickerDialog = new TimePickerDialog(homecareActivity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                /**
                 * Method ini dipanggil saat kita selesai memilih waktu di DatePicker
                 */

                if (hourOfDay < 10) {
                    jadwal_waktu = "0";
                }

                jadwal_waktu += hourOfDay;
                jadwal_waktu += ":";

                if (minute < 10) {
                    jadwal_waktu += "0" + minute;
                }

                edit_jadwal_waktu.setText(jadwal_waktu);

                waktu_kunjungan = jadwal_tgl + " " + jadwal_waktu;
            }
        },
                /**
                 * Tampilkan jam saat ini ketika TimePicker pertama kali dibuka
                 */
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),

                /**
                 * Cek apakah format waktu menggunakan 24-hour format
                 */

                DateFormat.is24HourFormat(homecareActivity));

        timePickerDialog.show();
    }
}
