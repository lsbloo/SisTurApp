package com.example.osvaldoairon.app4so.BaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.osvaldoairon.app4so.Fragments.FragmentInfCidade;
import com.example.osvaldoairon.app4so.Fragments.MapGoogleActivity;
import com.example.osvaldoairon.app4so.Modelo.Coordenadas;
import com.example.osvaldoairon.app4so.Modelo.Municipios;
import com.example.osvaldoairon.app4so.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CoordenadasAdapterCidades extends BaseAdapter implements BaseSliderView.OnSliderClickListener,BaseSliderView.ImageLoadListener{

    private ArrayList<Municipios> list;
    private Context ctx;
    private SliderLayout sliderLayout;
    private static String[] imagens;
    private StorageReference storageReference;
    private StorageReference fotoRef;
    private static Uri urlUri;
    private FirebaseDatabase firebaseDatabase;
    private static ArrayList<String> urls_list;


    public CoordenadasAdapterCidades(ArrayList<Municipios> arrayList, Context contexto){
        this.list=arrayList;
        this.ctx=contexto;
        urls_list = new ArrayList<String>();

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
        return 0;
    }
    public void init_firebase(){
        FirebaseApp.initializeApp(ctx);
        firebaseDatabase = firebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://apps4societyprotipo.appspot.com");


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        init_firebase();

        Municipios municipios = list.get(position);
        View view = LayoutInflater.from(ctx).inflate(R.layout.activity_fragment_inf_cidade,parent,false);

        TextView nomeCidade = (TextView)view.findViewById(R.id.nomeCidade);

        TextView descricaoCidade = (TextView)view.findViewById(R.id.descricaoCidade);

        TextView populacaoCidade = (TextView)view.findViewById(R.id.populacao);
        TextView areaTerritorial = (TextView)view.findViewById(R.id.areaTerritorial);
        //final ImageView imgCidade=(ImageView)view.findViewById(R.id.imgsrcc);

        nomeCidade.setText("Cidade: " + municipios.getNome());
        descricaoCidade.setText("Estado: " + municipios.getEstado());
        populacaoCidade.setText("População: " + municipios.getPopulacao() +" "+"habitantes");
        areaTerritorial.setText("Área Territorial: " + municipios.getAreaTerritorial());

       sliderLayout=(SliderLayout)view.findViewById(R.id.slidercidade);

       /*

       fotoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                TextSliderView aux = new TextSliderView(ctx);
                //aux.description("imagem1");
                aux.image(uri.toString());
                //aux.setOnImageLoadListener(CoordenadasAdapterCidades.this);
                //aux.setOnSliderClickListener(CoordenadasAdapterCidades.this);
                sliderLayout.addSlider(aux);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.w("LOGFAIL", "FAIL");
            }
        });

        */



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
        sliderLayout.startAutoCycle();

    }
}
