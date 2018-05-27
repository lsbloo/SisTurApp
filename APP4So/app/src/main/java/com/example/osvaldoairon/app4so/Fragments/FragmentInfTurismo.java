package com.example.osvaldoairon.app4so.Fragments;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.osvaldoairon.app4so.BaseAdapter.AtrativosAdapter;
import com.example.osvaldoairon.app4so.BaseAdapter.CoordenadasAdapterCidades;
import com.example.osvaldoairon.app4so.Modelo.AtrativosTuristicos;
import com.example.osvaldoairon.app4so.Modelo.Municipios;
import com.example.osvaldoairon.app4so.R;

import java.util.ArrayList;

public class FragmentInfTurismo extends Fragment {


    private static View view;

    private static ArrayList<AtrativosTuristicos> list_pontos = new ArrayList<AtrativosTuristicos>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(list_pontos!=null){


                View  view = inflater.inflate(R.layout.layout_listpontos,container,false);
                //Toast.makeText(getContext(), ""+list_cidades.size(), Toast.LENGTH_SHORT).show();
                ListView l1 =view.findViewById(R.id.list_pontos);
                AtrativosAdapter adapterPontos = new AtrativosAdapter(getContext(),list_pontos);
                if(l1!=null){
                    l1.setAdapter(adapterPontos);
                }
                return view;
            }



        view = inflater.inflate(R.layout.activity_fragment_inf_turismo,container,false);

        return view;
    }

    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public ArrayList<AtrativosTuristicos> recebeArray(ArrayList<AtrativosTuristicos> list){
        list_pontos=list;

        if(list_pontos.size() > 0){
            Log.w("NAO VAZIO ATRATIVOS","NAO VAZIO ATRATIVOS");
            return list_pontos;
        }
        return list;
    }
}
