package com.example.osvaldoairon.app4so.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.osvaldoairon.app4so.Modelo.AtrativosTuristicos;
import com.example.osvaldoairon.app4so.Modelo.Municipios;

import java.util.ArrayList;

public class HelperSQLAtrativos {

    private SqlAtrativosTuristicos sqlAtrativosTuristicos;

    private static ArrayList<AtrativosTuristicos> list_atrativos = new ArrayList<AtrativosTuristicos>();


    public HelperSQLAtrativos(Context context){
        sqlAtrativosTuristicos = new SqlAtrativosTuristicos(context);
    }


    public boolean verificaDado(AtrativosTuristicos at) {

        if(list_atrativos!=null){
            for(int i = 0 ; i<list_atrativos.size();i++){
                if(at.getNome().equals(list_atrativos.get(i).getNome()) && at.getDescricao().equals(list_atrativos.get(i).getDescricao()) && at.getInfoContato().equals(list_atrativos.get(i).getInfoContato())){
                    return true;
                }
            }
        }
        return false;
    }
    public void inserirAtrativo(AtrativosTuristicos atrativosTuristicos){

        if(verificaDado(atrativosTuristicos)){
            Log.v("JA INSERIDOS-ATRATIVOS", "Dados REST JA INSERIDO MUNICIPIO");
        }else{
            SQLiteDatabase db = sqlAtrativosTuristicos.getWritableDatabase();

            ContentValues cv = new ContentValues();

            cv.put(sqlAtrativosTuristicos.NOME_ATRATIVOS, atrativosTuristicos.getNome());
            cv.put(sqlAtrativosTuristicos.INFO_CONTATO, atrativosTuristicos.getInfoContato());
            cv.put(sqlAtrativosTuristicos.DESCRICAO, atrativosTuristicos.getDescricao());
            cv.put(sqlAtrativosTuristicos.LATITUDE, atrativosTuristicos.getLatitude());
            cv.put(sqlAtrativosTuristicos.LONGITUDE, atrativosTuristicos.getLongitude());
            cv.put(sqlAtrativosTuristicos.SITE, atrativosTuristicos.getSite());
            cv.put(sqlAtrativosTuristicos.COMO_CHEGAR,atrativosTuristicos.getComoChegar());

            long id = db.insert(sqlAtrativosTuristicos.NOME_TABELA,null,cv);

            if(id != -1){
                atrativosTuristicos.setId_sql(id);
            }
            db.close();
            Log.v("INSERINDO--Atrativos", "INSERINDO--ATRATIVOS");
        }



    }

    public void recoverDataSQLAtrativos() {

        SQLiteDatabase db = sqlAtrativosTuristicos.getWritableDatabase();
        String sql = "SELECT * FROM " + sqlAtrativosTuristicos.NOME_TABELA;
        Cursor cursor = db.rawQuery(sql, null);

        cursor.moveToFirst();

        while (cursor.moveToNext()) {
            int indexColunaLatitude = cursor.getColumnIndex(sqlAtrativosTuristicos.LATITUDE);
            int indexColunaLongitude = cursor.getColumnIndex(sqlAtrativosTuristicos.LONGITUDE);
            int indexID = cursor.getColumnIndex(sqlAtrativosTuristicos.ID_);
            int indexColunaNome = cursor.getColumnIndex(sqlAtrativosTuristicos.NOME_ATRATIVOS);
            int indexColunaSite = cursor.getColumnIndex(sqlAtrativosTuristicos.SITE);
            int indexColunaComoChegar = cursor.getColumnIndex(sqlAtrativosTuristicos.COMO_CHEGAR);
            int indexColunaInfo = cursor.getColumnIndex(sqlAtrativosTuristicos.INFO_CONTATO);
            int indexColunaDescricao = cursor.getColumnIndex(sqlAtrativosTuristicos.DESCRICAO);


            Double latitude = cursor.getDouble(indexColunaLatitude);
            Double longitude = cursor.getDouble(indexColunaLongitude);
            String nome = cursor.getString(indexColunaNome);
            String comochegar = cursor.getString(indexColunaComoChegar);
            String info = cursor.getString(indexColunaInfo);
            String site = cursor.getString(indexColunaSite);
            String descricao = cursor.getString(indexColunaDescricao);

            long municipiosid = cursor.getLong(indexID);

            AtrativosTuristicos atdb = new AtrativosTuristicos();

            atdb.setLatitude(latitude);
            atdb.setLongitude(longitude);
            atdb.setNome(nome);
            atdb.setSite(site);
            atdb.setId_sql(municipiosid);
            atdb.setComoChegar(comochegar);
            atdb.setInfoContato(info);
            atdb.setDescricao(descricao);

            list_atrativos.add(atdb);

        }
        cursor.close();
        db.close();


    }

    public ArrayList<AtrativosTuristicos> getReturnList(){
        return list_atrativos;
    }


    public int deleteAtrativo(Municipios city){
        SQLiteDatabase db = sqlAtrativosTuristicos.getWritableDatabase();

        int linha = db.delete(sqlAtrativosTuristicos.NOME_TABELA, sqlAtrativosTuristicos.ID_+"=?", new String[]{String.valueOf(city.getId_sql())});
        db.close();
        Log.v("DELETADO SQL", "DELETE SQL" + city.getId_sql());
        return linha;
    }

    public void reset(){
        SQLiteDatabase db = sqlAtrativosTuristicos.getWritableDatabase();
        String sql_del = "DELETE FROM " + sqlAtrativosTuristicos.NOME_TABELA;
        db.execSQL(sql_del);
        db.close();

    }
    public AtrativosTuristicos getPosition(int position){
        Log.d("LEN - ATRATIVOS", ""+list_atrativos.size());
        return list_atrativos.get(position);
    }

    public  void limparArray(){

        list_atrativos.clear();
    }


}
