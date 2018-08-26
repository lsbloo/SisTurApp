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
e transformar ela em Biptmap ; sua instancia esta sendo usada na classe HelpSQLmUNICIPIOS();

 */

    @Override
    public Bitmap doInBackground(String... strings) {
       Bitmap imagem=null;
       InputStream inputStream;
       String url_img = strings[0];
        Log.d("ENDEREÇO IMG", "Endereço: "+url_img);

       try{
           URL url  = new URL("http://pedra.pe.gov.br/zaap/kcfinder/upload/images/img_site/2018/13076834_943856129060253_8914598678967551039_n.jpg");
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
