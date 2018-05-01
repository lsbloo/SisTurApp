package com.example.osvaldoairon.app4so.Modelo;

import java.io.Serializable;
import java.util.UUID;

public class Coordenadas implements Serializable{
    private String nomeCidade;
    private String nomePontoTuristico;
    private String urlfotoCidade;
    private String urlfotoPonto;
    private double latitude;
    private double longitude;
    private double altitude;
    public String descricao;
    private String id;


    public String getUrlfotoCidade(){
        return urlfotoCidade;
    }
    public void setUrlfotoCidade(String urlfoto){
        this.urlfotoCidade=urlfoto;
    }
    public String getUrlfotoPonto(){
        return urlfotoPonto;
    }
    public void setUrlfotoPonto(String urlponto){
        this.urlfotoPonto=urlponto;
    }
    public void setNomePontoTuristico(String pontoTuristico){
        this.nomePontoTuristico=pontoTuristico;
    }
    public String getNomePontoTuristico(){
        return nomePontoTuristico;
    }
    public void setDescricao(String descricao){
        this.descricao=descricao;
    }
    public String getDescricao(){
        return descricao;
    }

    public void setNomeCidade(String nomeCidade){
        this.nomeCidade =nomeCidade;
    }
    public String getNomeCidade(){
        return nomeCidade;
    }

    public void setId(String id){
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Coordenadas(){};
}
