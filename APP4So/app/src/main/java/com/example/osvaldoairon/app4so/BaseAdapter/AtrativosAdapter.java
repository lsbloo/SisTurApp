package com.example.osvaldoairon.app4so.BaseAdapter;

import android.content.Context;
import android.util.Log;
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

       // View view = LayoutInflater.from(ctx).inflate(R.layout.activity_fragment_inf_turismo,parent,false);

    /*
    TextView nomePonto = (TextView)view.findViewById(R.id.nomePonto);
        TextView descricaoPonto = (TextView)view.findViewById(R.id.descricaoPonto);
        TextView comochegar = (TextView)view.findViewById(R.id.comoChegar);
        TextView info = (TextView)view.findViewById(R.id.infocontato);
        TextView site = (TextView)view.findViewById(R.id.site);
        TextView informacoesRelevante = (TextView)view.findViewById(R.id.inf_relevante);
        TextView nome_responsavel_at = (TextView)view.findViewById(R.id.nome_responsavel_atrativo);
        TextView email_responsavel_atrativo = (TextView)view.findViewById(R.id.email_responsavel_atrativo);
        TextView contato_responsavel_atrativo = (TextView)view.findViewById(R.id.contato_responsavel_atrativo);
        TextView fonte_inf = (TextView)view.findViewById(R.id.fonte_inf);

        nomePonto.setText(at.getNome());
        descricaoPonto.setText(at.getDescricao());
        comochegar.setText("Como chegar?: " +at.getComoChegar());
        info.setText("Informação contato: " +at.getInfoContato());
        site.setText("Site: " +at.getSite());
        informacoesRelevante.setText("Informações Relevantes: "+at.getInformacoesRelevantes());
        nome_responsavel_at.setText("Responsavel Atrativo: "+at.getNome_responsavel_atrativo());
        email_responsavel_atrativo.setText("Email Responsavel do Atrativo: "+at.getEmail_responsavel_atrativo());
        contato_responsavel_atrativo.setText("Contato Responsavel Atrativo: "+at.getContato_responsavel_atrativo());
        fonte_inf.setText("Fonte de Informações: "+at.getFonteInformacoes());


        return view;
     */
    return null;

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
