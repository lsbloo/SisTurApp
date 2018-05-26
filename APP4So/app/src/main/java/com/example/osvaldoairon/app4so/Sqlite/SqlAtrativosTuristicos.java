package com.example.osvaldoairon.app4so.Sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
public class SqlAtrativosTuristicos extends SQLiteOpenHelper{

    private static final String NOME_BANCO = "atrativosDB";
    private static final int VERSAO_BANCO = 1;
    private static final String NOME_TABELA = "atrativosTuristicos";


    public SqlAtrativosTuristicos(Context ctx){
        super(ctx,NOME_BANCO,null,VERSAO_BANCO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
