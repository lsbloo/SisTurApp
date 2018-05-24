package com.example.osvaldoairon.app4so.Modelo;

public class AtrativosTuristicos {

    private long id;


    public void setId(long id) {
        this.id=id;
    }
    public Long getId() {
        return id;
    }

    private String nome; // 0
    private double latitude; // 1
    private double longitude; // 2
    private String comoChegar; // 3
    private String site; // 4
    private String infoContato; // 5
    private String descricao; // 6

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
