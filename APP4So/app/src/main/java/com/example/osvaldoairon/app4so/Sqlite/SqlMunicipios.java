package com.example.osvaldoairon.app4so.Sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class SqlMunicipios extends SQLiteOpenHelper {

    public static final String NOME_BANCO="municipiosApp4Society";
    public static final int VERSAO_BANCO = 10;
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

    public static final String INFORMACOES_RELEVANTES = "informacoesRelevantes";
    public static final String NOME_RESPONSAVEL = "nome_responsavel";
    public static final String CONTATO_RESPONSAVEL = "contatos_responsavel";
    public static final String FONTE_INFORMACOES = "fontes_informacoes";
    public static final String EMAIL_RESPONSAVEL = "email_responsavel";


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + NOME_TABELA + " ( " + COLUNA_ID_BANCO + " INTEGER PRIMARY KEY AUTOINCREMENT , " + LATITUDE+ " REAL, " + LONGITUDE + " REAL, "+ NOME_MUNICIPIO + " TEXT NOT NULL, " + ESTADO + " TEXT NOT NULL, " + AREA_TERRITORIAL + " TEXT NOT NULL, "
                + CEP + " TEXT NOT NULL, " + SITE + " TEXT NOT NULL, " + POPULACAO + " INTEGER , " + INFORMACOES_RELEVANTES+" TEXT , "+NOME_RESPONSAVEL+" TEXT ,"+CONTATO_RESPONSAVEL+" TEXT , "+EMAIL_RESPONSAVEL+" TEXT ,"+FONTE_INFORMACOES+" TEXT )");
    }

    public SqlMunicipios(Context context){
        super(context,NOME_BANCO,null,VERSAO_BANCO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
