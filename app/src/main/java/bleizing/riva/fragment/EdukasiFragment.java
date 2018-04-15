package bleizing.riva.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bleizing.riva.MyClickListener;
import bleizing.riva.R;
import bleizing.riva.RecyclerTouchListener;
import bleizing.riva.activity.EdukasiActivity;
import bleizing.riva.adapter.ArticleAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class EdukasiFragment extends Fragment {

    public EdukasiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edukasi, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView tv_article = (TextView) getActivity().findViewById(R.id.tv_article);
        tv_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EdukasiActivity) getActivity()).changeToArticleFragment();
            }
        });

        TextView tv_perawatan_luka = (TextView) getActivity().findViewById(R.id.tv_perawatan_luka);
        tv_perawatan_luka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EdukasiActivity) getActivity()).changeToLukaFragment();
            }
        });

        TextView tv_perawatan_kaki = (TextView) getActivity().findViewById(R.id.tv_perawatan_kaki);
        tv_perawatan_kaki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EdukasiActivity) getActivity()).changeToKakiFragment();
            }
        });
    }
}
