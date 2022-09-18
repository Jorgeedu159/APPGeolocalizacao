package meu.primeiro.devprototipo;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Historico extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference database;

    Button btnConfig, btnGNSS, btnInfo, btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        setTitle("HISTORICO");

        Button buttonMap = (Button) findViewById(R.id.buttonMap);
        Button buttonLocations = (Button) findViewById(R.id.buttonLocations);
        Button buttonDelete = (Button) findViewById(R.id.buttonDelete);

        buttonMap.setOnClickListener(this);
        buttonLocations.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        btnConfig = findViewById(R.id.btnConfig);
        btnGNSS = findViewById(R.id.btnGNSS);
        btnInfo = findViewById(R.id.btnInfo);
        btnSalvar = findViewById(R.id.btnSalvar);

        database = FirebaseDatabase.getInstance().getReference("Ultima Localização");

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

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MapScreem = new Intent(getApplicationContext(), Map.class);
                startActivity(MapScreem);
            }
        });
    }

    @Override
    public void onClick (View view){
        switch (view.getId()) {
            case R.id.buttonMap:
                Intent z = new Intent(this, HistoryMaps.class);
                startActivity(z);
                break;
            case R.id.buttonLocations:
                Intent x = new Intent(this, LocationsTable.class);
                startActivity(x);
                break;
            case R.id.buttonDelete:
                System.out.println("Localização deletada");
                Firebase ref = new Firebase();
                ref.deleteLocs();
                Toast.makeText(this, "Localização deletada", Toast.LENGTH_SHORT).show();

                break;

        }
    }
}