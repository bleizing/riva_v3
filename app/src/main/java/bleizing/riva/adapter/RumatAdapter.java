package bleizing.riva.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import bleizing.riva.R;
import bleizing.riva.model.Lokasi;
import bleizing.riva.viewholder.RumatViewHolder;

/**
 * Created by Bleizing on 4/17/2018.
 */

public class RumatAdapter extends RecyclerView.Adapter<RumatViewHolder> {

    private ArrayList<Lokasi> lokasiArrayList;
    private Context context;

    public RumatAdapter(ArrayList<Lokasi> lokasiArrayList, Context context) {
        this.lokasiArrayList = lokasiArrayList;
        this.context = context;
    }

    @Override
    public RumatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rumat_cardview, parent, false);

        return new RumatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RumatViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        Lokasi lokasi = lokasiArrayList.get(position);

        // TODO : INI MASIH SALAH HARUSNYA CABANG
        holder.tv_rumat_cabang.setText(lokasi.getNama());

        holder.tv_rumat_klinik.setText(lokasi.getNama());
    }

    @Override
    public int getItemCount() {
        return lokasiArrayList.size();
    }

//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }

    public ArrayList<Lokasi> getLokasiArrayList() {
        return lokasiArrayList;
    }

    public void setLokasiArrayList(ArrayList<Lokasi> lokasiArrayList) {
        this.lokasiArrayList = lokasiArrayList;
    }
}
