package com.example.osvaldoairon.app4so.ActivitysSecond;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.osvaldoairon.app4so.MainActivity;
import com.example.osvaldoairon.app4so.R;

public class SplashAct extends AppCompatActivity {

    private static int TEMPO=4000;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        progressBar=(ProgressBar)findViewById(R.id.progressSplash);
        exibir_Conexao(verificaConexao());

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
