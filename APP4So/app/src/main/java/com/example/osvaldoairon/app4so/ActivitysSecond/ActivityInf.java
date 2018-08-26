package com.example.osvaldoairon.app4so.ActivitysSecond;

import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.osvaldoairon.app4so.BaseAdapter.CoordenadasAdapterCidades;
import com.example.osvaldoairon.app4so.Modelo.AtrativosTuristicos;
import com.example.osvaldoairon.app4so.Modelo.Municipios;
import com.example.osvaldoairon.app4so.R;
import com.example.osvaldoairon.app4so.Sqlite.HelperSQLMunicipios;
import com.example.osvaldoairon.app4so.adapterF.FragePageAdapterF;

import java.util.ArrayList;

public class ActivityInf extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static ArrayList<Municipios> list_cidades;
    private static ArrayList<AtrativosTuristicos> list_pontos;
    private HelperSQLMunicipios helperSQLMunicipios;

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
            try {
                finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

}




    private  void setupViewPager(){


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
