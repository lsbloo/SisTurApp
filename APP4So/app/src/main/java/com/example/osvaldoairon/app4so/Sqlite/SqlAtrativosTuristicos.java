package com.example.osvaldoairon.app4so.Sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
public class SqlAtrativosTuristicos extends SQLiteOpenHelper{

    public static final String NOME_BANCO = "atrativosDB";
    public static final int VERSAO_BANCO = 1;
    public static final String ID_ = "_id";
    public static final String NOME_TABELA = "atrativosTuristicos";
    public static final String NOME_ATRATIVOS = "nomeAtratativo";
    public static final String COMO_CHEGAR = "comoChegar";
    public static final String DESCRICAO = "descricao";
    public static final String INFO_CONTATO = "info_contato";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String SITE = "site";


    public SqlAtrativosTuristicos(Context ctx) {
        super(ctx,NOME_BANCO,null,VERSAO_BANCO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + NOME_TABELA + " ( " + ID_ + " INTEGER PRIMARY KEY AUTOINCREMENT , " + LATITUDE+ " REAL, " + LONGITUDE + " REAL, "+ NOME_ATRATIVOS + " TEXT NOT NULL, " + COMO_CHEGAR + " TEXT NOT NULL, " + DESCRICAO + " TEXT NOT NULL, "
                + INFO_CONTATO + " TEXT NOT NULL, " + SITE + " TEXT NOT NULL )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
