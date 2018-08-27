package com.example.osvaldoairon.app4so.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.squareup.picasso.Picasso;
public class TransformadorImg extends AsyncTask<String,Void,Bitmap> {
/*
Essa classe herda do Async; responsavel por carregar uma imagem de um uRL
e transformar ela em Biptmap ; sua instancia esta sendo usada na classe HelpSQLmUNICIPIOS()
e posteriormente no HelpersqlAtrativos;
 OBS: os links dessas imagens "urls" devem ser publicas, pra que não dispare
 nenhuma excessao;
 copiar imagem do google nao vale ;(

 */

    @Override
    public Bitmap doInBackground(String... strings) {
       Bitmap imagem=null;
       InputStream inputStream;
       String url_img = strings[0];
        Log.d("ENDEREÇO IMG", "Endereço: "+url_img);

       try{
           URL url  = new URL(url_img);
           HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
           httpURLConnection.setRequestMethod("GET");
           httpURLConnection.setDoInput(true);
           httpURLConnection.connect();

           if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
               inputStream = httpURLConnection.getInputStream();

               imagem = BitmapFactory.decodeStream(inputStream);
           }
           if(imagem!=null){
               Log.d("IMAGEM_OK", "ESSA IMAGEM ESTA OK");
           }else{
               Log.d("IMAGEM_N_OK","Essa imagem nao esta ok");
           }
       }catch(IOException e){
           e.printStackTrace();
       }

       return imagem;
    }

}
