package com.example.osvaldoairon.app4so.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.osvaldoairon.app4so.ActivitysSecond.ActivityInf;
import com.example.osvaldoairon.app4so.BaseAdapter.CoordenadasAdapterCidades;
import com.example.osvaldoairon.app4so.MainActivity;
import com.example.osvaldoairon.app4so.Modelo.Coordenadas;
import com.example.osvaldoairon.app4so.R;
import com.example.osvaldoairon.app4so.service.LocalizacaoIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class MapGoogleActivity extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback , Serializable{




    public static final int REQUEST_ERROR_PLAY_ = 1;
    public static final int REQUEST_CHECAR_GPS = 2;

    private LatLng coordenadasRequest;

    private static GoogleMap mMap;
    private static GoogleApiClient mGoogleApiCliente;
    private static LatLng mOrigem;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Handler handler;
    private static boolean dialog;
    private int tentativas;

    private LatLng localizacaoAtual;
    private static GoogleMap meuMapa;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private static ArrayList<Coordenadas> list_recover;
    private static ArrayList<Coordenadas> list_recover_point;
    private Context ctx;
    public static ArrayList<Coordenadas> list_buscaSql= new ArrayList<Coordenadas>();
    private static int tipo_mapa=0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list_recover = new ArrayList<Coordenadas>();
        list_recover_point = new ArrayList<Coordenadas>();

        iniciarFirebaseDatabase();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        getMapAsync(this);
        handler = new Handler();
        dialog = savedInstanceState == null;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        askPermissions();
        conectAPICLIENTE();
        //addCoordenadasCidadesDefinidas();
        //addPontosTuristicosValeDefinidos();


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        lastLocation(googleMap);
    }

    public int changeMAP(int type){
        tipo_mapa=type;
        Log.v("TIPOMAPA", "TIPOMAPA"+type);
        return tipo_mapa;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        statusGPS();


    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiCliente.disconnect();

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {

            try {
                // :
                connectionResult.startResolutionForResult(getActivity(), REQUEST_ERROR_PLAY_);
            } catch (IntentSender.SendIntentException e) {
                /*
                !
                 */

                e.printStackTrace();
            }
        } else {
            /*
            Exibi algum detalhe de possivel erro!
             */
            //showDetails(this, connectionResult.getErrorCode());
        }

    }


    @SuppressLint("ValidFragment")
    public MapGoogleActivity(Context ctx, ArrayList<Coordenadas> list){
        this.ctx=ctx;
        this.list_buscaSql=list;
    }



    @SuppressLint("MissingPermission")
    public int lastLocation(final GoogleMap map) {

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        tentativas = 0;
                        mOrigem = new LatLng(location.getAltitude(), location.getLongitude());
                        Log.d("LATITUDE", "ALTITUDE" + location.getLongitude());
                        Log.d("LONGITUDE", "LONGITUDE" + location.getLatitude());
                        localizacaoAtual = new LatLng(location.getLatitude(),location.getLongitude());
                        //Toast.makeText(getActivity(), ""+tipo_mapa, Toast.LENGTH_SHORT).show();
                        updateMap(map,localizacaoAtual,tipo_mapa);
                        recuperarDadosCidades(map);
                        recuperarDadosPontos(map);
                        marcarPontosBusca(map);
                    } else if (tentativas < 10) {
                        tentativas++;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                            }
                        }, 1000); // a cada um segundo
                    } else {
                        Toast.makeText(getActivity(), "lastLocation fail", Toast.LENGTH_SHORT).show();
                    }

                }
            });


        return 0;
    }

    @SuppressLint("MissingPermission")
    public void updateMap(GoogleMap map, LatLng localizacaoAtual,int type) {
        // mMap = mapFragment.getMap(); // metodo depreciado api google 8.1.0 \ ATUAL 12.1.0
        // mMap.getUiSettings().setMapToolbarEnabled(true);

        /*
                \/ >> objeto Mapa do tipo hibrido:

                Dados fotográficos de satélite com mapas de vias adicionados.
                Os rótulos de vias e recursos também são visíveis.
                 //Metódo setMapType
         */
        if(type == 1){
            map.setMyLocationEnabled(true);
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacaoAtual, 13));
            map.addMarker(new MarkerOptions().title("Localização Atual").position(localizacaoAtual));
        }else if (type == 2){
            map.setMyLocationEnabled(true);
            map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacaoAtual, 13));
            map.addMarker(new MarkerOptions().title("Localização Atual").position(localizacaoAtual));
        }else if(type == 3){
            map.setMyLocationEnabled(true);
            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacaoAtual, 13));
            map.addMarker(new MarkerOptions().title("Localização Atual").position(localizacaoAtual));
        }else if(type==4){
            map.setMyLocationEnabled(true);
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacaoAtual, 13));
            map.addMarker(new MarkerOptions().title("Localização Atual").position(localizacaoAtual));
        }else{
            map.setMyLocationEnabled(true);
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacaoAtual, 13));
            map.addMarker(new MarkerOptions().title("Localização Atual").position(localizacaoAtual));
        }



    }

    public Fragment startActivite(){
        return new MapGoogleActivity();
    }



    public void statusGPS() {
        Log.d("oks", "kPASKApskapskpaskapskpaks");

        final LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        final LocationSettingsRequest.Builder locationsettingsrequest = new LocationSettingsRequest.Builder();
        locationsettingsrequest.setAlwaysShow(true);
        locationsettingsrequest.addLocationRequest(locationRequest);

        PendingResult<LocationSettingsResult> resultado = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiCliente, locationsettingsrequest.build());

        resultado.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();

                //Toast.makeText(MapGoogleActivity.this, "KKKKKKKK", Toast.LENGTH_SHORT).show();

                switch (status.getStatusCode()) {

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.d("NGVL", "ERROR LINHA 215 RESULTADO CALLBACK");
                        break;

                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        if (dialog) {
                            try {
                                status.startResolutionForResult(getActivity(), REQUEST_CHECAR_GPS);
                                dialog = false;
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case LocationSettingsStatusCodes.SUCCESS:
                        break;
                }
            }
        });

    }

    public void ligarGPS() {
        /*
        Location Manager é obsoleto a api Google Location é melhor pra fazer essas verificaçoes
         */
       // LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
       // boolean GPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

       // if (!GPSEnabled) {
        //    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        //}
    }
    public MapGoogleActivity() {
    }


    private synchronized void conectAPICLIENTE() {
        mGoogleApiCliente = new GoogleApiClient.Builder(getActivity()).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();

        mGoogleApiCliente.connect();
    }

    public void askPermissions(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ){//Can add more as per requirement

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }else if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                ){
            boolean completed = true;
        }

    }

    public void iniciarFirebaseDatabase(){
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = firebaseDatabase.getInstance();
        //fireBaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();

    }
    public void marcarPontosBusca(final GoogleMap map){

        if(list_buscaSql.size()!=0){
            for(int i =0 ; i < list_buscaSql.size();i++){
                double latitude = list_buscaSql.get(i).getLatitude();
                double longitude = list_buscaSql.get(i).getLongitude();
                //Toast.makeText(getActivity(), "APSPASKAPSKPASK"+latitude, Toast.LENGTH_SHORT).show();

                LatLng lat = new LatLng(latitude,longitude);
                map.addMarker(new MarkerOptions().position(lat).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
        }else{
            Toast.makeText(getActivity(), "Armazenados: "+list_buscaSql.size(), Toast.LENGTH_SHORT).show();
        }


    }
    public void actived(){
        Log.d("ACT","act");
    }

    public void recuperarDadosCidades(final GoogleMap map){
        databaseReference.child("Coordenadas-CidadesVale").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot obj : dataSnapshot.getChildren()) {
                    Coordenadas coordenadas = (Coordenadas) obj.getValue(Coordenadas.class);
                    list_recover.add(coordenadas);
                }
                for(int i = 0;i<list_recover.size();i++){
                    double latitude = list_recover.get(i).getLatitude();
                    double longitude = list_recover.get(i).getLongitude();
                    LatLng locate = new LatLng(latitude,longitude);
                    map.addMarker(new MarkerOptions().title(list_recover.get(i).getNomeCidade()).snippet(list_recover.get(i).getDescricao()).position(locate));

                }
                MainActivity main = new MainActivity();
                main.recebeArraymain(list_recover);
                if(localizacaoAtual!=null){
                    main.recebeLocalizacao(localizacaoAtual);
                }else{
                    Log.d("LOCATION MAP","FAIL NULL LOCATION MAPGOOGLEACT");
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void recuperarDadosPontos(final GoogleMap map){
        databaseReference.child("Coordenadas-PontoTuristico").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot objTurismo: dataSnapshot.getChildren()){
                    Coordenadas coordenadasTurismo = (Coordenadas)objTurismo.getValue(Coordenadas.class);
                    list_recover_point.add(coordenadasTurismo);
                }
                for(int i = 0 ; i<list_recover_point.size(); i++){
                    double latitude = list_recover_point.get(i).getLatitude();
                    double longitude = list_recover_point.get(i).getLongitude();
                    LatLng locatePoint = new LatLng(latitude,longitude);
                    map.addMarker(new MarkerOptions().title(list_recover_point.get(i).getNomePontoTuristico()).snippet(list_recover_point.get(i).getDescricao()).position(locatePoint).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public void addPontosTuristicosValeDefinidos(){
        Coordenadas coordenadasReservaGuaribas = new Coordenadas();
        coordenadasReservaGuaribas.setNomePontoTuristico("Reserva Biologica Guaribas");
        coordenadasReservaGuaribas.setLatitude(-6.721389);
        coordenadasReservaGuaribas.setLongitude(-35.180278);
        coordenadasReservaGuaribas.setDescricao("localizada nos municípios paraibanos de Mamanguape (91,59%) e Rio Tinto (8,41%).");
        coordenadasReservaGuaribas.setId(UUID.randomUUID().toString());


        Coordenadas coordenadasMaracuja = new Coordenadas();
        coordenadasMaracuja.setNomePontoTuristico("Sítio Maracujá");
        coordenadasMaracuja.setLatitude(-6.81694487);
        coordenadasMaracuja.setLongitude(-35.10588752);
        coordenadasMaracuja.setDescricao("É um pequeno vilarejo localizado próximo a Rio Tinto");
        coordenadasMaracuja.setId(UUID.randomUUID().toString());

        Coordenadas coordenadasLungden = new Coordenadas();
        coordenadasLungden.setNomePontoTuristico("Casarão dos Lungdren");
        coordenadasLungden.setLatitude(-6.79652163);
        coordenadasLungden.setLongitude(-35.06456633);
        coordenadasLungden.setDescricao("O palacete tem três andares e abrigava um rico mobiliário, tapetes italianos, lustres e azulejos decorados.");
        coordenadasLungden.setId(UUID.randomUUID().toString());


        Coordenadas coordenadasAldeiaTramatia = new Coordenadas();
        coordenadasAldeiaTramatia.setNomePontoTuristico("Aldeia Tramataia");
        coordenadasAldeiaTramatia.setLatitude(-6.7415743);
        coordenadasAldeiaTramatia.setLongitude(-34.943477);
        coordenadasAldeiaTramatia.setDescricao("Tramataia é uma povoação indígena do município de Marcação, no estado brasileiro da Paraíba.");
        coordenadasAldeiaTramatia.setId(UUID.randomUUID().toString());

        Coordenadas coordenadasPraiaCoqueirinho = new Coordenadas();
        coordenadasPraiaCoqueirinho.setNomePontoTuristico("Praia Coqueirinho Marcação");
        coordenadasPraiaCoqueirinho.setLatitude(-6.74076476);
        coordenadasPraiaCoqueirinho.setLongitude(-34.92794121);
        coordenadasPraiaCoqueirinho.setDescricao("Praia localizada na regiao de Marcação");
        coordenadasPraiaCoqueirinho.setId(UUID.randomUUID().toString());

        Coordenadas coordenadasUsina = new Coordenadas();
        coordenadasUsina.setNomePontoTuristico("Usina Monte Alegre");
        coordenadasUsina.setLatitude(-6.8597843);
        coordenadasUsina.setLongitude(-35.1308254);
        coordenadasUsina.setDescricao("Uma das usinas da empresa 'AÇUCAR ALEGRE'");
        coordenadasUsina.setId(UUID.randomUUID().toString());

        databaseReference.child("Coordenadas-PontoTuristico").child(coordenadasReservaGuaribas.getId()).setValue(coordenadasReservaGuaribas);
        databaseReference.child("Coordenadas-PontoTuristico").child(coordenadasMaracuja.getId()).setValue(coordenadasMaracuja);
        databaseReference.child("Coordenadas-PontoTuristico").child(coordenadasLungden.getId()).setValue(coordenadasLungden);
        databaseReference.child("Coordenadas-PontoTuristico").child(coordenadasAldeiaTramatia.getId()).setValue(coordenadasAldeiaTramatia);
        databaseReference.child("Coordenadas-PontoTuristico").child(coordenadasPraiaCoqueirinho.getId()).setValue(coordenadasPraiaCoqueirinho);
        databaseReference.child("Coordenadas-PontoTuristico").child(coordenadasUsina.getId()).setValue(coordenadasUsina);




    }


    public void addCoordenadasCidadesDefinidas(){

        Coordenadas coordenadasMamanguape = new Coordenadas();
        coordenadasMamanguape.setNomeCidade("Mamanguape");
        coordenadasMamanguape.setLatitude(-6.83963229);
        coordenadasMamanguape.setLongitude(-35.13653453);
        coordenadasMamanguape.setDescricao("Mamanguape é um município do estado da Paraíba, no Brasil. É sede da Região Metropolitana do Vale do Mamanguape. Sua população em 2016 foi estimada pelo Instituto Brasileiro de Geografia e Estatística em 44.694 habitantes,[3] distribuídos em 340.482 quilômetros quadrados de área.É considerada uma cidade histórica devido à sua importância na colonização da capitania da Paraíba,marcada pela exploração do Pau-Brasil e anos depois plantio da cana-de-açúcar em seu vasto território que inicialmente compreendia todo o Vale do Mamanguape.");
        coordenadasMamanguape.setId(UUID.randomUUID().toString());
        coordenadasMamanguape.setUrlfotoCidade("fotosCidades/mamanguapedois.jpg");


        Coordenadas coordenadasCuiteMamanguape = new Coordenadas();
        coordenadasCuiteMamanguape.setNomeCidade("Cuité de Mamanguape");
        coordenadasCuiteMamanguape.setLatitude(-6.9145768);
        coordenadasCuiteMamanguape.setLongitude(-35.25024273);
        coordenadasCuiteMamanguape.setDescricao("Cuité de Mamanguape, município no estado da Paraíba (Brasil), localizado na Região Geográfica Imediata de João Pessoa. De acordo com o IBGE (Instituto Brasileiro de Geografia e Estatística), no ano de 2016 sua população era estimada em 6.349 habitantes. Área territorial de 110 km².");
        coordenadasCuiteMamanguape.setId(UUID.randomUUID().toString());
        coordenadasCuiteMamanguape.setUrlfotoCidade("fotosCidades/cuitemamanguape.jpeg");

        Coordenadas coordenadasItaporoca = new Coordenadas();
        coordenadasItaporoca.setNomeCidade("Itapororoca");
        coordenadasItaporoca.setLatitude(-6.8286181);
        coordenadasItaporoca.setLongitude(-35.2457153);
        coordenadasItaporoca.setDescricao("Itapororoca é um município da Região Geográfica Imediata de Mamanguape-Rio Tinto, no estado da Paraíba, no Nordeste do Brasil. De acordo com o Instituto Brasileiro de Geografia e Estatística, no ano de 2016 sua população era de 16.997 hab. Sua área é de 146 km², sendo seus biomas predominantes o cerrado que devido a exploração da monocultura da cana-de-açucar está quase todo devastado e a mata atlântica.");
        coordenadasItaporoca.setId(UUID.randomUUID().toString());
        coordenadasItaporoca.setUrlfotoCidade("fotosCidades/itapororoca.jpeg");

        Coordenadas coordenadasJacarau = new Coordenadas();
        coordenadasJacarau.setNomeCidade("Jacarau");
        coordenadasJacarau.setLatitude(-6.6150367);
        coordenadasJacarau.setLongitude(-35.2911358);
        coordenadasJacarau.setDescricao("Jacaraú do Estado do Paraíba. Os habitantes se chamam jacarauenses.\n" +
                "O município se estende por 253 km² e contava com 13 952 habitantes no último censo. A densidade demográfica é de 55,1 habitantes por km² no território do município.");
        coordenadasJacarau.setId(UUID.randomUUID().toString());
        coordenadasJacarau.setUrlfotoCidade("fotosCidades/jacarau.jpeg");

        Coordenadas coordenadasMataraca = new Coordenadas();
        coordenadasMataraca.setNomeCidade("Mataraca");
        coordenadasMataraca.setLatitude(-6.5928178);
        coordenadasMataraca.setLongitude(-35.0576436);
        coordenadasMataraca.setDescricao("Mataraca é um município brasileiro do estado da Paraíba localizado na Região Geográfica Imediata de Mamanguape-Rio Tinto. De acordo com o IBGE (Instituto Brasileiro de Geografia e Estatística), no ano de 2016 sua população era estimada em 8.345 habitantes. Área territorial de 174 km².");
        coordenadasMataraca.setId(UUID.randomUUID().toString());
        coordenadasMataraca.setUrlfotoCidade("fotosCidades/mataraca.jpg");

        Coordenadas coordenadasBaia = new Coordenadas();
        coordenadasBaia.setNomeCidade("Baia da traição");
        coordenadasBaia.setLatitude(-6.6900838);
        coordenadasBaia.setLongitude(-34.93421);
        coordenadasBaia.setDescricao("Baía da Traição é um município do estado da Paraíba, no Brasil. De acordo com o Instituto Brasileiro de Geografia e Estatística, no ano de 2016 sua população era estimada em 8.951 habitantes. Cerca de 90% do município está dentro de reservas indígenas dos Potiguaras.");
        coordenadasBaia.setId(UUID.randomUUID().toString());
        coordenadasBaia.setUrlfotoCidade("fotosCidades/baiadatraicao.jpg");

        Coordenadas coordenadasCurraldeCima = new Coordenadas();
        coordenadasCurraldeCima.setNomeCidade("Curral de Cima");
        coordenadasCurraldeCima.setLatitude(-6.7347555);
        coordenadasCurraldeCima.setLongitude(-35.2917432);
        coordenadasCurraldeCima.setDescricao("Curral de Cima, município no estado da Paraíba (Brasil), localizado na Região Geográfica Imediata de Mamanguape-Rio Tinto.");
        coordenadasCurraldeCima.setId(UUID.randomUUID().toString());
        coordenadasCurraldeCima.setUrlfotoCidade("fotosCidades/ curraldecima.jpeg");

        databaseReference.child("Coordenadas-CidadesVale").child(coordenadasMamanguape.getId()).setValue(coordenadasMamanguape);
        databaseReference.child("Coordenadas-CidadesVale").child(coordenadasBaia.getId()).setValue(coordenadasBaia);
        databaseReference.child("Coordenadas-CidadesVale").child(coordenadasCuiteMamanguape.getId()).setValue(coordenadasCuiteMamanguape);
        databaseReference.child("Coordenadas-CidadesVale").child(coordenadasJacarau.getId()).setValue(coordenadasJacarau);
        databaseReference.child("Coordenadas-CidadesVale").child(coordenadasItaporoca.getId()).setValue(coordenadasItaporoca);
        databaseReference.child("Coordenadas-CidadesVale").child(coordenadasMataraca.getId()).setValue(coordenadasMataraca);
        databaseReference.child("Coordenadas-CidadesVale").child(coordenadasCurraldeCima.getId()).setValue(coordenadasCurraldeCima);



    }


}
