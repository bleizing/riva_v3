package bleizing.riva.fragment;


import android.app.Dialog;
import android.graphics.Color;
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

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import bleizing.riva.R;
import bleizing.riva.model.DBHelper;
import bleizing.riva.model.GulaDarah;
import bleizing.riva.model.Model;

/**
 * A simple {@link Fragment} subclass.
 */
public class GrafikFragment extends Fragment {
    private static final String TAG = "GrafikFragment";
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

    private LineChart lineChart;

    private TextView tv_harian;
    private TextView tv_mingguan;
    private TextView tv_bulanan;

    private TextView tvTanggalAwal;
    private TextView tvTanggalAkhir;

    private LinearLayout linTanggalAwal;
    private LinearLayout linTanggalAkhir;

    private String tglAwal;
    private String tglAkhir;

    private DBHelper dbHelper;

    private String limit;

    final Calendar calAwal = Calendar.getInstance();
    final Calendar calAkhir = Calendar.getInstance();

    private int yearAwal, monthAwal, dayAwal;   // untuk tgl awal
    private int yearAkhir, monthAkhir, dayAkhir;   // untuk tgl akhir

    public GrafikFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grafik, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper = new DBHelper(getContext());

        lineChart = (LineChart) getActivity().findViewById(R.id.linechart);

//        dbHelper.clearTable();

//        Model.setGds(dbHelper.getAllGds());

        initStart();

        tv_harian = (TextView) getActivity().findViewById(R.id.tv_harian);
        tv_mingguan = (TextView) getActivity().findViewById(R.id.tv_mingguan);
        tv_bulanan = (TextView) getActivity().findViewById(R.id.tv_bulanan);

        linTanggalAwal = (LinearLayout) getActivity().findViewById(R.id.linTanggalAwal);
        linTanggalAkhir = (LinearLayout) getActivity().findViewById(R.id.linTanggalAkhir);

        tvTanggalAwal = (TextView) getActivity().findViewById(R.id.tvTanggalAwal);
        tvTanggalAkhir = (TextView) getActivity().findViewById(R.id.tvTanggalAkhir);

        TextView tvTanggal = (TextView) getActivity().findViewById(R.id.tvTanggal);
        Calendar cal = getCurrentDay();
        String month = convertMonth(cal.get(Calendar.MONTH));
        String currentDate = cal.get(Calendar.DATE) + " " + month + " " + cal.get(Calendar.YEAR);
        tvTanggal.setText(currentDate);

        yearAwal = calAwal.get(Calendar.YEAR);
        monthAwal = calAwal.get(Calendar.MONTH);
        dayAwal = calAwal.get(Calendar.DAY_OF_MONTH);
        calAwal.set(yearAwal, monthAwal, dayAwal);
        tglAwal = android.text.format.DateFormat.format("dd/MM/yyyy", calAwal).toString();
        tvTanggalAwal.setText(tglAwal);

        yearAkhir = calAkhir.get(Calendar.YEAR);
        monthAkhir = calAkhir.get(Calendar.MONTH);
        dayAkhir = calAkhir.get(Calendar.DAY_OF_MONTH);
        calAkhir.set(yearAkhir, monthAkhir, dayAkhir);
        tglAkhir = android.text.format.DateFormat.format("dd/MM/yyyy", calAkhir).toString();
        tvTanggalAkhir.setText(tglAkhir);

        linTanggalAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                calAwal.set(year, monthOfYear, dayOfMonth);
                                dayAwal = dayOfMonth;
                                monthAwal = monthOfYear;
                                yearAwal = year;
                                Locale.setDefault(new Locale("in", "ID"));
                                tglAwal = android.text.format.DateFormat.format("MM-dd-yyyy", calAwal).toString();
                                String str = android.text.format.DateFormat.format("dd/MM/yyyy", calAwal).toString();
                                tvTanggalAwal.setText(str);

//                                Model.setGds(dbHelper.getGdsByRangeTime(String.valueOf(getMillisByDate(calAwal)), String.valueOf(getMillisByDate(calAkhir))));
//                                setData();

                                setModelGds(dbHelper.getGdsByRangeTime(String.valueOf(getMillisByDate(calAwal)), String.valueOf(getMillisByDate(calAkhir))));
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(yearAwal, monthAwal, dayAwal)
                        .setDoneText("Pilih")
                        .setCancelText("Cancel");
                cdp.show(getActivity().getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
            }
        });

        linTanggalAkhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                calAkhir.set(year, monthOfYear, dayOfMonth);
                                dayAkhir = dayOfMonth;
                                monthAkhir = monthOfYear;
                                yearAkhir = year;
                                Locale.setDefault(new Locale("in", "ID"));
                                tglAkhir = android.text.format.DateFormat.format("MM-dd-yyyy", calAkhir).toString();
                                String str = android.text.format.DateFormat.format("dd/MM/yyyy", calAkhir).toString();
                                tvTanggalAkhir.setText(str);

