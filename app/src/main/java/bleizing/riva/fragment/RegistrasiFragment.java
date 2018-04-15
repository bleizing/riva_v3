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
public class RegistrasiFragment extends Fragment {

    private HomecareActivity homecareActivity;
    private RumatActivity rumatActivity;

    public RegistrasiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registrasi, container, false);
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

        Button btn_register = (Button) getActivity().findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rumatActivity != null) {
                    ((RumatActivity) getActivity()).changeToBookingFragment();
                }

                if (homecareActivity != null) {
                    ((HomecareActivity) getActivity()).changeToBookingFragment();
                }
            }
        });
    }
}
