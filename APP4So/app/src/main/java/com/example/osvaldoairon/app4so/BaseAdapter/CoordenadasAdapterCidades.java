package com.example.osvaldoairon.app4so.BaseAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.example.osvaldoairon.app4so.Modelo.Municipios;
import com.example.osvaldoairon.app4so.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

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

        View view = LayoutInflater.from(ctx).inflate(R.layout.activity_inf,parent,false);

         TextView nomeCidade = (TextView)view.findViewById(R.id.nomeCidade);
         TextView populacaoCidade = (TextView)view.findViewById(R.id.populacao);
         TextView infRelevante = (TextView)view.findViewById(R.id.informacoesRelevante);
         CardView card = (CardView)view.findViewById(R.id.card);
         TextView areaTerritorial = (TextView)view.findViewById(R.id.areaTerritorial);


        final ImageView imgCidade=(ImageView)view.findViewById(R.id.fotocidade);
        Bitmap foto = resizeImgBitmap(ctx,convertImgDBtoBitmap(municipios.getFotos()),200,87);

        nomeCidade.setText(municipios.getNome());
        populacaoCidade.setText("População: " + municipios.getPopulacao() +" "+"Habitantes");
        areaTerritorial.setText("Área Territorial: " + municipios.getAreaTerritorial()+ " "+"quilômetros quadrados");
        infRelevante.setText(municipios.getInformacoesRelevantes());

        imgCidade.setImageBitmap(foto);


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

    public Bitmap convertImgDBtoBitmap(byte[] img){
        Bitmap bitmap = BitmapFactory.decodeByteArray(img,0,img.length);
        if(bitmap!=null){
            Log.d("BITMAP","O bitmap ta ok!");
        }
        return bitmap;
    }

    public static Bitmap resizeImgBitmap(Context ctx,Bitmap original_bit ,float newWidth,float newHeight){
        Bitmap novo=null;
        int w = original_bit.getWidth();
        int h = original_bit.getHeight();
        float densityFactor = ctx.getResources().getDisplayMetrics().density;
        float novoW = newWidth * densityFactor;
        float novoH = newHeight * densityFactor;
        float scalaW = novoW / w;
        float scalaH = novoH / h;
        Matrix matrix = new Matrix();
        matrix.postScale(scalaW, scalaH);
        novo = Bitmap.createBitmap(original_bit, 0, 0, w, h, matrix, true);
        return novo;
    }
}
