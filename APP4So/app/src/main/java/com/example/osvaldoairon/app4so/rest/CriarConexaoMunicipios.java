package com.example.osvaldoairon.app4so.rest;

import android.util.Log;
import android.widget.Toast;

import com.example.osvaldoairon.app4so.Exceptions.ErrosDeConnexao;
import com.example.osvaldoairon.app4so.Modelo.Municipios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CriarConexaoMunicipios {

    private ArrayList<Municipios> lista_municipios = new ArrayList<Municipios>();
    private ArrayList<Municipios> retorno = new ArrayList<Municipios>();

    public ArrayList<Municipios> getInformacao(String end) throws ErrosDeConnexao {
        /*

         */
        String json;

        json = ConnectREST.createConnection(end);
        Log.i("Resultado", json);
        retorno = modificaJson(json);

        return retorno;
    }

    private ArrayList<Municipios> modificaJson(String json){

        try {


            JSONArray jsonObj = new JSONArray(json);

            for(int i = 0 ; i<jsonObj.length();i++){
                JSONObject objArray = jsonObj.getJSONObject(i);
                String nome = objArray.getString("nome");
                Long id = objArray.getLong("id");
                String areaTerritorial = objArray.getString("areaTerritorial");
                String cep = objArray.getString("cep");
                String estado = objArray.getString("estado");
                String site = objArray.getString("site");
                int populacao = objArray.getInt("populacao");
                double latitude = objArray.getDouble("latitude");
                double longitude = objArray.getDouble("longitude");

                //Atribui os objetos que estão nas camadas mais baixas
                Municipios municipios = new Municipios();
                municipios.setNome(nome);
                municipios.setId(id);
                municipios.setAreaTerritorial(areaTerritorial);
                municipios.setCep(cep);
                municipios.setPopulacao(populacao);
                municipios.setLatitude(latitude);
                municipios.setLongitude(longitude);
                municipios.setEstado(estado);
                municipios.setSite(site);

                Log.v("NOMES",""+nome);
                Log.v("CEPS", ""+cep);

                lista_municipios.add(municipios);

            }
            return lista_municipios;

        }catch (JSONException e){
            e.printStackTrace();

        }
        return null;

    }


}
