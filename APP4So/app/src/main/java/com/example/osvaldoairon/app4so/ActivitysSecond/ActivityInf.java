package com.example.osvaldoairon.app4so.ActivitysSecond;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.osvaldoairon.app4so.BaseAdapter.CoordenadasAdapterCidades;
import com.example.osvaldoairon.app4so.BaseAdapter.ExpanAdapter;
import com.example.osvaldoairon.app4so.Modelo.AtrativosTuristicos;
import com.example.osvaldoairon.app4so.Modelo.Coordenadas;
import com.example.osvaldoairon.app4so.Modelo.Municipios;
import com.example.osvaldoairon.app4so.R;
import com.example.osvaldoairon.app4so.Sqlite.HelperSQLMunicipios;
import com.example.osvaldoairon.app4so.adapterF.FragePageAdapterF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityInf extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static ArrayList<Municipios> list_cidades;
    private static ArrayList<AtrativosTuristicos> list_pontos;
    private HelperSQLMunicipios helperSQLMunicipios;
    private ExpandableListView expandableListView;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_inf);

        helperSQLMunicipios = new HelperSQLMunicipios(this);
        helperSQLMunicipios.recoverDataSQL();

        Toast.makeText(this, "Quantidade de Municipios: " + helperSQLMunicipios.getReturnList().size(), Toast.LENGTH_SHORT).show();


        ListView l1 = new ListView(this);
        CoordenadasAdapterCidades adapterCidades = new CoordenadasAdapterCidades(helperSQLMunicipios.getReturnList(), this);
        if (l1 != null) {
            l1.setAdapter(adapterCidades);
            setContentView(l1);

            l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final int i = position;
                    /*
                    captura o evento e joga ele em uma webview do site do municipio correspondente;
                     */
                    new AlertDialog.Builder(ActivityInf.this).setTitle("Informações Detalhadas").setMessage("Deseja visualizar as informações do municipio?").setPositiveButton("sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        listenEvent(i);

                        }
                    }).setNegativeButton("não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(ActivityInf.this, "Cancelado!", Toast.LENGTH_SHORT).show();

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
            Intent at = new Intent(ActivityInf.this, InfDetailsMunicipio.class);
            at.putExtra("position","ok");
            startActivity(at);
        }else{
            Intent at = new Intent(ActivityInf.this, InfDetailsMunicipio.class);
            at.putExtra("position","nao_ok");
            at.putExtra("detailMunicipio", helperSQLMunicipios.getReturnList().get(position));
            startActivity(at);
        }



    }


    private  void setupViewPager(){

        /**
         *
         * 
         */



        // setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        /*
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);



        viewPager.setAdapter(new FragePageAdapterF(getSupportFragmentManager(), getResources().getStringArray(R.array.secoes)));

        tabLayout.setupWithViewPager(viewPager);
         */


    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("EU TO DOIDO", "eu to doido");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.v("ZUERA POW", "kapsapsk");
        super.onDestroy();
    }
}
