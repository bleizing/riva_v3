package bleizing.riva.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import bleizing.riva.R;
import bleizing.riva.activity.RumatActivity;
import bleizing.riva.model.Lokasi;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailRumatFragment extends Fragment implements OnMapReadyCallback {


    GoogleMap mMap;
    private Double lat, lng;

    private Lokasi lokasi;

    private TextView tv_rumat_cabang;
    private TextView tv_rumat_klinik;

    public DetailRumatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lat = -6.175206;
        lng = 106.827131;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_rumat, container, false);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SupportMapFragment fragment = new SupportMapFragment();
        transaction.add(R.id.mapView, fragment);
        transaction.commit();
        fragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

//        LatLng b3 = new LatLng(-6.176712, 106.873415);
        if (lokasi != null) {
            LatLng latLng = new LatLng(lokasi.getLat(), lokasi.getLng());
            mMap.addMarker(new MarkerOptions().position(latLng).title(lokasi.getNama()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera( CameraUpdateFactory.zoomTo( 16.0f ) );
        }

        mMap.setMinZoomPreference(10.0f);
        mMap.setMaxZoomPreference(20.0f);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        lokasi = args.getParcelable("Lokasi");

        tv_rumat_cabang = (TextView) getActivity().findViewById(R.id.tv_rumat_cabang);
        tv_rumat_klinik = (TextView) getActivity().findViewById(R.id.tv_rumat_klinik);

        // TODO : INI MASIH SALAH HARUSNYA CABANG
        tv_rumat_cabang.setText(lokasi.getNama());

        tv_rumat_klinik.setText(lokasi.getNama());

        LinearLayout linear_registrasi = (LinearLayout) getActivity().findViewById(R.id.linear_registrasi);
        linear_registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RumatActivity) getActivity()).changeToRegistrasiFragment(lokasi.getId());
            }
        });

        ImageView img_phone = (ImageView) getActivity().findViewById(R.id.img_phone);
        img_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lokasi.getNoTelp().equals("")) {
                    Toast.makeText(getActivity(), "Sementara Layanan Ini Belum Dapat Diakses", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + lokasi.getNoTelp()));
                    startActivity(intent);
                }
            }
        });

        ImageView img_share = (ImageView) getActivity().findViewById(R.id.img_share);
        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_share);

                dialog.show();
            }
        });
    }
}
