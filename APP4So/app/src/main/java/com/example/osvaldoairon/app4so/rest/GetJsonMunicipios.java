package com.example.osvaldoairon.app4so.rest;

import android.os.AsyncTask;

import com.example.osvaldoairon.app4so.Modelo.Municipios;

import java.util.ArrayList;

public class GetJsonMunicipios extends AsyncTask<Void, Void, ArrayList<Municipios>> {
    @Override
    protected ArrayList<Municipios> doInBackground(Void... voids) {
        CriarConexaoMunicipios  util = new CriarConexaoMunicipios();



        return util.getInformacao("http://192.168.31.143:8080/municipios");
    }
}
