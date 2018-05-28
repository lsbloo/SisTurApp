package com.example.osvaldoairon.app4so.BaseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.example.osvaldoairon.app4so.Modelo.AtrativosTuristicos;
import com.example.osvaldoairon.app4so.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AtrativosAdapter extends BaseAdapter implements BaseSliderView.OnSliderClickListener,BaseSliderView.ImageLoadListener{



    private static ArrayList<AtrativosTuristicos> list;
    private Context ctx;


    public AtrativosAdapter(Context c , ArrayList<AtrativosTuristicos> at ){
        this.ctx=c;
        this.list=at;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId_sql();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AtrativosTuristicos at = list.get(position);

        View view = LayoutInflater.from(ctx).inflate(R.layout.activity_fragment_inf_turismo,parent,false);


        TextView nomePonto = (TextView)view.findViewById(R.id.nomePonto);
        TextView descricaoPonto = (TextView)view.findViewById(R.id.descricaoPonto);
        TextView comochegar = (TextView)view.findViewById(R.id.comoChegar);
        TextView info = (TextView)view.findViewById(R.id.infocontato);
        TextView site = (TextView)view.findViewById(R.id.site);


        nomePonto.setText(at.getNome());
        descricaoPonto.setText(at.getDescricao());
        comochegar.setText("Como chegar?: " +at.getComoChegar());
        info.setText("Informação contato: " +at.getInfoContato());
        site.setText("Site: " +at.getSite());

        return view;
    }

    @Override
    public void onStart(BaseSliderView target) {

    }

    @Override
    public void onEnd(boolean result, BaseSliderView target) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