//                                Model.setGds(dbHelper.getGdsByRangeTime(String.valueOf(getMillisByDate(calAwal)), String.valueOf(getMillisByDate(calAkhir))));
//                                setData();

                                setModelGds(dbHelper.getGdsByRangeTime(String.valueOf(getMillisByDate(calAwal)), String.valueOf(getMillisByDate(calAkhir))));
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(yearAkhir, monthAkhir, dayAkhir)
                        .setDoneText("Pilih")
                        .setCancelText("Cancel");
                cdp.show(getActivity().getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
            }
        });

        tv_harian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_harian.setBackground(getActivity().getResources().getDrawable(R.color.hijau));
                tv_mingguan.setBackground(getActivity().getResources().getDrawable(R.color.cardview_dark_background));
                tv_bulanan.setBackground(getActivity().getResources().getDrawable(R.color.cardview_dark_background));
                limit = "4";
                setModelGds(dbHelper.getGdsByLimit(limit));
            }
        });

        tv_mingguan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_mingguan.setBackground(getActivity().getResources().getDrawable(R.color.hijau));
                tv_bulanan.setBackground(getActivity().getResources().getDrawable(R.color.cardview_dark_background));
                tv_harian.setBackground(getActivity().getResources().getDrawable(R.color.cardview_dark_background));
                limit = "7";
                setModelGds(dbHelper.getGdsByLimit(limit));
            }
        });

        tv_bulanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_bulanan.setBackground(getActivity().getResources().getDrawable(R.color.hijau));
                tv_harian.setBackground(getActivity().getResources().getDrawable(R.color.cardview_dark_background));
                tv_mingguan.setBackground(getActivity().getResources().getDrawable(R.color.cardview_dark_background));
                limit = "30";
                setModelGds(dbHelper.getGdsByLimit(limit));
            }
        });

        // add data
//        setData();

        // get the legend (only possible after setting data)
        Legend l = lineChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);

        // no description text
        lineChart.setDescription("Gula Darah");
//        lineChart.setNoDataTextDescription("You need to provide data for the chart.");

//        List<String> categories = new ArrayList<String>();
//        categories.add("Harian");
//        categories.add("Mingguan");
//        categories.add("Bulanan");
//
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        Spinner spin_kategori = (Spinner) getActivity().findViewById(R.id.spin_kategori);
//        spin_kategori.setAdapter(dataAdapter);
//
//        spin_kategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                // On selecting a spinner item
//                String item = adapterView.getItemAtPosition(i).toString();
//
//                // Showing selected spinner item
//                Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        TextView tv_tambah = (TextView) getActivity().findViewById(R.id.tv_tambah);
        tv_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // custom dialog
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_input_gula_darah);

//                // set the custom dialog components - text, image and button
//                TextView text = (TextView) dialog.findViewById(R.id.text);
//                text.setText("Android custom dialog example!");
//                ImageView image = (ImageView) dialog.findViewById(R.id.image);
//                image.setImageResource(R.drawable.ic_launcher);

                Button btn_tambah = (Button) dialog.findViewById(R.id.btn_tambah);
                // if button is clicked, close the custom dialog
                btn_tambah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editAngka = (EditText) dialog.findViewById(R.id.angka);
                        EditText editCatatan = (EditText) dialog.findViewById(R.id.catatan);

                        String angka = editAngka.getText().toString();
                        String catatan = editCatatan.getText().toString();

                        GulaDarah gulaDarah = new GulaDarah(angka, catatan, getCurrentMillis());

                        GulaDarah gulaDarahDB = dbHelper.getGdsByTime(gulaDarah);

                        if (gulaDarahDB != null) {
                            gulaDarahDB.setAngka(angka);
                            gulaDarahDB.setCatatan(catatan);
                            dbHelper.editGds(gulaDarahDB);
                        } else {
                            dbHelper.inputGds(gulaDarah);
                        }

//                        Model.setGds(dbHelper.getAllGds());
                        tv_harian.setBackground(getActivity().getResources().getDrawable(R.color.hijau));
                        tv_mingguan.setBackground(getActivity().getResources().getDrawable(R.color.cardview_dark_background));
                        tv_bulanan.setBackground(getActivity().getResources().getDrawable(R.color.cardview_dark_background));
                        limit = "4";
                        setModelGds(dbHelper.getGdsByLimit(limit));

