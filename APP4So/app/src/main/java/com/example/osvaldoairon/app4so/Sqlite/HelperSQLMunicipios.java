package com.example.osvaldoairon.app4so.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.osvaldoairon.app4so.Util.TransformadorImg;
import com.example.osvaldoairon.app4so.Modelo.Coordenadas;
import com.example.osvaldoairon.app4so.Modelo.Municipios;

import java.io.ByteArrayOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class HelperSQLMunicipios {

    private SqlMunicipios municipiossql;
    private static TransformadorImg transformadorImg;

    private ArrayList<Municipios> list_municipios = new ArrayList<Municipios>();


    public HelperSQLMunicipios(Context ctx){
        municipiossql = new SqlMunicipios(ctx);


    }

    public byte[] transformBityMap(String url){
        /*
        Reponsavel por pegar uma imagem Bitmap gerada na classe Transformador
        e transformar ela em um byte[] array;
        é necessario para o armazenamento no sqlite;
        tipo de dado da imagem {blob};
        é interessante recuperar essas imagens do bd uma vez que se o dado
        do municipio ja tiver sido inserido, nao fazer uma nova requisição de imagem;

         */
        transformadorImg = new TransformadorImg();

        Bitmap img = transformadorImg.doInBackground(url);


        ByteArrayOutputStream s = new ByteArrayOutputStream();

        img.compress(Bitmap.CompressFormat.JPEG,100,s);

        byte[] imagemByte = s.toByteArray();

        return imagemByte;

    }

    public boolean verificaDado(Municipios city) {

        if(list_municipios!=null){
            for(int i = 0 ; i<list_municipios.size();i++){
                if(city.getNome().equals(list_municipios.get(i).getNome()) && city.getEstado().equals(list_municipios.get(i).getEstado())){
                    return true;
                }
            }
        }
        return false;
    }

    public long inserir(Municipios municipios) {
        if(verificaDado(municipios)){
            Log.v("DADOS JA INSERIDOS", "Dados REST JA INSERIDO MUNICIPIO");
        }else{
            SQLiteDatabase db = municipiossql.getWritableDatabase();
            ContentValues cv = new ContentValues();

            byte[] saida_ = transformBityMap(municipios.getImgUrl());

            cv.put(municipiossql.LATITUDE, municipios.getLatitude());
            cv.put(municipiossql.LONGITUDE, municipios.getLongitude());
            cv.put(municipiossql.DESCRICAO,municipios.getDescricao());
            cv.put(municipiossql.NOME_MUNICIPIO,municipios.getNome());
            cv.put(municipiossql.ESTADO, municipios.getEstado());
            cv.put(municipiossql.CEP, municipios.getCep());
            cv.put(municipiossql.AREA_TERRITORIAL, municipios.getAreaTerritorial());
            cv.put(municipiossql.SITE, municipios.getSite());
            cv.put(municipiossql.POPULACAO, municipios.getPopulacao());

            cv.put(municipiossql.IMAGEM_CTY,saida_);

            cv.put(municipiossql.FONTE_INFORMACOES,municipios.getFontes_informacoes());
            cv.put(municipiossql.EMAIL_RESPONSAVEL,municipios.getEmail_responsavel_pelo_preenchimento());
            cv.put(municipiossql.NOME_RESPONSAVEL,municipios.getNome_responsavel_pelo_preenchimento());
            cv.put(municipiossql.INFORMACOES_RELEVANTES,municipios.getInformacoesRelevantes());
            cv.put(municipiossql.CONTATO_RESPONSAVEL,municipios.getContato_responsavel_pelo_preenchimento());



            long id = db.insert(municipiossql.NOME_TABELA, null, cv);


            if (id != -1) {
                municipios.setId_sql(id);
            }
            db.close();
            Log.v("INSERINDO--MUNICIPIOS", "INSERINDO--MUNICIPIOS");
            return id;
        }
       return 0;


    }

    public void recoverDataSQL() {

        SQLiteDatabase db = municipiossql.getWritableDatabase();
        String sql = "SELECT * FROM " + municipiossql.NOME_TABELA;
        Cursor cursor = db.rawQuery(sql, null);

        cursor.moveToFirst();

        while (cursor.moveToNext()) {
            int indexColunaLatitude = cursor.getColumnIndex(municipiossql.LATITUDE);
            int indexColunaLongitude = cursor.getColumnIndex(municipiossql.LONGITUDE);
            int indexID = cursor.getColumnIndex(municipiossql.COLUNA_ID_BANCO);
            int indexColunaNome = cursor.getColumnIndex(municipiossql.NOME_MUNICIPIO);
            int indexColunaEstado = cursor.getColumnIndex(municipiossql.ESTADO);
            int indexColunaCEP = cursor.getColumnIndex(municipiossql.CEP);
            int inddexColunaPopulacao = cursor.getColumnIndex(municipiossql.POPULACAO);
            int indexColunaSite = cursor.getColumnIndex(municipiossql.SITE);
            int indexColunaAreaTerritorial = cursor.getColumnIndex(municipiossql.AREA_TERRITORIAL);
            int indexColunaFonteInformacoes = cursor.getColumnIndex(municipiossql.FONTE_INFORMACOES);
            int indexEmailResponsavel = cursor.getColumnIndex(municipiossql.EMAIL_RESPONSAVEL);
            int indexNomeResponsavel = cursor.getColumnIndex(municipiossql.NOME_RESPONSAVEL);
            int indexInformacoesRelevantes = cursor.getColumnIndex(municipiossql.INFORMACOES_RELEVANTES);
            int indexContatoResponsavel = cursor.getColumnIndex(municipiossql.CONTATO_RESPONSAVEL);
            int indexFotos_city = cursor.getColumnIndex(municipiossql.IMAGEM_CTY);
            int indexDescricao = cursor.getColumnIndex(municipiossql.DESCRICAO);

            Double latitude = cursor.getDouble(indexColunaLatitude);
            Double longitude = cursor.getDouble(indexColunaLongitude);
            String nome = cursor.getString(indexColunaNome);
            String estado = cursor.getString(indexColunaEstado);
            String cep = cursor.getString(indexColunaCEP);
            int populacao = cursor.getInt(inddexColunaPopulacao);
            String site = cursor.getString(indexColunaSite);
            String area = cursor.getString(indexColunaAreaTerritorial);
            String fonteInf = cursor.getString(indexColunaFonteInformacoes);
            String emailResponsavel = cursor.getString(indexEmailResponsavel);
            String nomeResponsavel = cursor.getString(indexNomeResponsavel);
            String informacoesRelevantes =cursor.getString(indexInformacoesRelevantes);
            String contato_responsavel = cursor.getString(indexContatoResponsavel);
            byte[] fotos = cursor.getBlob(indexFotos_city);
            String descricao = cursor.getString(indexDescricao);


            long municipiosid = cursor.getLong(indexID);

            Municipios municipiosdb = new Municipios();

            municipiosdb.setFotos(fotos);
            municipiosdb.setDescricao(descricao);

            municipiosdb.setLatitude(latitude);
            municipiosdb.setLongitude(longitude);
            municipiosdb.setNome(nome);
            municipiosdb.setEstado(estado);
            municipiosdb.setCep(cep);
            municipiosdb.setPopulacao(populacao);
            municipiosdb.setSite(site);
            municipiosdb.setAreaTerritorial(area);
            municipiosdb.setId_sql(municipiosid);
            municipiosdb.setFontes_informacoes(fonteInf);
            municipiosdb.setEmail_responsavel_pelo_preenchimento(emailResponsavel);
            municipiosdb.setNome_responsavel_pelo_preenchimento(nomeResponsavel);
            municipiosdb.setInformacoesRelevantes(informacoesRelevantes);
            municipiosdb.setContato_responsavel_pelo_preenchimento(contato_responsavel);




            list_municipios.add(municipiosdb);

        }
        cursor.close();
        db.close();


    }


    public ArrayList<Municipios> getReturnList(){
        return list_municipios;
    }


    public int deletecity(Municipios city){
        SQLiteDatabase db = municipiossql.getWritableDatabase();

        int linha = db.delete(municipiossql.NOME_TABELA, municipiossql.COLUNA_ID_BANCO+"=?", new String[]{String.valueOf(city.getId_sql())});
        db.close();
        Log.v("DELETADO SQL", "DELETE SQL" + city.getId_sql());
        return linha;
    }
    public void reset(){
        SQLiteDatabase db = municipiossql.getWritableDatabase();
        String sql_del = "DELETE FROM " + municipiossql.NOME_TABELA;
        db.execSQL(sql_del);
        db.close();

    }

    public Municipios getPosition(int position){
        Log.d("LEN", ""+list_municipios.size());
        return list_municipios.get(position);
    }
    public  void limparArray(){
        list_municipios.clear();
    }




}
