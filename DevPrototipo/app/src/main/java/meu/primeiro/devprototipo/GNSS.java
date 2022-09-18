package meu.primeiro.devprototipo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GNSS extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager; // O Gerente de localização
    private LocationProvider locationProvider; // O provedor de localizações
    private MyGnssStatusCallback gnssStatusCallback; //O escutador do sistema GNSS
    private static final int REQUEST_LOCATION = 2;
    private GNSSView gnssView;
    Button btnConfig, btnInfo, btnHistorico, btnSalvar, btnMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gnss);
        gnssView=findViewById(R.id.gnssview);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);


        ativaGNSS();

        setTitle("GNSS");

        btnMore = findViewById(R.id.bntMore);
        btnConfig = findViewById(R.id.btnConfig);
        btnInfo = findViewById(R.id.btnInfo);
        btnHistorico = findViewById(R.id.btnHistorico);
        btnSalvar = findViewById(R.id.btnSalvar);

        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ConfScreem = new Intent(getApplicationContext(), Conf.class);
                startActivity(ConfScreem);

            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent MoreScreem = new Intent(getApplicationContext(), meu.primeiro.devprototipo.LocationManager.class);
                startActivity(MoreScreem);

            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent InfoScreem = new Intent(getApplicationContext(), Info.class);
                startActivity(InfoScreem);

            }
        });

        btnHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent historicoScreem = new Intent(getApplicationContext(), Historico.class);
                startActivity(historicoScreem);
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MapScreem = new Intent(getApplicationContext(), Map.class);
                startActivity(MapScreem);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        desativaGNSS();
    }

    private void ativaGNSS() {
        // Se a app já possui a permissão, ativa a camada de localização
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(locationProvider.getName(),
                    5*1000,
                    0.1f,
                    (LocationListener) this);
            gnssStatusCallback = new MyGnssStatusCallback();
            locationManager.registerGnssStatusCallback(gnssStatusCallback);
        } else {
            // Solicite a permissão
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
    }

    private void desativaGNSS() {
        locationManager.unregisterGnssStatusCallback(gnssStatusCallback);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                // O usuário acabou de dar a permissão
                ativaGNSS();
            } else {
                // O usuário não deu a permissão solicitada
                Toast.makeText(this, "Sem permissão para acessar o sistema de posicionamento",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    private class MyGnssStatusCallback extends GnssStatus.Callback {
        public MyGnssStatusCallback() {
            super();
        }

        @Override
        public void onStarted() {
        }

        @Override
        public void onStopped() {
        }

        @Override
        public void onFirstFix(int ttffMillis) {
        }

        @Override
        public void onSatelliteStatusChanged(@NonNull GnssStatus status) {
            gnssView.onSatelliteStatusChanged(status);
            gnssView.invalidate();
        }
    }

}