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
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.osvaldoairon.app4so.Fragments.FragmentInfCidade;
import com.example.osvaldoairon.app4so.Fragments.MapGoogleActivity;
import com.example.osvaldoairon.app4so.Modelo.Coordenadas;
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

    private ArrayList<Coordenadas> list;
    private Context ctx;
    private SliderLayout sliderLayout;
    private static String[] imagens;
    private StorageReference storageReference;
    private StorageReference fotoRef;
    private static Uri urlUri;
    private FirebaseDatabase firebaseDatabase;


    public CoordenadasAdapterCidades(ArrayList<Coordenadas> arrayList, Context contexto){
        this.list=arrayList;
        this.ctx=contexto;

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
        storageReference = FirebaseStorage.getInstance().getReference();
        fotoRef = storageReference.child("fotosCidades/mamanguapedois.jpg");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        init_firebase();


        Coordenadas coordenadasCidade = list.get(position);
        View view = LayoutInflater.from(ctx).inflate(R.layout.activity_fragment_inf_cidade,parent,false);

        TextView nomeCidade = (TextView)view.findViewById(R.id.nomeCidade);

        TextView descricaoCidade = (TextView)view.findViewById(R.id.descricaoCidade);

        nomeCidade.setText("Cidade: " + coordenadasCidade.getNomeCidade());
        descricaoCidade.setText(" " + coordenadasCidade.getDescricao());

        slideImgCidade(view);


        return view;
    }

    public void slideImgCidade(View view){
        sliderLayout=(SliderLayout)view.findViewById(R.id.slidercidade);

        fotoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Log.d("LOG" , "SUCESSO");
                urlUri=uri;

                //Toast.makeText(getActivity(), ""+urlUri.toString(), Toast.LENGTH_SHORT).show();
                imagens = new String[]{uri.toString(), "https://www.google.com.br/search?hl=pt-BR&authuser=0&tbm=isch&source=hp&biw=1299&bih=627&ei=s87nWpn6JMKBwgTaiLXgBg&q=bolas&oq=bolas&gs_l=img.3..0l10.2278.3424.0.3578.6.6.0.0.0.0.358.614.2-1j1.2.0....0...1ac.1.64.img..4.2.610.0...0.0MxFIzd0mSM#imgrc=-F9n4Sm57vxZ-M:"};

                //if(sliderLayout != null){
                 //   Toast.makeText(ctx, "oks", Toast.LENGTH_SHORT).show();
                //}else{
                 //   Toast.makeText(ctx, "N oks", Toast.LENGTH_SHORT).show();
               // }


                TextSliderView aux = new TextSliderView(ctx);
                //aux.description("imagem1");
                aux.image(imagens[0]);
                aux.setOnImageLoadListener(CoordenadasAdapterCidades.this);
                aux.setOnSliderClickListener(CoordenadasAdapterCidades.this);


                sliderLayout.addSlider(aux);

                /*
                TextSliderView aux1 = new TextSliderView(ctx);
                //aux1.description("imagem2");
                aux1.image(imagens[1]);
                aux1.setOnImageLoadListener(CoordenadasAdapterCidades.this);
                aux1.setOnSliderClickListener(CoordenadasAdapterCidades.this);

                sliderLayout.addSlider(aux1);
                 */



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.w("LOGFAIL", "FAIL");
            }
        });

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