//                        setYAxisValues();
                        setData();
                        lineChart.notifyDataSetChanged();
                        lineChart.invalidate();

                        final Dialog dialog2 = new Dialog(getActivity());
                        dialog2.setContentView(R.layout.dialog_tips_jurnal);

                        LinearLayout lin140 = (LinearLayout) dialog2.findViewById(R.id.lin140);
                        LinearLayout lin90 = (LinearLayout) dialog2.findViewById(R.id.lin90);
                        LinearLayout lin70 = (LinearLayout) dialog2.findViewById(R.id.lin70);

                        lin140.setVisibility(View.GONE);
                        lin90.setVisibility(View.GONE);
                        lin70.setVisibility(View.GONE);

                        Log.d(TAG, "angka = " + angka);

                        if (Integer.parseInt(angka) > 140) {
                            Log.d(TAG, "angka > 140");
                            lin140.setVisibility(View.VISIBLE);
                            lin70.setVisibility(View.GONE);
                            lin90.setVisibility(View.GONE);
                        } else if (Integer.parseInt(angka) < 70) {
                            Log.d(TAG, "angka < 70");
                            lin140.setVisibility(View.GONE);
                            lin90.setVisibility(View.GONE);
                            lin70.setVisibility(View.VISIBLE);
                        } else {
                            Log.d(TAG, "70 < angka < 140");
                            lin140.setVisibility(View.GONE);
                            lin70.setVisibility(View.GONE);
                            lin90.setVisibility(View.VISIBLE);
                        }

                        TextView tv_tutup = (TextView) dialog2.findViewById(R.id.tv_tutup);
                        tv_tutup.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog2.dismiss();
                                dialog.dismiss();

                            }
                        });

                        dialog2.show();
                    }
                });

                dialog.show();
            }
        });
    }

    private void initStart() {
        limit = "4";
        setModelGds(dbHelper.getGdsByLimit(limit));
    }

    private void setModelGds(ArrayList<GulaDarah> gulaDarahArrayList) {
//        Model.setGds(dbHelper.getGdsByLimit(limit));
//        if (gulaDarahArrayList != null) {
//            if (gulaDarahArrayList.size() != 0) {
//                for (GulaDarah gd : gulaDarahArrayList) {
//                    Log.d(TAG, "GulaDarahGDS ID = " + gd.getId());
//                }
//            } else {
//                Log.d(TAG, "GulaDarahGDS Size == 0");
//            }
//        } else {
//            Log.d(TAG, "GulaDarahGDS == null");
//        }

        Model.setGds(gulaDarahArrayList);
        setData();
    }

    // This is used to store x-axis values
    private ArrayList<String> setXAxisValues(){
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<GulaDarah> gulaDarahArrayList = Model.getGds();
        if (gulaDarahArrayList == null) {
            gulaDarahArrayList = new ArrayList<>();
        }

        if (gulaDarahArrayList.size() > 1) {
//            int j = 1;
            for (GulaDarah gulaDarah : gulaDarahArrayList) {
//                Log.d(TAG, "GulaDarah ID = " + gulaDarah.getId());
                String date = convertMillis(gulaDarah.getTime());
                String dates[] = date.split("/");
                date = dates[0] + "/" + dates[1];

                xVals.add(date);
//                j++;
            }
        } else if (gulaDarahArrayList.size() == 1) {
//            Log.d(TAG, "GulaDarah ID SIZE == 1 = " + gulaDarahArrayList.get(0).getId());
            String date = convertMillis(gulaDarahArrayList.get(0).getTime());
            String dates[] = date.split("/");
            date = dates[0] + "/" + dates[1];

            xVals.add(date);
        }
//        xVals.add("1");
//        xVals.add("20");
//        xVals.add("30");

        return xVals;
    }

    // This is used to store Y-axis values
    private ArrayList<Entry> setYAxisValues(){
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        ArrayList<GulaDarah> gulaDarahArrayList = Model.getGds();
        if (gulaDarahArrayList == null) {
            gulaDarahArrayList = new ArrayList<>();
        }

        if (gulaDarahArrayList.size() > 1) {
            int j = 0;
            for (GulaDarah gulaDarah : gulaDarahArrayList) {
                yVals.add(new Entry(Float.parseFloat(gulaDarah.getAngka()), j));
                j++;
            }
        } else if (gulaDarahArrayList.size() == 1) {
            yVals.add(new Entry(Float.parseFloat(gulaDarahArrayList.get(0).getAngka()), 0));
        }
//        yVals.add(new Entry(Model.getAllGds(), 0));
//        yVals.add(new Entry(48, 1));
//        yVals.add(new Entry(100, 3));

        return yVals;
    }

    private void setData() {
        lineChart.invalidate();
        lineChart.clear();

        ArrayList<String> xVals = setXAxisValues();

        ArrayList<Entry> yVals = setYAxisValues();

        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "Gula Darah");
        set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        // set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        lineChart.setData(data);

    }

    private Calendar getCurrentDay() {
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date = cal.get(Calendar.DATE);

        cal.clear();

        cal.set(year, month, date);

        return cal;
    }

    private long getCurrentMillis() {
        Calendar cal = getCurrentDay();

        return cal.getTimeInMillis();
    }

    private long getMillisByDate(Calendar calendar) {
        return calendar.getTimeInMillis();
    }

    private String convertMillis(long millis) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        return formatter.format(new Date(millis));
    }

    private String convertMonth(int month) {
        String bulan = "";
        switch (month) {
            case 0:
                bulan = "Januari";
                break;
            case 1:
                bulan = "Februari";
                break;
            case 2:
                bulan = "Maret";
                break;
            case 3:
                bulan = "April";
                break;
            case 4:
                bulan = "May";
                break;
            case 5:
                bulan = "Juni";
                break;
            case 6:
                bulan = "Juli";
                break;
            case 7:
                bulan = "Agustus";
                break;
            case 8:
                bulan = "September";
                break;
            case 9:
                bulan = "Oktober";
                break;
            case 10:
                bulan = "November";
                break;
            case 11:
                bulan = "Desember";
                break;
        }

        return bulan;
    }
}
