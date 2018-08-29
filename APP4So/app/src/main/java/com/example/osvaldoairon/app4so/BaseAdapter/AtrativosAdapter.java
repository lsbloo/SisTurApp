package com.example.osvaldoairon.app4so.BaseAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AtrativosTuristicos at = list.get(position);

        View view = LayoutInflater.from(ctx).inflate(R.layout.activity_inf_at,parent,false);


        TextView nomeAtrativo = (TextView)view.findViewById(R.id.nomeAtrativo);
        CardView card = (CardView)view.findViewById(R.id.cardAt);

         /*
        Widgets
         */


        final ImageView imgAtrativo=(ImageView)view.findViewById(R.id.fotoatrativo);
        Bitmap foto = resizeImgBitmap(ctx,convertImgDBtoBitmap(at.getFotos_byte()),200,87);

        nomeAtrativo.setText(at.getNome());


        imgAtrativo.setImageBitmap(foto);


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

    public Bitmap convertImgDBtoBitmap(byte[] img){
        Bitmap bitmap = BitmapFactory.decodeByteArray(img,0,img.length);
        if(bitmap!=null){
            Log.d("BITMAP","O bitmap ta ok! Atrativo");
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
