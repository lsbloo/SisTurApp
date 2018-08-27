package com.example.osvaldoairon.app4so.ActivitysSecond;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.osvaldoairon.app4so.Fragments.MapGoogleActivity;
import com.example.osvaldoairon.app4so.MainActivity;
import com.example.osvaldoairon.app4so.Modelo.Coordenadas;
import com.example.osvaldoairon.app4so.R;
import com.example.osvaldoairon.app4so.Sqlite.HelperSQLAtrativos;
import com.example.osvaldoairon.app4so.Sqlite.HelperSQLMunicipios;

public class ActivityConf extends AppCompatActivity {
    /*
    Criar opçao de mudar o tipo do mapa Google-Act;
    Utilizar Spinner para carregar os tipos de mapas;
    alteração no fragment mapGoogle;
     */
    private HelperSQLMunicipios helpercity;
    private HelperSQLAtrativos helperatrativo;
    private Spinner spinnerConf;
    private ArrayAdapter adapter;
    private RadioGroup radio;
    private static int type;
    private Button btn_resetar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf);

        radio=(RadioGroup)findViewById(R.id.radio);
        type=0;
        btn_resetar=(Button)findViewById(R.id.btn_resetdb);
        helpercity = new HelperSQLMunicipios(ActivityConf.this);
        helperatrativo = new HelperSQLAtrativos(ActivityConf.this);


        btn_resetar.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {

                                               new AlertDialog.Builder(ActivityConf.this).setTitle("Resetar Banco de Dados").setMessage("Confirmar Operação?").setPositiveButton("sim", new DialogInterface.OnClickListener() {
                                                   @Override
                                                   public void onClick(DialogInterface dialog, int which) {
                                                       helpercity.reset();
                                                       helperatrativo.reset();

                                                       Toast.makeText(ActivityConf.this, "Resetado!", Toast.LENGTH_SHORT).show();
                                                   }
                                               }).setNegativeButton("não", new DialogInterface.OnClickListener() {
                                                   @Override
                                                   public void onClick(DialogInterface dialog, int which) {
                                                       Toast.makeText(ActivityConf.this, "Operação Cancelada!", Toast.LENGTH_SHORT).show();
                                                       finish();
                                                   }
                                               }).show();
                                           }
                                       });


                radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        int item = checkedId;

                        switch (item) {
                            case R.id.msimples:
                                Toast.makeText(ActivityConf.this, "Mapa simples adicionado", Toast.LENGTH_SHORT).show();
                                type = 1;
                                break;
                            case R.id.mapat:
                                Toast.makeText(ActivityConf.this, "Mapa terreno adicionado", Toast.LENGTH_SHORT).show();
                                type = 2;
                                break;
                            case R.id.mapaS:
                                Toast.makeText(ActivityConf.this, "Mapa Satelite adicionado", Toast.LENGTH_SHORT).show();
                                type = 3;
                                break;
                            case R.id.mapah:
                                Toast.makeText(ActivityConf.this, "Mapa hibrido adicionado", Toast.LENGTH_SHORT).show();
                                type = 4;
                                break;

                        }
                        if (type != 0) {
                            changeMap(type);
                            //Toast.makeText(ActivityConf.this, ""+type, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ActivityConf.this, "" + type, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }

            public void changeMap(int tipo) {
                switch (tipo) {
                    case 4:
                        Intent et = new Intent(ActivityConf.this, MainActivity.class);
                        et.putExtra("mapa", 4);
                        startActivity(et);
                        finish();
                        break;
                    case 3:
                        Intent at = new Intent(ActivityConf.this, MainActivity.class);
                        at.putExtra("mapa", 3);
                        startActivity(at);
                        finish();
                        break;
                    case 2:
                        Intent it = new Intent(ActivityConf.this, MainActivity.class);
                        it.putExtra("mapa", 2);
                        startActivity(it);
                        finish();
                        break;
                    case 1:
                        Intent ot = new Intent(ActivityConf.this, MainActivity.class);
                        ot.putExtra("mapa", 1);
                        startActivity(ot);
                        finish();
                        break;


                }
            }
        }

