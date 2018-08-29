package com.example.osvaldoairon.app4so.Modelo;

import java.io.Serializable;

import android.graphics.Bitmap;
public class AtrativosTuristicos implements Serializable{

    private long id;
    private long id_sql;


    public void setId(long id) {
        this.id=id;
    }
    public Long getId() {
        return id;
    }

    private String imgUrl;
    private byte[] fotos_byte;
    private Bitmap fotosBit;


    private String nome; // 0
    private double latitude; // 1
    private double longitude; // 2
    private String comoChegar; // 3
    private String site; // 4
    private String infoContato; // 5
    private String descricao; // 6

    private String informacoesRelevantes;
    private String nome_responsavel_preenchimento;
    private String contato_responsavel_preenchimento;
    private String fonteInformacoes;
    private String nome_responsavel_atrativo;
    private String contato_responsavel_atrativo;
    private String email_responsavel_preenchimento;
    private String email_responsavel_atrativo;




    public void setImgUrl(String imgUrl){
        this.imgUrl=imgUrl;
    }
    public String getImgUrl(){
        return imgUrl;
    }
    public void setFotos_byte(Bitmap ft_bitmap){
        this.fotosBit=ft_bitmap;
    }

    public Bitmap getFotosBit() {
        return fotosBit;
    }

    public void setFotos_byte(byte[] fotos_byte) {
        this.fotos_byte = fotos_byte;
    }

    public byte[] getFotos_byte() {
        return fotos_byte;
    }


    public void setInformacoesRelevantes(String informacoesRelevantes1){
        this.informacoesRelevantes=informacoesRelevantes1;
    }
    public String getInformacoesRelevantes(){
        return informacoesRelevantes;
    }
    public void setNome_responsavel_preenchimento(String nome_responsavel){
        this.nome_responsavel_preenchimento=nome_responsavel;
    }

    public String getNome_responsavel_preenchimento(){
        return nome_responsavel_preenchimento;
    }
    public void setContato_responsavel_preenchimento(String contato_responsavel_preenchimento1){
        this.contato_responsavel_preenchimento=contato_responsavel_preenchimento1;
    }
    public String getContato_responsavel_preenchimento(){
        return contato_responsavel_preenchimento;
    }
    public void setFonteInformacoes(String fonteInformacoes1){
        this.fonteInformacoes=fonteInformacoes1;
    }
    public String getFonteInformacoes(){
        return fonteInformacoes;
    }
    public void setNome_responsavel_atrativo(String nome_responsavel_atrativo){
        this.nome_responsavel_atrativo=nome_responsavel_atrativo;
    }
    public String getNome_responsavel_atrativo(){
        return nome_responsavel_atrativo;
    }
    public void setContato_responsavel_atrativo(String contato_responsavel_atrativo1){
        this.contato_responsavel_atrativo=contato_responsavel_atrativo1;
    }
    public String getContato_responsavel_atrativo(){
        return contato_responsavel_atrativo;
    }
    public void setEmail_responsavel_preenchimento(String email_responsavel_preenchimento){
        this.email_responsavel_preenchimento=email_responsavel_preenchimento;
    }
    public String getEmail_responsavel_preenchimento(){
        return email_responsavel_preenchimento;
    }
    public void setEmail_responsavel_atrativo(String email_responsavel_atrativo1){
        this.email_responsavel_atrativo=email_responsavel_atrativo1;
    }
    public String getEmail_responsavel_atrativo(){
        return email_responsavel_atrativo;
    }


    public void setId_sql(Long id){
        this.id_sql = id;
    }

    public long getId_sql() {
        return id_sql;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public String getComoChegar() {
        return comoChegar;
    }
    public void setComoChegar(String comoChegar) {
        this.comoChegar = comoChegar;
    }
    public String getSite() {
        return site;
    }
    public void setSite(String site) {
        this.site = site;
    }
    public String getInfoContato() {
        return infoContato;
    }
    public void setInfoContato(String infoContato) {
        this.infoContato = infoContato;
    }





    public AtrativosTuristicos() {}

    public AtrativosTuristicos(String nome ,String comoChegar, String descricao , String infoContato , double latitude, double longitude, String site){

        setNome(nome);
        setComoChegar(comoChegar);
        setDescricao(descricao);
        setInfoContato(infoContato);
        setLatitude(latitude);
        setLongitude(longitude);
        setSite(site);
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
