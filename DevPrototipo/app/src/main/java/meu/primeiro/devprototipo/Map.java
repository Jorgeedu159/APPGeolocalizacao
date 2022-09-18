package meu.primeiro.devprototipo;


import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;

//import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;

import android.os.Build;
import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
import android.view.View;

//import android.view.animation.LinearInterpolator;
import android.widget.Button;
//import android.widget.EditText;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

//import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.HashMap;
//import java.util.List;

import meu.primeiro.devprototipo.databinding.ActivityMapBinding;

public class Map extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMyLocationButtonClickListener {

    private GoogleMap mMap;
    private ActivityMapBinding binding;
    private static final int REQUEST_LAST_LOCATION = 1;
    private static final int REQUEST_LOCATION_UPDATES = 2;
    private SharedPreferences sharedPrefs;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private Circle circle = null;
    // private CameraPosition cameraPosition;
    private String TAG = "HomeActivity";
    private Marker marker;

    double latitudeConfere = 0;
    double longitudeConfere = 0;
    int contador = 1;

//    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    Button attInfo, stop, btnConfig, btnGNSS, btnInfo, btnHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.Body.getRootView());

        attInfo = (Button) findViewById(R.id.attInfo);
        stop = (Button) findViewById(R.id.stop);
        btnConfig = findViewById(R.id.btnConfig);
        btnGNSS = findViewById(R.id.btnGNSS);
        btnInfo = findViewById(R.id.btnInfo);
        btnHistorico = findViewById(R.id.btnHistorico);

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

        attInfo.setOnClickListener(this);
        stop.setOnClickListener(this);

