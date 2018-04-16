package bleizing.riva.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import bleizing.riva.R;

/**
 * Created by Bleizing on 4/17/2018.
 */

public class RumatViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_rumat_cabang;
    public TextView tv_rumat_klinik;

    public RumatViewHolder(View itemView) {
        super(itemView);

        tv_rumat_cabang = (TextView) itemView.findViewById(R.id.tv_rumat_cabang);
        tv_rumat_klinik = (TextView) itemView.findViewById(R.id.tv_rumat_klinik);
    }
}
