package meu.primeiro.devprototipo;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Map;

public class LocationsTable extends AppCompatActivity {

    private static ArrayList<Map> locations = new ArrayList<>();
    private ListView lv_savedLocations;

    Button btnConfig, btnGNSS, btnInfo, btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations_table);

        btnConfig = findViewById(R.id.btnConfig);
        btnGNSS = findViewById(R.id.btnGNSS);
        btnInfo = findViewById(R.id.btnInfo);
        btnSalvar = findViewById(R.id.btnSalvar);

        lv_savedLocations = (ListView) findViewById(R.id.lv_savedLocations);
        Firebase locs = new Firebase();
        locations = locs.getLocations();
        lv_savedLocations.setAdapter(new ArrayAdapter<Map>(this, android.R.layout.simple_list_item_1, locations));

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
                Intent MapScreem = new Intent(getApplicationContext(), meu.primeiro.devprototipo.Map.class);
                startActivity(MapScreem);
            }
        });
    }
}








