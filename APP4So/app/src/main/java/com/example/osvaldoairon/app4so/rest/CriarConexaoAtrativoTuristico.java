package com.example.osvaldoairon.app4so.rest;

import android.util.Log;

import com.example.osvaldoairon.app4so.Exceptions.ErrosDeConnexao;
import com.example.osvaldoairon.app4so.Modelo.AtrativosTuristicos;
import com.example.osvaldoairon.app4so.Modelo.Municipios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CriarConexaoAtrativoTuristico {

    private ArrayList<AtrativosTuristicos> lista_atrativos = new ArrayList<AtrativosTuristicos>();
    private ArrayList<AtrativosTuristicos> retorno = new ArrayList<AtrativosTuristicos>();


    public ArrayList<AtrativosTuristicos> getInformacao(String end) throws ErrosDeConnexao {
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
                String nome_responsavel_preenchimento=objArray.getString("nome_responsavel_preenchimento");
                String inf_relevante = objArray.getString("informacoes_relevantes");
                String contato_r_preenchimento = objArray.getString("contato_responsavel_preenchimento");
                String fonte_inf = objArray.getString("fonte_informacoes");
                String nome_r_atrativo = objArray.getString("nome_responsavel_atrativo");
                String contato_r_atrativo = objArray.getString("contato_responsavel_atrativo");
                String email_r_atrativo = objArray.getString("email_reponsavel_atrativo");
                String email_responsavel_preenchimento = objArray.getString("email_responsavel");

                String imgUrl = objArray.getString("imgURL");



                //Atribui os objetos que estão nas camadas mais baixas

                AtrativosTuristicos atrativos = new AtrativosTuristicos();

                atrativos.setImgUrl(imgUrl);
                atrativos.setNome(nome);
                atrativos.setId(id);
                atrativos.setLatitude(latitude);
                atrativos.setLongitude(longitude);
                atrativos.setSite(site);
                atrativos.setComoChegar(comoChegar);
                atrativos.setDescricao(descricao);
                atrativos.setInfoContato(infoContato);
                atrativos.setNome_responsavel_preenchimento(nome_responsavel_preenchimento);
                atrativos.setInformacoesRelevantes(inf_relevante);
                atrativos.setContato_responsavel_preenchimento(contato_r_preenchimento);
                atrativos.setFonteInformacoes(fonte_inf);
                atrativos.setNome_responsavel_atrativo(nome_r_atrativo);
                atrativos.setContato_responsavel_atrativo(contato_r_atrativo);
                atrativos.setEmail_responsavel_atrativo(email_r_atrativo);
                atrativos.setEmail_responsavel_preenchimento(email_responsavel_preenchimento);


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
