package com.example.osvaldoairon.app4so.rest;

import com.example.osvaldoairon.app4so.Exceptions.ErrosDeConnexao;

import java.net.MalformedURLException;
import java.net.URL;

public class ConnectREST {
    /*
    Criar conexao entre minha api rest do spring e retornar o json com os dados
    dos municipios e pontos turisticos;
       é importante que os dados sigam a mesma ordem do que vem da api para poder selecionar
       conforme o especificado na paste de pesquisa;
     */


    public static String createConnection(String url){

        String saida="";

        try{
            URL apiurl = new URL(url);


        }

         catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
