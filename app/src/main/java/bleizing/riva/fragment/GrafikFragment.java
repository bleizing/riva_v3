package bleizing.riva.fragment;


import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import bleizing.riva.R;
import bleizing.riva.activity.JurnalGdsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class GrafikFragment extends Fragment {

    private LineChart lineChart;

    private TextView tv_harian;
    private TextView tv_mingguan;
    private TextView tv_bulanan;

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

        tv_harian = (TextView) getActivity().findViewById(R.id.tv_harian);
        tv_mingguan = (TextView) getActivity().findViewById(R.id.tv_mingguan);
        tv_bulanan = (TextView) getActivity().findViewById(R.id.tv_bulanan);

        tv_harian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_harian.setBackground(getActivity().getResources().getDrawable(R.color.hijau));
                tv_mingguan.setBackground(getActivity().getResources().getDrawable(R.color.cardview_dark_background));
                tv_bulanan.setBackground(getActivity().getResources().getDrawable(R.color.cardview_dark_background));
            }
        });

        tv_mingguan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_mingguan.setBackground(getActivity().getResources().getDrawable(R.color.hijau));
                tv_bulanan.setBackground(getActivity().getResources().getDrawable(R.color.cardview_dark_background));
                tv_harian.setBackground(getActivity().getResources().getDrawable(R.color.cardview_dark_background));
            }
        });

        tv_bulanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_bulanan.setBackground(getActivity().getResources().getDrawable(R.color.hijau));
                tv_harian.setBackground(getActivity().getResources().getDrawable(R.color.cardview_dark_background));
                tv_mingguan.setBackground(getActivity().getResources().getDrawable(R.color.cardview_dark_background));
            }
        });

        lineChart = (LineChart) getActivity().findViewById(R.id.linechart);
        // add data
        setData();

        // get the legend (only possible after setting data)
        Legend l = lineChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);

        // no description text
        lineChart.setDescription("Demo Line Chart");
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");

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
                        final Dialog dialog2 = new Dialog(getActivity());
                        dialog2.setContentView(R.layout.dialog_tips_jurnal);

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

    // This is used to store x-axis values
    private ArrayList<String> setXAxisValues(){
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("10");
        xVals.add("20");
        xVals.add("30");

        return xVals;
    }

    // This is used to store Y-axis values
    private ArrayList<Entry> setYAxisValues(){
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        yVals.add(new Entry(60, 0));
        yVals.add(new Entry(48, 1));
        yVals.add(new Entry(100, 3));

        return yVals;
    }

    private void setData() {
        ArrayList<String> xVals = setXAxisValues();

        ArrayList<Entry> yVals = setYAxisValues();

        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "DataSet 1");
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
}
