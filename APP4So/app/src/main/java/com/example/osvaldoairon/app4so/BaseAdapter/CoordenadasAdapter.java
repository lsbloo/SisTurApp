package com.example.osvaldoairon.app4so.BaseAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.osvaldoairon.app4so.Modelo.Coordenadas;

import java.util.ArrayList;

public class CoordenadasAdapter extends BaseAdapter{

    private ArrayList<Coordenadas> list;

    public CoordenadasAdapter(ArrayList<Coordenadas> arrayList){
        this.list=arrayList;

    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
