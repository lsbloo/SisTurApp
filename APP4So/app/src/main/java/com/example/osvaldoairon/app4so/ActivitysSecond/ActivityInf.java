package com.example.osvaldoairon.app4so.ActivitysSecond;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private ArrayList<Municipios> list_cidades;
    private ArrayList<AtrativosTuristicos> list_pontos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf);

        list_cidades = (ArrayList<Municipios>) getIntent().getSerializableExtra("arrayCidades");
        list_pontos = (ArrayList<AtrativosTuristicos>) getIntent().getSerializableExtra("arrayPontos");

        if(list_pontos!=null){
            FragmentInfTurismo f2 = new FragmentInfTurismo();
            f2.recebeArray(list_pontos);

        }
        if (list_cidades != null) {

            FragmentInfCidade f1 = new FragmentInfCidade();
            f1.recebeArray(list_cidades);

        }


        getSupportActionBar().setHomeButtonEnabled(true);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        viewPager.setAdapter(new FragePageAdapterF(getSupportFragmentManager(), getResources().getStringArray(R.array.secoes)));

        tabLayout.setupWithViewPager(viewPager);





    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
}
