package meu.primeiro.devprototipo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class Conf extends AppCompatActivity {
    RadioGroup GroupCO, GroupUV;
    private RadioButton RadioDC, RadioMN, RadioSG;
    private RadioButton RadioKM, RadioMPH;
    private RadioButton RadioNH, RadioNP, RadioCP;
    private RadioButton RadioVT, RadioIS;
    SharedPreferences sharedPreferences;
    private Switch btnSW;

    Button btnConfig, btnGNSS, btnInfo, btnHistorico, btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf);
        setTitle("CONFIGURAÇÃO");


        btnGNSS = findViewById(R.id.btnGNSS);
        btnInfo = findViewById(R.id.btnInfo);
        btnHistorico = findViewById(R.id.btnHistorico);
        btnSalvar = findViewById(R.id.btnSalvar);

        sharedPreferences = getSharedPreferences("MySettings", MODE_PRIVATE);
        int coor = sharedPreferences.getInt("Coordenadas", 3);
        int vel = sharedPreferences.getInt("Velocidade", 2);
        int tipo = sharedPreferences.getInt("Tipo", 2);
        int ori = sharedPreferences.getInt("Orientação", 1);
        int tra = sharedPreferences.getInt("Trafego", 1);

        RadioDC = findViewById(R.id.RadioDC);
        RadioMN = findViewById(R.id.RadioMN);
        RadioSG = findViewById(R.id.RadioSG);
        switch (coor) {
            case 1:
                RadioDC.setChecked(true);
                break;
            case 2:
                RadioMN.setChecked(true);
                break;
            case 3:
                RadioSG.setChecked(true);
                break;
        }

        RadioKM = findViewById(R.id.RadioKM);
        RadioMPH = findViewById(R.id.RadioMPH);
        switch (vel) {
            case 1:
                RadioKM.setChecked(true);
                break;
            case 2:
                RadioMPH.setChecked(true);
                break;
        }

        RadioNH = findViewById(R.id.RadioNH);
        RadioNP = findViewById(R.id.RadioNP);
        RadioCP = findViewById(R.id.RadioCP);
        switch (ori) {
            case 1:
                RadioNH.setChecked(true);
                break;
            case 2:
                RadioNP.setChecked(true);
                break;
            case 3:
                RadioCP.setChecked(true);
        }

        RadioVT = findViewById(R.id.RadioVT);
        RadioIS = findViewById(R.id.RadioIS);
        switch (tipo) {
            case 1:
                RadioVT.setChecked(true);
                break;
            case 2:
                RadioIS.setChecked(true);
                break;
        }

        btnSW = findViewById(R.id.btnSW);
        switch (tra) {
            case 1:
                btnSW.setChecked(false);
                break;
            case 2:
                btnSW.setChecked(true);
                break;
        }
        RadioGroup groupCoor = findViewById(R.id.GroupCO);
        groupCoor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup groupCoor, int checkedId) {
                if (checkedId == R.id.RadioDC) {
                    SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                    sharedPreferencesEditor.putInt("Coordenadas", 1);
                    sharedPreferencesEditor.commit();
                } else if (checkedId == R.id.RadioMN) {
                    SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                    sharedPreferencesEditor.putInt("Coordenadas", 2);
                    sharedPreferencesEditor.commit();
                } else if (checkedId == R.id.RadioSG) {
                    SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                    sharedPreferencesEditor.putInt("Coordenadas", 3);
                    sharedPreferencesEditor.commit();
                }

            }
        });
        RadioGroup groupVel = findViewById(R.id.GroupUV);
        groupVel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup groupVel, int checkedId) {
                if (checkedId == R.id.RadioKM) {
                    SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                    sharedPreferencesEditor.putInt("Velocidade", 1);
                    sharedPreferencesEditor.commit();
                } else if (checkedId == R.id.RadioMPH) {
                    SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                    sharedPreferencesEditor.putInt("Velocidade", 2);
                    sharedPreferencesEditor.commit();
                }
            }
        });
        RadioGroup groupOri = findViewById(R.id.GroupOM);
        groupOri.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup groupOri, int checkedId) {
                if (checkedId == R.id.RadioNH) {
                    SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                    sharedPreferencesEditor.putInt("Orientação", 1);
                    sharedPreferencesEditor.commit();
                } else if (checkedId == R.id.RadioNP) {
                    SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                    sharedPreferencesEditor.putInt("Orientação", 2);
                    sharedPreferencesEditor.commit();
                } else if (checkedId == R.id.RadioCP) {
                    SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                    sharedPreferencesEditor.putInt("Orientação", 3);
                    sharedPreferencesEditor.commit();
                }
            }
        });
        RadioGroup groupTipo = findViewById(R.id.GroupMP);
        groupTipo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup groupTipo, int checkedId) {
                if (checkedId == R.id.RadioVT) {
                    SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                    sharedPreferencesEditor.putInt("Tipo", 1);
                    sharedPreferencesEditor.commit();
                } else if (checkedId == R.id.RadioIS) {
                    SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                    sharedPreferencesEditor.putInt("Tipo", 2);
                    sharedPreferencesEditor.commit();
                }
            }
        });
        Switch switchTra = findViewById(R.id.btnSW);
        switchTra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked == false) {
                    SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                    sharedPreferencesEditor.putInt("Trafego", 1);
                    sharedPreferencesEditor.commit();
                } else if (isChecked) {
                    SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                    sharedPreferencesEditor.putInt("Trafego", 2);
                    sharedPreferencesEditor.commit();
                }
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
}