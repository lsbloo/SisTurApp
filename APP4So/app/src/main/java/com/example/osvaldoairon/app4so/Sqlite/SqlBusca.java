package com.example.osvaldoairon.app4so.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlBusca extends SQLiteOpenHelper {

    public static final String NOME_BANCO ="Apps4Society1";
    public static final String NOME_TABELA = "buscas";
    public static final int VERSAO_BANCO = 2;
    public static final String COLUNA_LATITUDE = "latitudes";
    public static final String COLUNA_LONGITUDE = "longitudes";
    public static final String COLUNA_ID_BANCO = "_id";
    public static final String COLUNA_NOME = "descricoes";




    public SqlBusca(Context context) {
        super(context,NOME_BANCO,null,VERSAO_BANCO);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + NOME_TABELA + " ( " + COLUNA_ID_BANCO + " INTEGER PRIMARY KEY AUTOINCREMENT , " + COLUNA_LATITUDE+ " REAL, " + COLUNA_LONGITUDE + " REAL, "+ COLUNA_NOME + " TEXT )");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
