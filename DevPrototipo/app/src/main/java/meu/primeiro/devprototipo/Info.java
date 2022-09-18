package meu.primeiro.devprototipo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Info extends AppCompatActivity {

    Button btnConfig, btnGNSS, btnHistorico, btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setTitle("SOBRE");

        btnConfig = findViewById(R.id.btnConfig);
        btnGNSS = findViewById(R.id.btnGNSS);
        btnHistorico = findViewById(R.id.btnHistorico);
        btnSalvar = findViewById(R.id.btnSalvar);

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
}