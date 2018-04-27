package bleizing.riva.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bleizing.riva.R;
import bleizing.riva.activity.BonusReferensiActivity;
import bleizing.riva.model.Artikel;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailArticleFragment extends Fragment {

    private Artikel artikel;


    public DetailArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_article, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        artikel = args.getParcelable("article");

        TextView tv_judul = (TextView) getActivity().findViewById(R.id.tv_title_article);
        TextView tv_content = (TextView) getActivity().findViewById(R.id.tv_content_article);

        tv_judul.setText(artikel.getJudul());
        tv_content.setText(artikel.getIsi());
    }
}
