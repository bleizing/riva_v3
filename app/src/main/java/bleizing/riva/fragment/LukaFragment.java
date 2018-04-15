package bleizing.riva.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import bleizing.riva.R;
import bleizing.riva.activity.EdukasiActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LukaFragment extends Fragment {


    public LukaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_luka, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((EdukasiActivity) getActivity()).setActionBarTitle("PERAWATAN LUKA");

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

        TextView tv_diabetes = (TextView) getActivity().findViewById(R.id.tv_diabetes);
        tv_diabetes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EdukasiActivity) getActivity()).changeToDetailLukaFragment(1);
            }
        });
    }
}
