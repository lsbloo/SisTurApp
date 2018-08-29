package com.example.osvaldoairon.app4so.Sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
public class SqlAtrativosTuristicos extends SQLiteOpenHelper{

    public static final String NOME_BANCO = "SisTurATuristicos";
    public static final int VERSAO_BANCO = 15;
    public static final String ID_ = "_id";
    public static final String NOME_TABELA = "atrativosTuristicos1";
    public static final String IMAGEM_AT = "imagens_atrativo";
    public static final String NOME_ATRATIVOS = "nomeAtratativo";
    public static final String COMO_CHEGAR = "comoChegar";
    public static final String DESCRICAO = "descricao";
    public static final String INFO_CONTATO = "info_contato";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String SITE = "site";

    public static final String INFORMACOES_RELEVANTES="informacoes_relevantes";
    public static final String NOME_RESPONSAVEL_PREENCHIMENTO="nome_responsavel_preenchimento";
    public static final String CONTATO_RESPONSAVEL_PREENCHIMENTO="contato_responsavel_preenchimento";
    public static final String FONTE_INFORMACOES="fonte_informacoes";
    public static final String NOME_RESPONSAVEL_ATRATIVO="nome_responsavel_atrativo";
    public static final String CONTATO_RESPONSAVEL_ATRATIVO="contato_responsavel_atrativo";
    public static final String EMAIL_RESPONSAVEL_PREENCHIMENTO="email_responsavel";
    public static final String EMAIL_RESPONSAVEL_ATRATIVO="email_responsavel_atrativo";



    public SqlAtrativosTuristicos(Context ctx) {

        super(ctx,NOME_BANCO,null,VERSAO_BANCO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + NOME_TABELA + " ( " + ID_ + " INTEGER PRIMARY KEY AUTOINCREMENT , " + LATITUDE+ " REAL, " + LONGITUDE + " REAL, "+ NOME_ATRATIVOS + " TEXT NOT NULL, " + COMO_CHEGAR + " TEXT NOT NULL, " + DESCRICAO + " TEXT NOT NULL, "
                + INFO_CONTATO+"  TEXT NOT NULL, " + SITE + " TEXT NOT NULL , "+ INFORMACOES_RELEVANTES + " TEXT NOT NULL, "+ NOME_RESPONSAVEL_PREENCHIMENTO + " TEXT NOT NULL, "+ CONTATO_RESPONSAVEL_PREENCHIMENTO +" TEXT NOT NULL, "+ FONTE_INFORMACOES +" TEXT NOT NULL, "+ NOME_RESPONSAVEL_ATRATIVO +" TEXT NOT NULL , "+ CONTATO_RESPONSAVEL_ATRATIVO +" TEXT NOT NULL , "+ EMAIL_RESPONSAVEL_PREENCHIMENTO +" TEXT NOT NULL , "+ EMAIL_RESPONSAVEL_ATRATIVO +" TEXT NOT NULL ,"+IMAGEM_AT+" BLOB )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
