package bleizing.riva.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import bleizing.riva.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailLukaFragment extends Fragment {


    public DetailLukaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_luka, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayout linear_diabetes = (LinearLayout) getActivity().findViewById(R.id.linear_diabetes);

        int i = 0;

        Bundle args = getArguments();
        if (args != null) {
            i = args.getInt("luka");
        }

        if (i != 0) {
            switch (i) {
                case 1:
                    linear_diabetes.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    break;
            }
        }
    }
}
