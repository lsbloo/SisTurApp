package com.example.osvaldoairon.app4so.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.osvaldoairon.app4so.Modelo.Coordenadas;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class HelperBuscas {

    private SqlBusca busca;

    public static ArrayList<Coordenadas> list_coordenadas = new ArrayList<Coordenadas>();


    public HelperBuscas(Context ctx) {

        busca = new SqlBusca(ctx);
    }


    public long inserir(Coordenadas coordenadas) {

        SQLiteDatabase db = busca.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(busca.COLUNA_LATITUDE, coordenadas.getLatitude());
        cv.put(busca.COLUNA_LONGITUDE, coordenadas.getLongitude());
        cv.put(busca.COLUNA_NOME,coordenadas.getNomePontoTuristico());
       Log.d("COOR", ""+coordenadas.getNomePontoTuristico());


        long id = db.insert(busca.NOME_TABELA, null, cv);


        if (id != -1) {
            coordenadas.setId_sql(id);
        }
        db.close();
        Log.v("INSERINDO", "INSERIDOO");
        return id;

    }

    public void recoverDataSQL() {

        SQLiteDatabase db = busca.getWritableDatabase();
        String sql = "SELECT * FROM " + busca.NOME_TABELA;
        Cursor cursor = db.rawQuery(sql, null);

        cursor.moveToFirst();

        while (cursor.moveToNext()) {
            int indexColunaLatitude = cursor.getColumnIndex(busca.COLUNA_LATITUDE);
            int indexColunaLongitude = cursor.getColumnIndex(busca.COLUNA_LONGITUDE);
            int indexColunaIdCoordenadas = cursor.getColumnIndex(busca.COLUNA_ID_BANCO);
            int indexColunaDescricao = cursor.getColumnIndex(busca.COLUNA_NOME);

            Double latitude = cursor.getDouble(indexColunaLatitude);
            Double longitude = cursor.getDouble(indexColunaLongitude);
            String descricao = cursor.getString(indexColunaDescricao);
            long idCoordenadas = cursor.getLong(indexColunaIdCoordenadas);

            Coordenadas coordenadasdb = new Coordenadas();

            coordenadasdb.setLatitude(latitude);
            coordenadasdb.setLongitude(longitude);
            coordenadasdb.setNomePontoTuristico(descricao);
            coordenadasdb.setId_sql(idCoordenadas);



            list_coordenadas.add(coordenadasdb);

        }
        cursor.close();
        db.close();


    }

    public ArrayList<Coordenadas> getReturnList(){
        return list_coordenadas;
    }


    public int deleteUser(Coordenadas coordenadas){
        SQLiteDatabase db = busca.getWritableDatabase();

        int linha = db.delete(busca.NOME_TABELA, busca.COLUNA_ID_BANCO+"=?", new String[]{String.valueOf(coordenadas.getId_sql())});
        db.close();
        Log.v("DELETADO SQL", "DELETE SQL" + coordenadas.getId_sql());
        return linha;
    }
    public void reset(){
        SQLiteDatabase db = busca.getWritableDatabase();
        String sql_del = "DELETE FROM " + busca.NOME_TABELA;
        db.execSQL(sql_del);
        db.close();

    }

    public Coordenadas getPosition(int position){
        Log.d("LEN", ""+list_coordenadas.size());
        return list_coordenadas.get(position);
    }
    public  void limparArray(){
        list_coordenadas.clear();
    }
}
