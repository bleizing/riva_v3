package bleizing.riva.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import bleizing.riva.R;
import bleizing.riva.model.GulaDarah;

public class PengingatAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<GulaDarah> gulaDarahArrayList;

    public PengingatAdapter(Context context, ArrayList<GulaDarah> gulaDarahArrayList) {
        this.context = context;
        this.gulaDarahArrayList = gulaDarahArrayList;
    }

    @Override
    public int getCount() {
        return gulaDarahArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return gulaDarahArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.pengingat_listview, viewGroup, false);
        }

        final GulaDarah gulaDarah = (GulaDarah) getItem(i);
        TextView tvCatatan = (TextView) view.findViewById(R.id.tvCatatan);
        TextView tvTanggal = (TextView) view.findViewById(R.id.tvTanggal);

        tvCatatan.setText(gulaDarah.getCatatan());
        tvTanggal.setText(convertMillis(gulaDarah.getTime()));

        return view;
    }

    private String convertMillis(long millis) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        return formatter.format(new Date(millis));
    }
}
