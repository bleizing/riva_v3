package bleizing.riva.fragment;


import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import bleizing.riva.R;
import bleizing.riva.activity.HomecareActivity;
import bleizing.riva.activity.RumatActivity;
import bleizing.riva.model.Model;
import bleizing.riva.model.NETApi;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrasiFragment extends Fragment {
    private static final String TAG = "RegisterFragment";
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

    private HomecareActivity homecareActivity;
    private RumatActivity rumatActivity;

    private EditText edit_nama;
    private EditText edit_tgl_lahir;
    private EditText edit_no_telp;
    private EditText edit_tgl_kunjungan;
    private EditText edit_waktu_kunjungan;
    private EditText edit_email;

    private String nama;
    private String tgl_lahir;
    private String no_telp;
    private String waktu_kunjungan;
    private String tgl_kunjungan;
    private String email;
    private String jam_kunjungan;

    private int id;

    private RequestQueue requestQueue;

    final Calendar calx = Calendar.getInstance();
    final Calendar caly = Calendar.getInstance();

    private int year_x, month_x, day_x;   // untuk tgl lahir
    private int year_y, month_y, day_y;   // untuk tgl kunjungan

    private TimePickerDialog timePickerDialog;

    public RegistrasiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registrasi, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Request Queue Volley Network Connection
        requestQueue = Volley.newRequestQueue(getContext());

        Bundle args = getArguments();
        id = args.getInt("id");

        if (Model.getHomecareActivity() != null) {
            homecareActivity = Model.getHomecareActivity();
        } else {
            if (Model.getRumatActivity() != null) {
                rumatActivity = Model.getRumatActivity();
            }
        }

        tgl_lahir = "";
        waktu_kunjungan = "";
        jam_kunjungan = "";

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
        edit_no_telp = (EditText) getActivity().findViewById(R.id.edit_no_telp);
        edit_tgl_kunjungan = (EditText) getActivity().findViewById(R.id.edit_tgl_kunjungan);
        edit_waktu_kunjungan = (EditText) getActivity().findViewById(R.id.edit_waktu_kunjungan);
        edit_email = (EditText) getActivity().findViewById(R.id.edit_email);

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
                        .setCancelText("Batal");
                cdp.show(getActivity().getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
            }
        });

        edit_tgl_kunjungan.setOnClickListener(new View.OnClickListener() {
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
                                tgl_kunjungan = DateFormat.format("MM-dd-yyyy", caly).toString();
                                String str = DateFormat.format("dd MMMM yyyy", caly).toString();
                                edit_tgl_kunjungan.setText(str);
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(year_y, month_y, day_y)
                        .setDoneText("Pilih")
                        .setCancelText("Batal");
                cdp.show(getActivity().getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
            }
        });

        edit_waktu_kunjungan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jam_kunjungan = "";
                edit_waktu_kunjungan.setText("");
                showTimeDialog();
            }
        });

        Button btn_register = (Button) getActivity().findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama = edit_nama.getText().toString();
//                tgl_lahir = edit_tgl_lahir.getText().toString();
                no_telp = edit_no_telp.getText().toString();
