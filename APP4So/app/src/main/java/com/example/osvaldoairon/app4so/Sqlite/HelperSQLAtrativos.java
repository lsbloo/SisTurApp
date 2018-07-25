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

            cv.put(sqlAtrativosTuristicos.INFORMACOES_RELEVANTES,atrativosTuristicos.getInformacoesRelevantes());
            cv.put(sqlAtrativosTuristicos.NOME_RESPONSAVEL_PREENCHIMENTO,atrativosTuristicos.getNome_responsavel_preenchimento());
            cv.put(sqlAtrativosTuristicos.CONTATO_RESPONSAVEL_PREENCHIMENTO,atrativosTuristicos.getContato_responsavel_preenchimento());
            cv.put(sqlAtrativosTuristicos.FONTE_INFORMACOES,atrativosTuristicos.getFonteInformacoes());
            cv.put(sqlAtrativosTuristicos.NOME_RESPONSAVEL_ATRATIVO,atrativosTuristicos.getNome_responsavel_atrativo());
            cv.put(sqlAtrativosTuristicos.CONTATO_RESPONSAVEL_ATRATIVO,atrativosTuristicos.getContato_responsavel_atrativo());
            cv.put(sqlAtrativosTuristicos.EMAIL_RESPONSAVEL_PREENCHIMENTO,atrativosTuristicos.getEmail_responsavel_preenchimento());
            cv.put(sqlAtrativosTuristicos.EMAIL_RESPONSAVEL_ATRATIVO,atrativosTuristicos.getEmail_responsavel_atrativo());


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

            int indexColunaInformacoesRelevantes = cursor.getColumnIndex(sqlAtrativosTuristicos.INFORMACOES_RELEVANTES);
            int indexColunaNomeResponsavelPreenchimento = cursor.getColumnIndex(sqlAtrativosTuristicos.NOME_RESPONSAVEL_PREENCHIMENTO);
            int indexColunaContatoResponsavelPreenchimento = cursor.getColumnIndex(sqlAtrativosTuristicos.CONTATO_RESPONSAVEL_PREENCHIMENTO);
            int indexColunaInformacoesFonte = cursor.getColumnIndex(sqlAtrativosTuristicos.FONTE_INFORMACOES);
            int indexColunaNomeResponsavelAtrativo = cursor.getColumnIndex(sqlAtrativosTuristicos.NOME_RESPONSAVEL_ATRATIVO);
            int indexColunaContatoResponsavelAtrativo = cursor.getColumnIndex(sqlAtrativosTuristicos.CONTATO_RESPONSAVEL_ATRATIVO);
            int indexColunaEmailResponsavelPreenchimento = cursor.getColumnIndex(sqlAtrativosTuristicos.EMAIL_RESPONSAVEL_PREENCHIMENTO);
            int indexColunaEmailResponsavelAtrativo = cursor.getColumnIndex(sqlAtrativosTuristicos.EMAIL_RESPONSAVEL_ATRATIVO);



            Double latitude = cursor.getDouble(indexColunaLatitude);
            Double longitude = cursor.getDouble(indexColunaLongitude);
            String nome = cursor.getString(indexColunaNome);
            String comochegar = cursor.getString(indexColunaComoChegar);
            String info = cursor.getString(indexColunaInfo);
            String site = cursor.getString(indexColunaSite);
            String descricao = cursor.getString(indexColunaDescricao);
            String informacoesRelevantes = cursor.getString(indexColunaInformacoesRelevantes);
            String nome_responsavel_preenchimento = cursor.getString(indexColunaNomeResponsavelPreenchimento);
            String contato_responsavel_preenchimento = cursor.getString(indexColunaContatoResponsavelPreenchimento);
            String fonte_informacoes = cursor.getString(indexColunaInformacoesFonte);
            String nome_responsavel_atrativo = cursor.getString(indexColunaNomeResponsavelAtrativo);
            String contato_responsavel_atrativo = cursor.getString(indexColunaContatoResponsavelAtrativo);
            String email_responsavel_preenchimento = cursor.getString(indexColunaEmailResponsavelPreenchimento);
            String email_responsavel_atrativo = cursor.getString(indexColunaEmailResponsavelAtrativo);



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
            atdb.setInformacoesRelevantes(informacoesRelevantes);
            atdb.setNome_responsavel_preenchimento(nome_responsavel_preenchimento);
            atdb.setContato_responsavel_preenchimento(contato_responsavel_preenchimento);
            atdb.setFonteInformacoes(fonte_informacoes);
            atdb.setNome_responsavel_atrativo(nome_responsavel_atrativo);
            atdb.setContato_responsavel_atrativo(contato_responsavel_atrativo);
            atdb.setEmail_responsavel_preenchimento(email_responsavel_preenchimento);
            atdb.setEmail_responsavel_atrativo(email_responsavel_atrativo);

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
