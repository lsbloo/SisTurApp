package com.example.osvaldoairon.app4so.Fragments;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.osvaldoairon.app4so.BaseAdapter.CoordenadasAdapterCidades;
import com.example.osvaldoairon.app4so.Modelo.Coordenadas;
import com.example.osvaldoairon.app4so.Modelo.Municipios;
import com.example.osvaldoairon.app4so.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URISyntaxException;
import java.util.ArrayList;

import static android.content.Intent.getIntent;

public class FragmentInfCidade extends Fragment implements BaseSliderView.OnSliderClickListener,BaseSliderView.ImageLoadListener {

    private static View view2;
    private SliderLayout sliderLayout;
    private String[] imagens;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageReference;
    private StorageReference fotoRef;
    private static Uri urlUri;
    private static ArrayList<Municipios> list_cidades = new ArrayList<Municipios>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if(list_cidades!=null){
            View  view = inflater.inflate(R.layout.layout_listcity,container,false);
            //Toast.makeText(getContext(), ""+list_cidades.size(), Toast.LENGTH_SHORT).show();
            ListView l1 =view.findViewById(R.id.list_city);
            CoordenadasAdapterCidades adapterCidades = new CoordenadasAdapterCidades(list_cidades,getContext());
            if(l1!=null){
                l1.setAdapter(adapterCidades);
            }
            return view;
        }


        return null;


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


    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onStart(BaseSliderView target) {

    }

    @Override
    public void onEnd(boolean result, BaseSliderView target) {

    }
    public FragmentInfCidade(){
        }

    public ArrayList<Municipios> recebeArray(ArrayList<Municipios> list){
        list_cidades=list;

        if(list_cidades.size() > 0){
           Log.w("NAO VAZIO","NAO VAZIO");
            return list_cidades;
        }
        return list;
    }



}
