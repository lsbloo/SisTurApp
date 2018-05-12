package com.example.osvaldoairon.app4so.ActivitysSecond;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.osvaldoairon.app4so.Fragments.MapGoogleActivity;
import com.example.osvaldoairon.app4so.MainActivity;
import com.example.osvaldoairon.app4so.R;

public class ActivityConf extends AppCompatActivity {
    /*
    Criar opçao de mudar o tipo do mapa Google-Act;
    Utilizar Spinner para carregar os tipos de mapas;
    alteração no fragment mapGoogle;
     */

    private Spinner spinnerConf;
    private ArrayAdapter adapter;
    private RadioGroup radio;
    private static int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf);

        radio=(RadioGroup)findViewById(R.id.radio);
        type=0;

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int item = checkedId;

                switch(item){
                    case R.id.msimples:
                        Toast.makeText(ActivityConf.this, "Mapa simples adicionado", Toast.LENGTH_SHORT).show();
                        type =1;
                        break;
                    case R.id.mapat:
                        Toast.makeText(ActivityConf.this, "Mapa terreno adicionado", Toast.LENGTH_SHORT).show();
                        type=2;
                        break;
                    case R.id.mapaS:
                        Toast.makeText(ActivityConf.this, "Mapa Satelite adicionado", Toast.LENGTH_SHORT).show();
                        type=3;
                        break;
                    case R.id.mapah:
                        Toast.makeText(ActivityConf.this, "Mapa hibrido adicionado", Toast.LENGTH_SHORT).show();
                        type=4;
                        break;

                }
                if(type != 0){
                    changeMap(type);
                    //Toast.makeText(ActivityConf.this, ""+type, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ActivityConf.this, ""+type, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void changeMap(int tipo){
        switch (tipo){
            case 4:
                Intent et = new Intent(ActivityConf.this,MainActivity.class);
                et.putExtra("mapa",4);
                startActivity(et);
                break;
            case 3:
                Intent at = new Intent(ActivityConf.this,MainActivity.class);
                at.putExtra("mapa",3);
                startActivity(at);
                break;
            case 2:
                Intent it = new Intent(ActivityConf.this,MainActivity.class);
                it.putExtra("mapa",2);
                startActivity(it);
                break;
            case 1:
                Intent ot = new Intent(ActivityConf.this,MainActivity.class);
                ot.putExtra("mapa",1);
                startActivity(ot);
                break;


        }
    }
}