//                waktu_kunjungan = edit_waktu_kunjungan.getText().toString();
                waktu_kunjungan = tgl_kunjungan + " " + jam_kunjungan;
                email = edit_email.getText().toString();



                if (!nama.equals("") && !tgl_lahir.equals("") && !waktu_kunjungan.equals("") && !no_telp.equals("") && !email.equals("")) {
                    sendDataToServer(nama, tgl_lahir, no_telp, waktu_kunjungan, email);


                } else {
                    Toast.makeText(getActivity(), "Lengkapi Form Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendDataToServer(final String nama, final String tgl_lahir, final String no_telp, final String waktu_kunjungan, final String email) {
//        final JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("rqid", NETApi.RQID);
//            jsonObject.put("id_perusahaan", id);
//            jsonObject.put("nama", nama);
//            jsonObject.put("alamat", "");
//            jsonObject.put("ttl", tgl_lahir);
//            jsonObject.put("no_telp", no_telp);
//            jsonObject.put("waktu_kunjungan", waktu_kunjungan);
//            jsonObject.put("email", email);
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
//                        String id_unit = jsonObject1.getString("id_unit");
//                        String nama = jsonObject1.getString("nama");
//                        String ttl = jsonObject1.getString("ttl");
//                        String no_telp = jsonObject1.getString("no_telp");
//                        String waktu_kunjungan = jsonObject1.getString("waktu_kunjungan");
//                        String email = jsonObject1.getString("email");
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
//                    pars.put("Content-Type", "application/x-www-form-urlencoded");
//                    return pars;
//                }
//
//                @Override
//                public String getBodyContentType() {
//                    Map<String, String> pars = new HashMap<String, String>();
//                    pars.put("Content-Type", "application/x-www-form-urlencoded");
////                    pars.put("rqid", NETApi.RQID);
////                    pars.put("id_perusahaan", String.valueOf(id));
////                    pars.put("nama", nama);
////                    pars.put("alamat", "");
////                    pars.put("ttl", tgl_lahir);
////                    pars.put("no_telp", no_telp);
////                    pars.put("waktu_kunjungan", waktu_kunjungan);
////                    pars.put("email", email);
//                    Log.d(TAG, "pars = " + pars);
////                    return pars.toString();
//                    return "application/x-www-form-urlencoded";
//                }
//            };
//
//            jsonObjectRequest.setTag(TAG);
//            requestQueue.add(jsonObjectRequest);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, NETApi.BASE_URL + NETApi.POST_BOOKING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                 // TODO : DATANYA MAU DIAPAIN?
                try {
                    JSONObject jsonObject1 = new JSONObject(response).getJSONObject("parameter");

                    String booking_code = jsonObject1.getString("booking_code");
                    String id_unit = jsonObject1.getString("id_unit");
                    String nama = jsonObject1.getString("nama");
                    String ttl = jsonObject1.getString("ttl");
                    String no_telp = jsonObject1.getString("no_telp");
                    String waktu_kunjungan = jsonObject1.getString("waktu_kunjungan");
                    String email = jsonObject1.getString("email");
                    int status = jsonObject1.getInt("status");

                    moveToNextFragment();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(rumatActivity, "Proses Registrasi Gagal", Toast.LENGTH_SHORT).show();
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
                pars.put("id_perusahaan", String.valueOf(id));
                pars.put("nama", nama);
//                pars.put("alamat", "");
                pars.put("ttl", tgl_lahir);
                pars.put("no_telp", no_telp);
                pars.put("waktu_kunjungan", waktu_kunjungan);
                pars.put("email", email);
                pars.put("id_user", String.valueOf(Model.getId()));
                pars.put("no_mr", "");

                Log.wtf(TAG, "pars = " + pars.toString());

                return pars;
            }
        };
        stringRequest.setTag(TAG);
        requestQueue.add(stringRequest);
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
        timePickerDialog = new TimePickerDialog(rumatActivity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                /**
                 * Method ini dipanggil saat kita selesai memilih waktu di DatePicker
                 */

                if (hourOfDay < 10) {
                    jam_kunjungan = "0";
                }

                jam_kunjungan += hourOfDay;
                jam_kunjungan += ":";

                if (minute < 10) {
                    jam_kunjungan += "0";
                }

                jam_kunjungan += minute;

                edit_waktu_kunjungan.setText(jam_kunjungan);
            }
        },
                /**
                 * Tampilkan jam saat ini ketika TimePicker pertama kali dibuka
                 */
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),

                /**
                 * Cek apakah format waktu menggunakan 24-hour format
                 */

                DateFormat.is24HourFormat(rumatActivity));

        timePickerDialog.show();
    }
}
