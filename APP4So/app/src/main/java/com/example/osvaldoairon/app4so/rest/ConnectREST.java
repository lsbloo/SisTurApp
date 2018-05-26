package com.example.osvaldoairon.app4so.rest;

import com.example.osvaldoairon.app4so.Exceptions.ErrosDeConnexao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConnectREST {
    /*
    Criar conexao entre minha api rest do spring e retornar o json com os dados
    dos municipios e pontos turisticos;
       é importante que os dados sigam a mesma ordem do que vem da api para poder selecionar
       conforme o especificado na paste de pesquisa;
     */


    public static String createConnection(String url) throws ErrosDeConnexao {

        String saida="";

        try{
            //Pega o link da url que contem os dados do rest
            URL apiurl = new URL(url);
            int codigo;
            HttpURLConnection conexao; // cria uma conexao entre meu aplicativo e rest
            InputStream is;

            conexao = (HttpURLConnection) apiurl.openConnection(); // faz a chamada da conexao
            conexao.setRequestMethod("GET"); // define o parametro dela como "GET"
            conexao.setReadTimeout(30000);
            conexao.setConnectTimeout(30000);
            conexao.connect(); // conexao

            codigo = conexao.getResponseCode();
            /*
            Faz a verificacao pra ver se a conexao foi ou nao bem sucedida
            necessario conexao com a internet para usar o Rest:X
             */
            if(codigo < HttpURLConnection.HTTP_BAD_REQUEST){
                is = conexao.getInputStream();
            }else{
                is = conexao.getErrorStream();
            }

            saida = converterInputStreamToString(is);
            is.close();
            conexao.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }


        return saida;
    }

    private static String converterInputStreamToString(InputStream is){

        StringBuffer buffer = new StringBuffer();
        try{
            BufferedReader br;
            String linha;

            br = new BufferedReader(new InputStreamReader(is));
            while((linha = br.readLine())!=null){
                buffer.append(linha);
            }
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return buffer.toString();

    }
}
