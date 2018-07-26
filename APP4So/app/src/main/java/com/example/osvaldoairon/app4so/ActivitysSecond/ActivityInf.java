package com.example.osvaldoairon.app4so.ActivitysSecond;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.osvaldoairon.app4so.BaseAdapter.CoordenadasAdapterCidades;
import com.example.osvaldoairon.app4so.Fragments.FragmentInfCidade;
import com.example.osvaldoairon.app4so.Fragments.FragmentInfTurismo;
import com.example.osvaldoairon.app4so.Modelo.AtrativosTuristicos;
import com.example.osvaldoairon.app4so.Modelo.Coordenadas;
import com.example.osvaldoairon.app4so.Modelo.Municipios;
import com.example.osvaldoairon.app4so.R;
import com.example.osvaldoairon.app4so.adapterF.FragePageAdapterF;
import com.example.osvaldoairon.app4so.rest.CriarConexaoMunicipios;

import java.net.URISyntaxException;
import java.util.ArrayList;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntent;
public class ActivityInf extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static ArrayList<Municipios> list_cidades ;
    private static ArrayList<AtrativosTuristicos> list_pontos ;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf);

        /*

        --PASSAVA A LISTA DE DE OBJETOS DO TIPO MUNICIPIO E ATRATIVO VIA
         INTENT NA CLASSE MAIN
        -----------------------------------------------------------------
        OPTEI POR CARREGAR ESSA LISTA DIRETO DO BANCO AO INVES DE PASSAR VIA INTENT;

         list_cidades = new ArrayList<Municipios>();
        list_pontos = new ArrayList<AtrativosTuristicos>();
        list_cidades = (ArrayList<Municipios>) getIntent().getSerializableExtra("arrayCidades");
        list_pontos = (ArrayList<AtrativosTuristicos>) getIntent().getSerializableExtra("arrayPontos");

        Toast.makeText(this, ""+list_pontos.size(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "x"+list_cidades.size(), Toast.LENGTH_SHORT).show();


        if(list_pontos!=null && list_cidades!=null){
            FragmentInfTurismo f2 = new FragmentInfTurismo();
            f2.recebeArray(list_pontos);
            FragmentInfCidade f1 = new FragmentInfCidade();
            f1.recebeArray(list_cidades);

        }else{
            Toast.makeText(this, "Nenhuma informação disponivel", Toast.LENGTH_SHORT).show();
        }
         */
        setupViewPager();


    }

    private  void setupViewPager(){


       // setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);



        viewPager.setAdapter(new FragePageAdapterF(getSupportFragmentManager(), getResources().getStringArray(R.array.secoes)));

        tabLayout.setupWithViewPager(viewPager);

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
