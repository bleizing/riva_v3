package bleizing.riva.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import bleizing.riva.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageView img_login = (ImageView) findViewById(R.id.img_login);
        img_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        ImageView img_homecare = (ImageView) findViewById(R.id.img_homecare);
        img_homecare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, HomecareActivity.class);
                startActivity(intent);
            }
        });

        ImageView img_lokasi_rumat = (ImageView) findViewById(R.id.img_lokasi_rumat);
        img_lokasi_rumat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, RumatActivity.class);
                startActivity(intent);
            }
        });

        ImageView img_dressing = (ImageView) findViewById(R.id.img_dressing);
        img_dressing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, DressingActivity.class);
                startActivity(intent);
            }
        });

        ImageView img_suplemen = (ImageView) findViewById(R.id.img_suplemen);
        img_suplemen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SuplemenActivity.class);
                startActivity(intent);
            }
        });

        ImageView img_edukasi = (ImageView) findViewById(R.id.img_edukasi);
        img_edukasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, EdukasiActivity.class);
                startActivity(intent);
            }
        });

        ImageView img_training = (ImageView) findViewById(R.id.img_training);
        img_training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, TrainingActivity.class);
                startActivity(intent);
            }
        });

        ImageView img_bonus_referensi = (ImageView) findViewById(R.id.img_bonus_referensi);
        img_bonus_referensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, BonusReferensiActivity.class);
                startActivity(intent);
            }
        });

        ImageView img_jurnal_gds = (ImageView) findViewById(R.id.img_jurnal_gds);
        img_jurnal_gds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, JurnalGdsActivity.class);
                startActivity(intent);
            }
        });
    }
}
