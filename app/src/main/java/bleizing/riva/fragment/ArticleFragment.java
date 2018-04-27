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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import java.util.ArrayList;

import bleizing.riva.MyClickListener;
import bleizing.riva.R;
import bleizing.riva.RecyclerTouchListener;
import bleizing.riva.activity.EdukasiActivity;
import bleizing.riva.adapter.ArticleAdapter;
import bleizing.riva.model.Artikel;
import bleizing.riva.model.Model;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFragment extends Fragment {

    private ArticleAdapter articleAdapter;

    private ArrayList<Artikel> artikelArrayList;

    public ArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        artikelArrayList = Model.getArtikelArrayList();

        if (artikelArrayList == null) {
            artikelArrayList = new ArrayList<>();
        }

        articleAdapter = new ArticleAdapter(artikelArrayList, getContext());

        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.article_recycler_view);
        recyclerView.setAdapter(articleAdapter);
        articleAdapter.notifyDataSetChanged();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new MyClickListener() {
            @Override
            public void onClick(View view, int position) {
                Artikel artikel = articleAdapter.getArtikelArrayList().get(position);
                ((EdukasiActivity) getActivity()).changeToDetailArticleFragment(artikel);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        ((EdukasiActivity) getActivity()).setActionBarTitle("ARTIKEL");

//        String url_article_rumat = "https://www.rumat-indonesia.com/menu/artikel.html";
//
//        WebView webView = (WebView) getActivity().findViewById(R.id.webview);
//
//        WebSettings webSettings = webView.getSettings();
//
//        webSettings.setJavaScriptEnabled(true);
//
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.clearCache(true);
//        webView.clearHistory();
//        webView.loadUrl(url_article_rumat);

//        LinearLayout linear_kotak1 = (LinearLayout) getActivity().findViewById(R.id.linear_kotak1);
//        LinearLayout linear_kotak2 = (LinearLayout) getActivity().findViewById(R.id.linear_kotak2);
//
//        linear_kotak1.setOnClickListener(onClickListener);
//        linear_kotak2.setOnClickListener(onClickListener);
    }

//    private View.OnClickListener onClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            ((EdukasiActivity) getActivity()).changeToDetailArticleFragment(1);
//        }
//    };
}
