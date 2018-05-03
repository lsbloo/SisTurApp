package com.example.osvaldoairon.app4so.rotas;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.util.List;
import java.util.Locale;

public class BuscarLtask extends AsyncTaskLoader<List<Address>> {

    Context ctx;
    String local;
    List<Address> enderecosEncontrados;


    public BuscarLtask(Context act , String local){
        super(act);
        this.ctx=act;
        this.local=local;
    }

    @Override
    protected void onStartLoading() {
        if(enderecosEncontrados==null){
            forceLoad();
        }else{
            deliverResult(enderecosEncontrados);
        }
    }

    @Override
    public List<Address> loadInBackground() {
        Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());

        try{
            enderecosEncontrados = geocoder.getFromLocationName(local,10);
        }catch (Exception e){
            e.printStackTrace();
        }
        return enderecosEncontrados;
    }
}

