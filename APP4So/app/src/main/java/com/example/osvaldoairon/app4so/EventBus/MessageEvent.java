package com.example.osvaldoairon.app4so.EventBus;

public class MessageEvent {

    private String data;

    public MessageEvent(){
    }

    public void setData(String data){
        this.data=data;
    }

    public String getData(){
        return data;
    }
}
