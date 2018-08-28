package com.example.osvaldoairon.app4so.ActivitysSecond;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.osvaldoairon.app4so.MainActivity;
import com.example.osvaldoairon.app4so.R;

public class SplashAct extends AppCompatActivity {

    private static int TEMPO=5000;
    private ProgressBar progressBar;
    private boolean cxtwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        progressBar=(ProgressBar)findViewById(R.id.progressSplash);
        exibir_Conexao(verificaConexao());
        Toast.makeText(SplashAct.this, "Bem vindo ao SisTur!", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                Intent i = new Intent(SplashAct.this, MainActivity.class);

                startActivity(i);

                // Fecha esta activity
                finish();
            }
        }, TEMPO);


    }
    public void exibir_Conexao(boolean conexao){
        if(!conexao){
            Toast.makeText(SplashAct.this, "SmartPhone não conectado a internet", Toast.LENGTH_SHORT).show();
        }
    }
    public  boolean verificaConexao() {
        boolean conectado;
        String saida = "Conexao Aparelho: ";

        // ConnectivityManager prove informacoes de conexoes do aparelho
        /*
        representado por um objeto NetworkInfo (INFORMACOES)
           o metodo connectManager.getNetworkInfo especifica o tipo de informaçao
           passado por uma constante(int)
           ConnectivyManager.TYPE_WIFI
         */

        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifi = conectivtyManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mob = conectivtyManager.getNetworkInfo(conectivtyManager.TYPE_MOBILE);

        if(wifi.isConnected()){
            saida += "wifi";
            conectado = true;
        }else if(mob.isConnected()){
            saida += "mob";
        }else{
            saida += "Não connectado";
            conectado = false;
        }
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }

    public boolean verificallConnect(){
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }
}
