package com.example.osvaldoairon.app4so.ActivitysSecond;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.osvaldoairon.app4so.Modelo.AtrativosTuristicos;
import com.example.osvaldoairon.app4so.R;
import com.example.osvaldoairon.app4so.Sqlite.HelperSQLAtrativos;
import com.example.osvaldoairon.app4so.Util.FontesTerceiras;

public class InfDetailsAt extends AppCompatActivity {

    private HelperSQLAtrativos helperSQLAtrativos;

    private ImageButton btn_inf_cont;
    private TextView nomeAtrativo;
    private TextView descricaoAtrativo;
    private TextView inf_relevanteAtrativo;
    private TextView fonte_inf;
    private AtrativosTuristicos primeiroAtrativo;
    private AtrativosTuristicos secondAtrativo;
    private String aux;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_details_at);
        btn_inf_cont = (ImageButton)findViewById(R.id.btnat);
        helperSQLAtrativos = new HelperSQLAtrativos(this);
        helperSQLAtrativos.recoverDataSQLAtrativos();


          /*
        Widgets
         */
          nomeAtrativo=(TextView)findViewById(R.id.nomedetailAt);
          descricaoAtrativo=(TextView)findViewById(R.id.descriptionDetailAt);
          inf_relevanteAtrativo=(TextView)findViewById(R.id.infrelevantedetailAt);
          fonte_inf = (TextView)findViewById(R.id.fonte_detailsAt);

          /*
          ImageButton
           */

        btn_inf_cont = (ImageButton)findViewById(R.id.btnat);


        String position = getIntent().getStringExtra("detailAtrativo");
        if(position!=null){
            index = Integer.parseInt(position);
        }


        String primeiroPosition = getIntent().getStringExtra("position");
        if(primeiroPosition.equals("ok") && primeiroPosition != null){
            primeiroAtrativo = helperSQLAtrativos.getReturnList().get(0);
            nomeAtrativo.setText(primeiroAtrativo.getNome());
            descricaoAtrativo.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
            descricaoAtrativo.setText(primeiroAtrativo.getDescricao());
            inf_relevanteAtrativo.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
            inf_relevanteAtrativo.setText(primeiroAtrativo.getInformacoesRelevantes());
            fonte_inf.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
            fonte_inf.setText("Fonte de Informações: " + primeiroAtrativo.getFonteInformacoes());
            aux = "primeiro";
        }else{
            if(index!=0){
                secondAtrativo = helperSQLAtrativos.getReturnList().get(index);
                nomeAtrativo.setText(secondAtrativo.getNome());
                descricaoAtrativo.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                descricaoAtrativo.setText(secondAtrativo.getDescricao());
                inf_relevanteAtrativo.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                inf_relevanteAtrativo.setText(secondAtrativo.getInformacoesRelevantes());
                fonte_inf.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                fonte_inf.setText("Fonte de Informações: " + secondAtrativo.getFonteInformacoes());
                aux = "outros";
            }
        }

        btn_inf_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent et = new Intent(InfDetailsAt.this,InfContactAtrativo.class);
                if(aux.equals("primeiro")){
                    et.putExtra("position","0");
                }else{
                    et.putExtra("position",String.valueOf(index));
                }
                startActivity(et);

            }
        });
    }




}
