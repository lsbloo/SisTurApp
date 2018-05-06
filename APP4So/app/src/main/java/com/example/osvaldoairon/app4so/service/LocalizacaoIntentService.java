package com.example.osvaldoairon.app4so.service;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.osvaldoairon.app4so.EventBus.MessageEvent;
import com.example.osvaldoairon.app4so.MainActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocalizacaoIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * ------------ OSVALDO AIRON -------------------------------------
     *
     *  > Esse Serviço trabalha em conjunto com o EventBus que usa um Subscriber e um POster
     *      * Primeiro Objetivo pegar o dado do endereço e enviar para a MessageEvent pelo
     *      post do EventBus.
     *      Em seguida a main activity que contem o metodo onEvent do "Subscriber"
     *      "fica em espera" pelo dado passado pelo POST.
     *      usei o metodo runUIthread para trabalhar em uma segunda tarefa esse processo de
     *      passagem de Dados;
     */
    public LocalizacaoIntentService() {
        super("woker-thread");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Location locate = intent.getParcelableExtra(MainActivity.LOCATION);
        int type = intent.getIntExtra(MainActivity.TYPE,1);

        String adress = intent.getStringExtra(MainActivity.ADRESS);

        List<Address> lista_enderecos = new ArrayList<Address>();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String resultAdress=null;

            try {
                if(type == 1 && adress != null) {
                    lista_enderecos = (ArrayList<Address>) geocoder.getFromLocationName(adress, 1);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("ERROR CONEXAO LOCATION","ERROR INTENTSERVICELOCATION");
                Toast.makeText(this, "Conexão com a internet Fraca", Toast.LENGTH_SHORT).show();
            }


            if(lista_enderecos!=null && lista_enderecos.size()>0){
                Address result = lista_enderecos.get(0);

                resultAdress = result.getLatitude()+"\n" +result.getLongitude();
            }else{
                Log.d("ERROR LIST_END","LISTA DE ENDERECOS VAZIA");
                Toast.makeText(this, "Local Não Encontrado, tente novamente!", Toast.LENGTH_SHORT).show();
            }


            if(resultAdress!=null){
                MessageEvent evento = new MessageEvent();
                evento.setData(resultAdress);
                EventBus.getDefault().post(evento);
            }


    }
}

