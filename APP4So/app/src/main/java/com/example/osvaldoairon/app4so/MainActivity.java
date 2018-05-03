package com.example.osvaldoairon.app4so;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.osvaldoairon.app4so.ActivitysSecond.ActivityInf;
import com.example.osvaldoairon.app4so.Fragments.MapGoogleActivity;
import com.example.osvaldoairon.app4so.Modelo.Coordenadas;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggler;
    private MapGoogleActivity mapFragmento;
    private FragmentManager fragmentManager;
    private static ArrayList<Coordenadas> list_main;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list_main = new ArrayList<Coordenadas>();

        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        actionBarDrawerToggler = new ActionBarDrawerToggle(this,drawerLayout,R.string.Abr,R.string.Fec);
        actionBarDrawerToggler.setDrawerIndicatorEnabled(true);



        drawerLayout.addDrawerListener(actionBarDrawerToggler);
        actionBarDrawerToggler.syncState();

        NavigationView view = (NavigationView)findViewById(R.id.nav_Mapa);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //carrega o botao home no actionbar

        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.config){
                    Toast.makeText(MainActivity.this, "não implementado", Toast.LENGTH_SHORT).show();
                }else if(id == R.id.exibir_inf){
                    /*
                    exibir informações;
                     */
                    Intent at = new Intent(MainActivity.this, ActivityInf.class);
                    at.putExtra("arrayCidades",list_main);
                    startActivity(at);
                }else{
                    /*
                    botao rotas;
                     */
                }


                return true;
            }
        });

        /*
        Gerenciador de fragmentos fragmentManager

        FragmentTransaction = inicia uma transacao de fragmento podendo iniciar ou parar uma "transaction"

        é necessario dizer o layout que o fragmento vai ser carregado , e uma nova instanciacao do objeto fragmento
        pelo meotodo fragmentTransaction.add(layout,fragmento.obj, "string");

        iniciar o fragmento chamando o
        fragmentTransaction.commit() ou comit()AllowingStateLoss();
         */
        fragmentManager = getSupportFragmentManager();

        FragmentTransaction fg = fragmentManager.beginTransaction();

        fg.add(R.id.relativeLayoutx,new MapGoogleActivity(), "MapGOOGLE");
        fg.commitAllowingStateLoss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return  actionBarDrawerToggler.onOptionsItemSelected(item)||super.onOptionsItemSelected(item);
    }

    public ArrayList<Coordenadas> recebeArraymain(ArrayList<Coordenadas> list){

        if(list!=null){
//            Toast.makeText(MainActivity.this, "DOIDERA", Toast.LENGTH_SHORT).show();
            list_main = list;
            return list_main;
        }
        return null;
    }

    public MainActivity(){}
}
