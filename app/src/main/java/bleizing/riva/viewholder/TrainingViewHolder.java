package bleizing.riva.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.toolbox.NetworkImageView;

import bleizing.riva.R;

/**
 * Created by Bleizing on 3/17/2018.
 */

public class TrainingViewHolder extends RecyclerView.ViewHolder {

    NetworkImageView img_foto_training;

    public TrainingViewHolder(View itemView) {
        super(itemView);
        img_foto_training = (NetworkImageView) itemView.findViewById(R.id.img_foto_training);
    }
}
