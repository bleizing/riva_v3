package bleizing.riva.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import bleizing.riva.R;
import bleizing.riva.activity.HomecareActivity;
import bleizing.riva.activity.RumatActivity;
import bleizing.riva.model.Model;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends Fragment {

    private HomecareActivity homecareActivity;
    private RumatActivity rumatActivity;

    private LinearLayout linear_perawat;
    private LinearLayout linear_laki;
    private LinearLayout linear_perempuan;

    public BookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (Model.getHomecareActivity() != null) {
            homecareActivity = Model.getHomecareActivity();
        } else {
            if (Model.getRumatActivity() != null) {
                rumatActivity = Model.getRumatActivity();
            }
        }

        if (rumatActivity != null) {
            ((RumatActivity) getActivity()).setActionBarTitle(getActivity().getResources().getString(R.string.homecare));
        }

        if (homecareActivity != null) {
            ((HomecareActivity) getActivity()).setActionBarTitle(getActivity().getResources().getString(R.string.homecare));
        }


        Button btn_order = (Button) getActivity().findViewById(R.id.btn_order);
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rumatActivity != null) {
                    ((RumatActivity) getActivity()).changeToPaymentFragment();
                }

                if (homecareActivity != null) {
                    ((HomecareActivity) getActivity()).changeToPaymentFragment();
                }
            }
        });

        linear_perawat = (LinearLayout) getActivity().findViewById(R.id.linear_perawat);
        linear_laki = (LinearLayout) getActivity().findViewById(R.id.linear_laki);
        linear_perempuan = (LinearLayout) getActivity().findViewById(R.id.linear_perempuan);

        linear_perawat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear_perawat.setVisibility(View.GONE);
                linear_laki.setVisibility(View.VISIBLE);
                linear_perempuan.setVisibility(View.VISIBLE);
            }
        });

        linear_laki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linear_perempuan.getVisibility() == View.GONE) {
                    linear_perempuan.setVisibility(View.VISIBLE);
                } else {
                    linear_perempuan.setVisibility(View.GONE);
                }
            }
        });

        linear_perempuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linear_laki.getVisibility() == View.GONE) {
                    linear_laki.setVisibility(View.VISIBLE);
                } else {
                    linear_laki.setVisibility(View.GONE);
                }
            }
        });
    }
}
