package com.example.osvaldoairon.app4so.rest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
                String nome = objArray.getString("nomecidade");
                Long id = objArray.getLong("id");
                String imgUrl = objArray.getString("imgUrl");
                String areaTerritorial = objArray.getString("areaTerritorial");
                String cep = objArray.getString("cep");
                String estado = objArray.getString("estado");
                String site = objArray.getString("site");
                int populacao = objArray.getInt("populacao");
                double latitude = checkLatAndLong(objArray.getString("latitude"));
                double longitude = checkLatAndLong(objArray.getString("longitude"));
                String informacoes_relevantes = objArray.getString("informacoesRelevantes");
                String email_responsavel = objArray.getString("email_responsavel");
                String nome_responsavel = objArray.getString("nome_responsavel");
                String contato_responsavel = objArray.getString("contatos_responsavel");
                String fonte_inf = objArray.getString("fonte_informacoes");
                String descricao = objArray.getString("descricao");


                //Atribui os objetos que estão nas camadas mais baixas
                Municipios municipios = new Municipios();
                municipios.setNome(nome);
                municipios.setId(id);

                municipios.setImgUrl(imgUrl);
                municipios.setDescricao(descricao);
                municipios.setAreaTerritorial(areaTerritorial);
                municipios.setCep(cep);
                municipios.setPopulacao(populacao);
                municipios.setLatitude(latitude);
                municipios.setLongitude(longitude);
                municipios.setEstado(estado);
                municipios.setSite(site);
                municipios.setInformacoesRelevantes(informacoes_relevantes);
                municipios.setEmail_responsavel_pelo_preenchimento(email_responsavel);
                municipios.setNome_responsavel_pelo_preenchimento(nome_responsavel);
                municipios.setContato_responsavel_pelo_preenchimento(contato_responsavel);
                municipios.setFontes_informacoes(fonte_inf);

                //Log.v("NOMES",""+nome);
                //Log.v("CEPS", ""+cep);

                lista_municipios.add(municipios);

            }
            return lista_municipios;

        }catch (JSONException e){
            e.printStackTrace();

        }
        return null;

    }

    public Double checkLatAndLong(String param){
        if(param.equals("campo nao informado")){
            return 0.0;
        }
        return Double.parseDouble(param);
    }


}
