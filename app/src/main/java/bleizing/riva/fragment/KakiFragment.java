package bleizing.riva.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bleizing.riva.R;
import bleizing.riva.activity.EdukasiActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class KakiFragment extends Fragment {


    public KakiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kaki, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((EdukasiActivity) getActivity()).setActionBarTitle("PERAWATAN KAKI");
    }
}
