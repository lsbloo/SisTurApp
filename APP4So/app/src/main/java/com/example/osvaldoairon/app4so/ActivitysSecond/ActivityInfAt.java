package com.example.osvaldoairon.app4so.ActivitysSecond;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.osvaldoairon.app4so.BaseAdapter.AtrativosAdapter;
import com.example.osvaldoairon.app4so.R;
import com.example.osvaldoairon.app4so.Sqlite.HelperSQLAtrativos;

public class ActivityInfAt extends AppCompatActivity {

    private HelperSQLAtrativos helperSQLAtrativos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_at);


        helperSQLAtrativos = new HelperSQLAtrativos(this);
        helperSQLAtrativos.recoverDataSQLAtrativos();


        Toast.makeText(this, "Quantidade de Atrativos Turisticos: " + helperSQLAtrativos.getReturnList().size(), Toast.LENGTH_SHORT).show();

        ListView l1 = new ListView(ActivityInfAt.this);
        AtrativosAdapter atrativosAdapter = new AtrativosAdapter(this,helperSQLAtrativos.getReturnList());
        if (l1 != null) {
            l1.setAdapter(atrativosAdapter);
            setContentView(l1);

            l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final int i = position;
                    /*
                    captura o evento e joga ele em uma Activity do municipio correspondente
                    ;
                     */
                    new AlertDialog.Builder(ActivityInfAt.this).setTitle("Informações Detalhadas").setMessage("Deseja visualizar as informações do atrativos turisticos?").setPositiveButton("sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            listenEvent(i);

                        }
                    }).setNegativeButton("não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(ActivityInfAt.this, "Cancelado!", Toast.LENGTH_SHORT).show();

                        }
                    }).show();
                }
            });
        }


    }

    public void listenEvent(int position){
        /*
                            caso sim ! entra uma new intenção com os dados
                            do municipio correspondente;
        */

        if(position==0){
            Intent at = new Intent(ActivityInfAt.this, InfDetailsAt.class);
            at.putExtra("position","ok");
            startActivity(at);
        }else{
            Log.d("POSITION","POSITION"+position);
            Intent et = new Intent(ActivityInfAt.this, InfDetailsAt.class);
            et.putExtra("position","nao_ok");
            et.putExtra("detailAtrativo", String.valueOf(position));
            startActivity(et);

        }



    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("Verific At","VERIFIC AT");
        helperSQLAtrativos.limparArray();
        finish();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.v("ZUERA POW", "kapsapsk");
        super.onDestroy();
    }
    }



