package com.example.osvaldoairon.app4so.Modelo;

public class Municipios {

    private long id;

    public void setId(long id) {
        this.id=id;
    }
    public long getId() {
        return id;
    }

    private String cep;
    private String areaTerritorial;
    private String nome;
    private int populacao;
    private double latitude;
    private double longitude;
    private String estado;
    private String site;
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

