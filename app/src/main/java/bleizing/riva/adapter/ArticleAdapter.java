package bleizing.riva.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import bleizing.riva.R;
import bleizing.riva.model.Artikel;
import bleizing.riva.viewholder.ArticleViewHolder;
import bleizing.riva.viewholder.RumatViewHolder;

/**
 * Created by Bleizing on 3/13/2018.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

    ArrayList<Artikel> artikelArrayList;
    private Context context;

    public ArticleAdapter(ArrayList<Artikel> artikelArrayList, Context context) {
        this.artikelArrayList = artikelArrayList;
        this.context = context;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_cardview, parent, false);

        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        Artikel artikel = artikelArrayList.get(position);

        holder.tv_tanggal_article.setText(artikel.getTanggal());
        holder.tv_bulan_article.setText(artikel.getBulan());
        holder.tv_title_article.setText(artikel.getJudul());

        //TODO: FOTO
    }

    @Override
    public int getItemCount() {
        return artikelArrayList.size();
    }

    public ArrayList<Artikel> getArtikelArrayList() {
        return artikelArrayList;
    }

    public void setArtikelArrayList(ArrayList<Artikel> artikelArrayList) {
        this.artikelArrayList = artikelArrayList;
    }
}
