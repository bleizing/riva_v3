package bleizing.riva.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import bleizing.riva.R;
import bleizing.riva.activity.BonusReferensiActivity;
import bleizing.riva.activity.DressingActivity;
import bleizing.riva.activity.EdukasiActivity;
import bleizing.riva.activity.HomecareActivity;
import bleizing.riva.activity.JurnalGdsActivity;
import bleizing.riva.activity.LoginActivity;
import bleizing.riva.activity.RumatActivity;
import bleizing.riva.activity.SuplemenActivity;
import bleizing.riva.activity.TrainingActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageView img_login = (ImageView) getActivity().findViewById(R.id.img_login);
        img_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        ImageView img_homecare = (ImageView) getActivity().findViewById(R.id.img_homecare);
        img_homecare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomecareActivity.class);
                startActivity(intent);
            }
        });

        ImageView img_lokasi_rumat = (ImageView) getActivity().findViewById(R.id.img_lokasi_rumat);
        img_lokasi_rumat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RumatActivity.class);
                startActivity(intent);
            }
        });

        ImageView img_dressing = (ImageView) getActivity().findViewById(R.id.img_dressing);
        img_dressing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DressingActivity.class);
                startActivity(intent);
            }
        });

        ImageView img_suplemen = (ImageView) getActivity().findViewById(R.id.img_suplemen);
        img_suplemen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SuplemenActivity.class);
                startActivity(intent);
            }
        });

        ImageView img_edukasi = (ImageView) getActivity().findViewById(R.id.img_edukasi);
        img_edukasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EdukasiActivity.class);
                startActivity(intent);
            }
        });

        ImageView img_training = (ImageView) getActivity().findViewById(R.id.img_training);
        img_training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TrainingActivity.class);
                startActivity(intent);
            }
        });

        ImageView img_bonus_referensi = (ImageView) getActivity().findViewById(R.id.img_bonus_referensi);
        img_bonus_referensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BonusReferensiActivity.class);
                startActivity(intent);
            }
        });

        ImageView img_jurnal_gds = (ImageView) getActivity().findViewById(R.id.img_jurnal_gds);
        img_jurnal_gds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), JurnalGdsActivity.class);
                startActivity(intent);
            }
        });
    }
}
