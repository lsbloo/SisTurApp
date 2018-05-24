package com.example.osvaldoairon.app4so.rest;

import android.util.Log;

import com.example.osvaldoairon.app4so.Modelo.AtrativosTuristicos;
import com.example.osvaldoairon.app4so.Modelo.Municipios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CriarConexaoAtrativoTuristico {

    private ArrayList<AtrativosTuristicos> lista_atrativos = new ArrayList<AtrativosTuristicos>();
    private ArrayList<AtrativosTuristicos> retorno = new ArrayList<AtrativosTuristicos>();


    public ArrayList<AtrativosTuristicos> getInformacao(String end) {
        /*

         */
        String json;

        json = ConnectREST.createConnection(end);
        Log.i("Resultado", json);
        retorno = modificaJson(json);

        return retorno;
    }

    private ArrayList<AtrativosTuristicos> modificaJson(String json){


        try {
            AtrativosTuristicos atrativos = new AtrativosTuristicos();

            JSONArray jsonObj = new JSONArray(json);

            for(int i = 0 ; i<jsonObj.length();i++){
                JSONObject objArray = jsonObj.getJSONObject(i);
                String nome = objArray.getString("nome");
                Long id = objArray.getLong("id");
                String comoChegar = objArray.getString("comoChegar");
                String descricao = objArray.getString("descricao");
                String infoContato = objArray.getString("infoContato");
                String site = objArray.getString("site");
                double latitude = objArray.getDouble("latitude");
                double longitude = objArray.getDouble("longitude");

                //Atribui os objetos que estão nas camadas mais baixas
                atrativos.setNome(nome);
                atrativos.setId(id);
                atrativos.setLatitude(latitude);
                atrativos.setLongitude(longitude);
                atrativos.setSite(site);
                atrativos.setComoChegar(comoChegar);
                atrativos.setDescricao(descricao);
                atrativos.setInfoContato(infoContato);


                lista_atrativos.add(atrativos);
            }


            // Log.d("INFIFNFIFNFI", "" + lista_municipios.size());

            return lista_atrativos;
        }catch (JSONException e){
            e.printStackTrace();

        }
        return null;

    }
}
