package com.example.osvaldoairon.app4so.ActivitysSecond;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osvaldoairon.app4so.R;
import com.example.osvaldoairon.app4so.Modelo.Municipios;
import com.example.osvaldoairon.app4so.Sqlite.HelperSQLMunicipios;
import com.example.osvaldoairon.app4so.Util.FontesTerceiras;

import java.util.ArrayList;

public class InfDetailsMunicipio extends AppCompatActivity {
    /*
    Garante que a intenção que vinher da classe de Informaçao
    seja totalmente visualizada aqui.
     */

    private Municipios municipios;
    private HelperSQLMunicipios helperSQLMunicipios;
    private Municipios primeiroMuncipio;
    private Municipios secundariosMunicipios;
    private TextView nomeCity;
    private TextView descricaoCity;
    private TextView fonte_inf;
    private TextView inforRelevante;
    private TextView localidade;
    private TextView estado;
    private int index;
    private TextView populacao;

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
        populacao =(TextView)findViewById(R.id.populacaoCidade);
     //   localidade=(TextView)findViewById(R.id.locate_inf);


       String position = getIntent().getStringExtra("detailMunicipio");
       if(position!=null){
           index = Integer.parseInt(position);
       }


       String primeiroPosition = getIntent().getStringExtra("position");
       if(primeiroPosition.equals("ok") && primeiroPosition != null){
           primeiroMuncipio = helperSQLMunicipios.getReturnList().get(0);
           nomeCity.setText(primeiroMuncipio.getNome()+","+primeiroMuncipio.getEstado());
           descricaoCity.setText(primeiroMuncipio.getDescricao());
           populacao.setText(String.valueOf(primeiroMuncipio.getPopulacao()) +" "+"Habitantes");

           descricaoCity.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
           inforRelevante.setText(primeiroMuncipio.getInformacoesRelevantes());
           inforRelevante.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
           fonte_inf.setText("Fonte de Informações: " +primeiroMuncipio.getFontes_informacoes());
           fonte_inf.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
       }else{
           if(index!=0){
               secundariosMunicipios = helperSQLMunicipios.getReturnList().get(index);
               nomeCity.setText(secundariosMunicipios.getNome()+","+secundariosMunicipios.getEstado());
               populacao.setText(String.valueOf(secundariosMunicipios.getPopulacao()) +" "+"Habitantes");

               fonte_inf.setText("Fonte de Informações: " +secundariosMunicipios.getFontes_informacoes());
               fonte_inf.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
               descricaoCity.setText(secundariosMunicipios.getDescricao());
               descricaoCity.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
               inforRelevante.setText(secundariosMunicipios.getInformacoesRelevantes());
               inforRelevante.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));


           }
       }
       }


    @Override
    protected void onPause() {
        Log.d("INFDETAILS","InfDetailsPause()");
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
