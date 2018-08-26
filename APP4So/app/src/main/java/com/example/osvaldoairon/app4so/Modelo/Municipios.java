package com.example.osvaldoairon.app4so.Modelo;

import android.graphics.Bitmap;
import android.content.Context;
import android.provider.MediaStore;

import java.io.Serializable;
import com.squareup.picasso.Picasso;

public class Municipios implements Serializable{

    private long id;
    private long id_sql;
    private String imgUrl;
    private byte[] fotos_city;
    private Bitmap fotosBit;
    private String cep;
    private String areaTerritorial;
    private String nome;
    private int populacao;
    private double latitude;
    private double longitude;
    private String estado;
    private String site;
    private String descricao;


    private String informacoesRelevantes;
    private String email_responsavel_pelo_preenchimento;
    private String nome_responsavel_pelo_preenchimento;
    private String contato_responsavel_pelo_preenchimento;
    private String fontes_informacoes;


    public void setFotosBit(Bitmap f){
        this.fotosBit=fotosBit;
    }

    public Bitmap getFotosBit() {
        return fotosBit;
    }

    public void setFotos(byte[] fotos1){
        this.fotos_city=fotos1;
    }

    public byte[] getFotos() {
        return fotos_city;
    }

    public void setImgUrl(String url){
        this.imgUrl= url;
    }
    public String getImgUrl(){
        return imgUrl;
    }

    public void setInformacoesRelevantes(String inf){
        this.informacoesRelevantes=inf;
    }
    public String getInformacoesRelevantes(){
        return informacoesRelevantes;
    }
    public void setDescricao(String descricao){
        this.descricao=descricao;
    }
    public String getDescricao(){
        return descricao;
    }
    public void setEmail_responsavel_pelo_preenchimento(String email_responsavel){
        this.email_responsavel_pelo_preenchimento=email_responsavel;
    }
    public String getEmail_responsavel_pelo_preenchimento(){
        return email_responsavel_pelo_preenchimento;
    }

    public void setNome_responsavel_pelo_preenchimento(String nome_responsavel){
        this.nome_responsavel_pelo_preenchimento=nome_responsavel;
    }
    public String getNome_responsavel_pelo_preenchimento(){
        return nome_responsavel_pelo_preenchimento;
    }
    public void setContato_responsavel_pelo_preenchimento(String contato_responsavel){
        this.contato_responsavel_pelo_preenchimento=contato_responsavel;
    }
    public String getContato_responsavel_pelo_preenchimento(){
        return contato_responsavel_pelo_preenchimento;
    }
    public void setFontes_informacoes(String inf){
        this.fontes_informacoes=inf;
    }
    public String getFontes_informacoes(){
        return fontes_informacoes;
    }

    public void setId_sql(long id ){
        this.id_sql=id;
    }
    public long getId_sql(){
        return id_sql;
    }

    public void setId(long id) {
        this.id=id;
    }
    public long getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getPopulacao() {
        return populacao;
    }
    public void setPopulacao(int populacao) {
        this.populacao = populacao;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getSite() {
        return site;
    }
    public void setSite(String site) {
        this.site = site;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Municipios() {}


    public Municipios(String areaTerritorial,String cep,String estado, Double latitude , Double longitude , String nome , int populacao , String site){
        setAreaTerritorial(areaTerritorial); // 0
        setCep(cep); // 1
        setEstado(estado); // 2
        setLatitude(latitude); // 3
        setLongitude(longitude); // 4
        setNome(nome); // 5
        setPopulacao(populacao); // 6
        setSite(site); // 7
    }
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    public String getAreaTerritorial() {
        return areaTerritorial;
    }
    public void setAreaTerritorial(String areaTerritorial) {
        this.areaTerritorial = areaTerritorial;
    }

}

