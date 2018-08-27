package com.example.osvaldoairon.app4so.ActivitysSecond;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.osvaldoairon.app4so.R;
import com.example.osvaldoairon.app4so.Modelo.Municipios;
import com.example.osvaldoairon.app4so.Sqlite.HelperSQLMunicipios;

import java.util.ArrayList;

public class InfDetailsMunicipio extends AppCompatActivity {
    /*
    Garante que a intenção que vinher da classe de Informaçao
    seja totalmente visualizada aqui.
     */

    private Municipios municipios;
    private HelperSQLMunicipios helperSQLMunicipios;
    private Municipios primeiroMuncipio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_details_municipio);
        helperSQLMunicipios = new HelperSQLMunicipios(InfDetailsMunicipio.this);
        helperSQLMunicipios.recoverDataSQL();

       municipios = (Municipios)getIntent().getSerializableExtra("detailMunicipio");
       String primeiroPosition = getIntent().getStringExtra("position");
       if(primeiroPosition.equals("ok") && primeiroPosition != null){
           primeiroMuncipio = helperSQLMunicipios.getReturnList().get(0);
       }else{
           if(municipios!=null){
               Toast.makeText(this, ""+municipios.getNome(), Toast.LENGTH_SHORT).show();
           }
       }



    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
