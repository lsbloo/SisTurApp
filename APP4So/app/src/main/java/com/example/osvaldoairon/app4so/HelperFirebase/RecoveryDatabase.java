package com.example.osvaldoairon.app4so.HelperFirebase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.osvaldoairon.app4so.Modelo.Coordenadas;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class RecoveryDatabase {

    public static ArrayList<Coordenadas> list_cidades_predefinidas;
    public static ArrayList<Coordenadas> list_cidades_predefinidas_;

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private Context contexto;

    public RecoveryDatabase(Context ctx ){
        this.contexto=ctx;
        this.list_cidades_predefinidas = new ArrayList<Coordenadas>();
        this.list_cidades_predefinidas_ = new ArrayList<Coordenadas>();
;    }

    public ArrayList recoveryFire(Context ctx){
        FirebaseApp.initializeApp(ctx);
        firebaseDatabase = firebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Coordenadas-CidadesVale").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot obj : dataSnapshot.getChildren()) {
                    Coordenadas coordenadas = (Coordenadas) obj.getValue(Coordenadas.class);
                    list_cidades_predefinidas.add(coordenadas);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return list_cidades_predefinidas;
    }


    public int retornaList(){
        Toast.makeText(contexto, "" +list_cidades_predefinidas.size(), Toast.LENGTH_SHORT).show();
        return list_cidades_predefinidas.size();
    }


}
