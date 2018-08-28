package com.example.osvaldoairon.app4so.ActivitysSecond;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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
    private TextView nomeCity;
    private TextView descricaoCity;
    private TextView fonte_inf;
    private TextView inforRelevante;
    private TextView localidade;
    private TextView site;
    private TextView estado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_details_municipio);
        helperSQLMunicipios = new HelperSQLMunicipios(InfDetailsMunicipio.this);
        helperSQLMunicipios.recoverDataSQL();

        /*
        Widgets
         */
        nomeCity=(TextView)findViewById(R.id.nomecitydetail) ;
        descricaoCity=(TextView)findViewById(R.id.descriptionDetail);
        fonte_inf=(TextView)findViewById(R.id.fonte_details) ;
        inforRelevante=(TextView)findViewById(R.id.infrelevantedetail) ;
        localidade=(TextView)findViewById(R.id.locate_inf);
        site=(TextView)findViewById(R.id.site_inf);
        estado=(TextView)findViewById(R.id.estado_detail);

       municipios = (Municipios)getIntent().getSerializableExtra("detailMunicipio");
       String primeiroPosition = getIntent().getStringExtra("position");
       if(primeiroPosition.equals("ok") && primeiroPosition != null){
           primeiroMuncipio = helperSQLMunicipios.getReturnList().get(0);
           nomeCity.setText(primeiroMuncipio.getNome());
           descricaoCity.setText(primeiroMuncipio.getDescricao());
           estado.setText(primeiroMuncipio.getEstado());
       }else{
           if(municipios!=null){
               nomeCity.setText(municipios.getNome());
               estado.setText(municipios.getEstado());
               descricaoCity.setText(municipios.getDescricao());
               inforRelevante.setText(municipios.getInformacoesRelevantes());
               localidade.setText("Latitude: "+ municipios.getLatitude() + " \n Longitude: " +municipios.getLongitude());
               site.setText("Site: "+municipios.getSite());

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
