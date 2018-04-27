package bleizing.riva.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import bleizing.riva.R;

/**
 * Created by Bleizing on 3/13/2018.
 */

public class ArticleViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_tanggal_article;
    public TextView tv_bulan_article;
    public TextView tv_title_article;
    public NetworkImageView img_foto_article;

    public ArticleViewHolder(View itemView) {
        super(itemView);

        tv_tanggal_article = (TextView) itemView.findViewById(R.id.tv_tanggal_article);
        tv_bulan_article = (TextView) itemView.findViewById(R.id.tv_bulan_article);
        tv_title_article = (TextView) itemView.findViewById(R.id.tv_title_article);
        img_foto_article = (NetworkImageView) itemView.findViewById(R.id.img_foto_article);
    }
}
