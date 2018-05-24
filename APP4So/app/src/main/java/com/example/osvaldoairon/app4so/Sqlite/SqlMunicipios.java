package com.example.osvaldoairon.app4so.Sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
public class SqlMunicipios extends SQLiteOpenHelper {

    public static final String NOME_BANCO="municipioDB4";
    public static final int VERSAO_BANCO = 6;
    public static final String NOME_TABELA = "municipios";
    public static final String NOME_MUNICIPIO = "municipio_nome";
    public static final String ESTADO = "estado";
    public static final String AREA_TERRITORIAL = "area_territorial";
    public static final String CEP = "cep";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String SITE = "site";
    public static final String COLUNA_ID_BANCO = "_id";
    public static final String POPULACAO = "populacao";


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + NOME_TABELA + " ( " + COLUNA_ID_BANCO + " INTEGER PRIMARY KEY AUTOINCREMENT , " + LATITUDE+ " REAL, " + LONGITUDE + " REAL, "+ NOME_MUNICIPIO + " TEXT NOT NULL, " + ESTADO + " TEXT NOT NULL, " + AREA_TERRITORIAL + " TEXT NOT NULL, "
                + CEP + " TEXT NOT NULL, " + SITE + " TEXT NOT NULL, " + POPULACAO + " INTEGER )");
    }

    public SqlMunicipios(Context context){
        super(context,NOME_BANCO,null,VERSAO_BANCO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
