package bleizing.riva.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import bleizing.riva.R;
import bleizing.riva.activity.HomecareActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomecareFragment extends Fragment {


    public HomecareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homecare, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button btn_booking_sekarang = (Button) getActivity().findViewById(R.id.btn_booking_sekarang);
        btn_booking_sekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomecareActivity) getActivity()).changeToBookingFragment();
            }
        });
    }
}