        /*  btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MenuScreem = new Intent(getApplicationContext(), Menu.class);
                startActivity(MenuScreem);
            }
        });
         */

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.attInfo)
            startLocationUpdates();
        if (view.getId() == R.id.stop)
            stopLocationUpdates();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        habilitaMyLocation();
        UiSettings mapUI = mMap.getUiSettings();
        mapUI.setZoomControlsEnabled(true);
        sharedPrefs = getSharedPreferences("MySettings", Context.MODE_PRIVATE);

        int tra = sharedPrefs.getInt("Trafego", 1);
        int tipo = sharedPrefs.getInt("Tipo", 1);
        int orient = sharedPrefs.getInt("Orientação", 1);
        int vel = sharedPrefs.getInt("Velocidade", 1);

        if (tra == 1) {
            googleMap.setTrafficEnabled(false);
        } else {
            googleMap.setTrafficEnabled(true);
        }
        switch (tipo) {
            case 1:
                tipo = GoogleMap.MAP_TYPE_NORMAL;
                googleMap.setMapType(tipo);
                break;
            case 2:
                tipo = GoogleMap.MAP_TYPE_SATELLITE;
                googleMap.setMapType(tipo);
                break;
        }
        switch (orient) {
            case 1:
                mapUI.setRotateGesturesEnabled(true);
                break;
            case 2:
            case 3:
                mapUI.setRotateGesturesEnabled(false);
                break;
        }

        // Add a marker in Sydney and move the camera
        LatLng Unifacs = new LatLng(-12.97938, -38.50982);

        Marker marker = mMap.addMarker(new MarkerOptions().position(Unifacs).snippet("Viva a Universidade").title("UNIFACS"));
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.agulha2));
        marker.setAnchor(0.5f, 0.5f);
        marker.setRotation(0);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Unifacs, 15.0f));
    }

    public void myMarker(Location location) {

        int orient = sharedPrefs.getInt("Orientação", 1);

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        CameraPosition camera = new CameraPosition.Builder()
                .target(latLng)
                .zoom(17.5f)
                .bearing(location.getBearing())
                .tilt(10)
                .build();

        if (marker == null) {

            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .flat(true)
                    .zIndex(1)
                    .anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.car01))
                    .rotation(location.getBearing());
            marker = mMap.addMarker(markerOptions);
            if (orient != 3) {
                markerOptions.rotation(location.getBearing());
                marker.setFlat(true);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
            } else {
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));
                marker.setRotation(location.getBearing());
            }
        } else {
            marker.setPosition(latLng);
        }
        if (orient != 3) {
            marker.setRotation(location.getBearing());
            marker.setFlat(true);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
        } else {
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));
            marker.setRotation(location.getBearing());
        }
        if (circle != null) {
            circle.remove();
        }

        circle = mMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(location.getAccuracy())
                .strokeColor(Color.red(125))
                .fillColor(Color.argb(128, 0, 0, 125)));
    }

    private void startLocationUpdates() {
        // Se a app já possui a permissão, ativa a camada de localização
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            // A permissão foi dada
            // Cria o cliente FusedLocation
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            // Configura solicitações de localização
            mLocationRequest = com.google.android.gms.location.LocationRequest.create();
            mLocationRequest.setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(5 * 1000);
            mLocationRequest.setFastestInterval(1 * 1000);
            // Programa o evento a ser chamado em intervalo regulares de tempo
            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    Location location = locationResult.getLastLocation();
                    atualizaLocationUpdatesView(location);

                    myMarker(location);
                }
            };

        } else {
            // Solicite a permissão
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_UPDATES);
        }
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

    private void stopLocationUpdates() {
        if (mFusedLocationProviderClient != null)
            mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                atualizaLastLocationView(location);
                habilitaMyLocation();
            }
        });
    }

    private void atualizaLocationUpdatesView(Location location) {
        TextView tv_lu = (TextView) findViewById(R.id.StatusBar);

        Date date = new Date();
        Date newDate = new Date(date.getTime() + (24 * 60 * 60));
        SimpleDateFormat dt = new SimpleDateFormat("'D: 'dd/MM/yyyy | 'H: 'HH:mm:ss");
        String Data = dt.format(newDate);

        int coor = sharedPrefs.getInt("Coordenadas", 1);
        int vel = sharedPrefs.getInt("Velocidade", 1);
        String tipVel;
        Float milhasSegundos = location.getSpeed();
        int velocimetro;
        int FORMAT_DEGREES;
        int FORMAT_MINUTES;
        int FORMAT_SECONDS;

        if (vel == 1) {
            tipVel = "km/h";
            velocimetro = (int) (milhasSegundos * 3.6);
        } else {
            tipVel = "Mph";
            velocimetro = (int) (milhasSegundos * 2.23694);
        }

        String mens = "Dados da posição atual:\n";
        if (location != null) {

            switch (coor) {
                case 1:
                    mens += String.valueOf("Latitude(graus)= "
                            + Location.convert(+location.getLatitude(), Location.FORMAT_DEGREES)) + "\n"
                            + String.valueOf("Longitude(graus)= "
                            + Location.convert(+location.getLongitude(), Location.FORMAT_DEGREES)) + "\n"
                            + String.valueOf("Velocidade =" + velocimetro + tipVel) + "\n"
                            + String.valueOf("Rumo(graus)= " + location.getBearing()) + "\n"
                            + String.valueOf("Acuracia(metros)= " + location.getAccuracy());

                    DatabaseReference referencia = FirebaseDatabase.getInstance().getReference().child("Ultima localização (Riiki)").push();

                    referencia.child("Data | Hora").setValue(Data);
                    referencia.child("Latitude").setValue(Location.convert(location.getLatitude(), Location.FORMAT_DEGREES));
                    referencia.child("Longitude").setValue(Location.convert(location.getLongitude(), Location.FORMAT_DEGREES));

                    saveToFirebase(location);

                    break;

                case 2:
                    mens += String.valueOf("Latitude(minuto)= "
                            + Location.convert(+location.getLatitude(), Location.FORMAT_MINUTES)) + "\n"
                            + String.valueOf("Longitude(minuto)= "
                            + Location.convert(+location.getLongitude(), Location.FORMAT_MINUTES)) + "\n"
                            + String.valueOf("Velocidade =" + velocimetro + tipVel) + "\n"
                            + String.valueOf("Rumo(graus)= " + location.getBearing()) + "\n"
                            + String.valueOf("Acuracia(metros)= " + location.getAccuracy());
                    break;

                case 3:
                    mens += String.valueOf("Latitude(segundo)= "
                            + Location.convert(+location.getLatitude(), Location.FORMAT_SECONDS)) + "\n"
                            + String.valueOf("Longitude(segundo)= "
                            + Location.convert(+location.getLongitude(), Location.FORMAT_SECONDS)) + "\n"
                            + String.valueOf("Velocidade =" + velocimetro + tipVel) + "\n"
                            + String.valueOf("Rumo(graus)= " + location.getBearing()) + "\n"
                            + String.valueOf("Acuracia(metros)= " + location.getAccuracy());
                    break;
            }
        } else {
            mens += "Não coletando informações de atualização";
        }
        tv_lu.setText(mens);
    }

    private void atualizaLastLocationView(Location location) {
        TextView tv_ll = (TextView) findViewById(R.id.StatusBar);

//        Date date = new Date();
//        Date newDate = new Date(date.getTime() + (24 * 60 * 60));
//        SimpleDateFormat dt = new SimpleDateFormat("'D: 'dd/MM/yyyy | 'H: 'HH:mm:ss");
//        String Data = dt.format(newDate);

        int coor = sharedPrefs.getInt("Coordenadas", 1);
        int vel = sharedPrefs.getInt("Velocidade", 1);
        String tipVel;
        Float milhasSegundos = location.getSpeed();
        int velocimetro;
        int FORMAT_DEGREES;
        int FORMAT_MINUTES;
        int FORMAT_SECONDS;

        if (vel == 1) {
            tipVel = "km/h";
            velocimetro = (int) (milhasSegundos * 3.6);
        } else {
            tipVel = "Mph";
            velocimetro = (int) (milhasSegundos * 2.23694);
        }

        String mens = "Dados da ultima posição:\n";
        if (location != null) {

            switch (coor) {
                case 1:

                    mens += String.valueOf("Latitude(graus)= "
                            + Location.convert(+location.getLatitude(), Location.FORMAT_DEGREES)) + "\n"
                            + String.valueOf("Longitude(graus)= "
                            + Location.convert(+location.getLongitude(), Location.FORMAT_DEGREES)) + "\n"
                            + String.valueOf("Velocidade =" + velocimetro + tipVel) + "\n"
                            + String.valueOf("Rumo(graus)= " + location.getBearing()) + "\n"
                            + String.valueOf("Acuracia(metros)= " + location.getAccuracy());

//                    DatabaseReference referencia = FirebaseDatabase.getInstance().getReference().child("Ultima localização").push();
//
//                    referencia.child("Data | Hora").setValue(Data);
//                    referencia.child("Latitude").setValue(Location.convert(location.getLatitude(), Location.FORMAT_DEGREES));
//                    referencia.child("Longitude").setValue(Location.convert(location.getLongitude(), Location.FORMAT_DEGREES));
                    break;
                case 2:
                    mens += String.valueOf("Latitude(minuto)= "
                            + Location.convert(+location.getLatitude(), Location.FORMAT_MINUTES)) + "\n"
                            + String.valueOf("Longitude(minuto)= "
                            + Location.convert(+location.getLongitude(), Location.FORMAT_MINUTES)) + "\n"
                            + String.valueOf("Velocidade =" + velocimetro + tipVel) + "\n"
                            + String.valueOf("Rumo(graus)= " + location.getBearing()) + "\n"
                            + String.valueOf("Acuracia(metros)= " + location.getAccuracy());
                    break;
                case 3:
                    mens += String.valueOf("Latitude(segundo)= "
                            + Location.convert(+location.getLatitude(), Location.FORMAT_SECONDS)) + "\n"
                            + String.valueOf("Longitude(segundo)= "
                            + Location.convert(+location.getLongitude(), Location.FORMAT_SECONDS)) + "\n"
                            + String.valueOf("Velocidade =" + velocimetro + tipVel) + "\n"
                            + String.valueOf("Rumo(graus)= " + location.getBearing()) + "\n"
                            + String.valueOf("Acuracia(metros)= " + location.getAccuracy());
                    break;
            }
        }
        tv_ll.setText(mens);
    }

    private void habilitaMyLocation() {

        // Registra esta atividade como escutadora do click no botão de localização
        mMap.setOnMyLocationButtonClickListener(this);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            // A permissão foi dada
            mMap.setMyLocationEnabled(true);

        } else {
            // Solicite a permissão
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LAST_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LAST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                // O usuário acabou de dar a permissão
                habilitaMyLocation();

            } else {
                // O usuário não deu a permissão solicitada
                Toast.makeText(this, "Sem permissão para mostrar sua localização",
                        Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == REQUEST_LOCATION_UPDATES) {
            if (grantResults.length == 1 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                // O usuário acabou de dar a permissão
                startLocationUpdates();
            } else {
                // O usuário não deu a permissão solicitada
                Toast.makeText(this, "Sem permissão para mostrar atualizações da sua localização",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

//    public void inserirNoBanco(Location location){
//        if ((latitudeConfere != location.getLatitude())&& (longitudeConfere!=location.getLongitude())){
//            LocationData locationData = new LocationData(location.getLatitude(), location.getLongitude());
//            referencia.child("HISTORICO").child(String.valueOf(contador)).setValue(locationData);
//            referencia.child("HISTORICO").child("Contador").setValue(contador);
//            contador ++;
//        }
//        latitudeConfere = location.getLatitude();
//        longitudeConfere = location.getLongitude();
//    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveToFirebase(Location location){

        Firebase fire = new Firebase();
        fire.saveToFirebase(location);

    }

}