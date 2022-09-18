package meu.primeiro.devprototipo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class LocationManager extends AppCompatActivity implements LocationListener {
    private android.location.LocationManager locationManager; // O Gerente de localização
    private LocationProvider locationProvider; // O provedor de localizações
    private GnssStatusCallback gnssStatusCallback; //O escutador do sistema GNSS
    private static final int REQUEST_LOCATION = 2;
    Button btnConfig, btnGNSS, btnInfo, btnHistorico, btnSalvar, btnGrafico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitvity_location_manager);
        locationManager = (android.location.LocationManager) getSystemService(LOCATION_SERVICE);
        locationProvider = locationManager.getProvider(android.location.LocationManager.GPS_PROVIDER);
        ativaGNSS();

        btnConfig = findViewById(R.id.btnConfig);
        btnGNSS = findViewById(R.id.btnGNSS);
        btnInfo = findViewById(R.id.btnInfo);
        btnHistorico = findViewById(R.id.btnHistorico);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnGrafico = findViewById(R.id.bntGrafico);

        btnGrafico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent GraficoScreem = new Intent(getApplicationContext(), meu.primeiro.devprototipo.GNSS.class);
                startActivity(GraficoScreem);

            }
        });

        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ConfScreem = new Intent(getApplicationContext(), Conf.class);
                startActivity(ConfScreem);

            }
        });

        btnGNSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent GNSScreem = new Intent(getApplicationContext(), GNSS.class);
                startActivity(GNSScreem);

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
                    5 * 1000,
                    0.1f,
                    this);
            gnssStatusCallback = new GnssStatusCallback();
            locationManager.registerGnssStatusCallback(gnssStatusCallback);
        } else {
            // Solicite a permissão
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
    }

    private void desativaGNSS() {
        locationManager.removeUpdates(this);
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

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    private class GnssStatusCallback extends GnssStatus.Callback {
        public GnssStatusCallback() {
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
            TextView tv_gnss = (TextView) findViewById(R.id.tv_gnssInfo);
            String mens = "                        Dados do Sitema de Posicionamento\n";
            if (status != null) {
                mens += "                                    Número de Satélites:  " + status.getSatelliteCount() + "\n\n";
                for (int i = 0; i < status.getSatelliteCount(); i++) {
                    mens += "SVID=" + status.getSvid(i) + "-" + status.getConstellationType(i) +
                            "Azi=" + status.getAzimuthDegrees(i) +
                            "Elev=" + status.getElevationDegrees(i) +
                            "Used in Fix" + status.usedInFix(i) +
                            "SNR=" + status.getCn0DbHz(i) + "|X|\n";
                }
            } else {
                mens += "GNSS Não disponível";
            }
            tv_gnss.setText(mens);
        }
    }
}