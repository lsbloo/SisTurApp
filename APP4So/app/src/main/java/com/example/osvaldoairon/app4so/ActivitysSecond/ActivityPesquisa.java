package com.example.osvaldoairon.app4so.ActivitysSecond;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.osvaldoairon.app4so.BaseAdapter.CoordenadasAdapterCidades;
import com.example.osvaldoairon.app4so.BaseAdapter.PesquisaAdapter;
import com.example.osvaldoairon.app4so.Modelo.Coordenadas;
import com.example.osvaldoairon.app4so.R;
import com.example.osvaldoairon.app4so.Sqlite.HelperBuscas;

import java.util.ArrayList;

public class ActivityPesquisa extends AppCompatActivity {
    private ArrayList<Coordenadas> list_pesquisas;
    private HelperBuscas helperBuscas;
    private int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list_pesquisas = new ArrayList<Coordenadas>();
        list_pesquisas= (ArrayList<Coordenadas>) getIntent().getSerializableExtra("list_pequisa");
        helperBuscas = new HelperBuscas(this);

        final PesquisaAdapter adapter = new PesquisaAdapter(this,list_pesquisas);

       // Toast.makeText(this, "Pesquisa"+list_pesquisas.size(), Toast.LENGTH_SHORT).show();
        if(list_pesquisas != null){
            final ListView listView = new ListView(ActivityPesquisa.this);
            listView.setAdapter(adapter);
            setContentView(listView);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                     i = position;

                    try{

                        new AlertDialog.Builder(ActivityPesquisa.this).setTitle("Deletar Pesquisa").setMessage("Tem certeza que quer deletar a pesquisa?").setPositiveButton("sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Coordenadas cdPosition = helperBuscas.getPosition(i);
                                helperBuscas.deleteUser(cdPosition);
                                helperBuscas.list_coordenadas.remove(i);
                                list_pesquisas.remove(i);
                                listView.setAdapter(adapter);



                                Toast.makeText(ActivityPesquisa.this, "Pesquisa Removida!", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ActivityPesquisa.this, "Operação Cancelada!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }).show();

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

        }else{
            Toast.makeText(ActivityPesquisa.this,"nenhuma pesquisa encontrada!", Toast.LENGTH_SHORT).show();
        }


    }

}
