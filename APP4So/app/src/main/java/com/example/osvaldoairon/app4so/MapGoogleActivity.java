package com.example.osvaldoairon.app4so;

import android.Manifest;
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
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.osvaldoairon.app4so.HelperFirebase.RecoveryDatabase;
import com.example.osvaldoairon.app4so.Modelo.Coordenadas;
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
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class MapGoogleActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {

    public static final int REQUEST_ERROR_PLAY_ = 1;
    public static final int REQUEST_CHECAR_GPS = 2;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_google);
        list_recover = new ArrayList<Coordenadas>();
        iniciarFirebaseDatabase();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragmento = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragmento.getMapAsync(this);
        handler = new Handler();
        dialog = savedInstanceState == null;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        askPermissions();
        conectAPICLIENTE();
        //addCoordenadasCidadesDefinidas();


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
                connectionResult.startResolutionForResult(this, REQUEST_ERROR_PLAY_);
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
            showDetails(this, connectionResult.getErrorCode());
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putBoolean("dialog", dialog);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        dialog = savedInstanceState.getBoolean("dialog", true);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ActivityResume", "ActivityResume");
    }

    @Override
    protected void onStop() {
        if (mGoogleApiCliente != null && mGoogleApiCliente.isConnected()) {
            mGoogleApiCliente.disconnect();
        }
        handler.removeCallbacks(null);
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("DestroY Activity ", "DestroayActivity");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ERROR_PLAY_ && resultCode == RESULT_OK) {
            mGoogleApiCliente.disconnect();
        } else if (requestCode == REQUEST_CHECAR_GPS) {
            if (resultCode == RESULT_OK) {
                tentativas = 0;
                handler.removeCallbacks(null);
            }
        } else {
            Toast.makeText(this, "Erro gps", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    public void showDetails(Context ctx, int resultado) {
        final String TAG = "DIALOG_ERRO_PLAY_SERVICES";
        if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
            Log.d("ERROR FRAGMENT", "ERROR FRAGMENT METODO SHOWDETAILS");
        }

    }

    public int lastLocation(final GoogleMap map) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //Location locate = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiCliente);

            return 0;
        }else{
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        tentativas = 0;
                        mOrigem = new LatLng(location.getAltitude(), location.getLongitude());
                        Log.d("LATITUDE", "ALTITUDE" + location.getLongitude());
                        Log.d("LONGITUDE", "LONGITUDE" + location.getLatitude());
                        localizacaoAtual = new LatLng(location.getLatitude(),location.getLongitude());
                        updateMap(map,localizacaoAtual);
                        recuperarDadosCidades(map);
                    } else if (tentativas < 10) {
                        tentativas++;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                            }
                        }, 1000); // a cada um segundo
                    } else {
                        Toast.makeText(MapGoogleActivity.this, "lastLocation fail", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
        return 0;
    }

    public void updateMap(GoogleMap map, LatLng localizacaoAtual) {
        // mMap = mapFragment.getMap(); // metodo depreciado api google 8.1.0 \ ATUAL 12.1.0
        // mMap.getUiSettings().setMapToolbarEnabled(true);

        /*
                \/ >> objeto Mapa do tipo hibrido:

                Dados fotográficos de satélite com mapas de vias adicionados.
                Os rótulos de vias e recursos também são visíveis.
                 //Metódo setMapType
         */
        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // mMap.setMyLocationEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }else{
            map.setMyLocationEnabled(true);
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacaoAtual, 13));
            map.addMarker(new MarkerOptions().title("Localização Atual").snippet("Rio Tinto é um município brasileiro localizado na Região Metropolitana de João Pessoa").position(localizacaoAtual));
        }

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
                                status.startResolutionForResult(MapGoogleActivity.this, REQUEST_CHECAR_GPS);
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
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean GPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!GPSEnabled) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }

    private synchronized void conectAPICLIENTE() {
        mGoogleApiCliente = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();

        mGoogleApiCliente.connect();
    }

    public void askPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ){//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }else if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                ){
            boolean completed = true;
        }

    }

    public void iniciarFirebaseDatabase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = firebaseDatabase.getInstance();
        //fireBaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();

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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void addCoordenadasCidadesDefinidas(){

        Coordenadas coordenadasMamanguape = new Coordenadas();
        coordenadasMamanguape.setNomeCidade("Mamanguape");
        coordenadasMamanguape.setLatitude(-6.83963229);
        coordenadasMamanguape.setLongitude(-35.13653453);
        coordenadasMamanguape.setDescricao("Mamanguape é um município do estado da Paraíba, no Brasil. É sede da Região Metropolitana do Vale do Mamanguape. Sua população em 2016 foi estimada pelo Instituto Brasileiro de Geografia e Estatística em 44.694 habitantes,[3] distribuídos em 340.482 quilômetros quadrados de área.É considerada uma cidade histórica devido à sua importância na colonização da capitania da Paraíba,marcada pela exploração do Pau-Brasil e anos depois plantio da cana-de-açúcar em seu vasto território que inicialmente compreendia todo o Vale do Mamanguape.");
        coordenadasMamanguape.setId(UUID.randomUUID().toString());


        Coordenadas coordenadasCuiteMamanguape = new Coordenadas();
        coordenadasCuiteMamanguape.setNomeCidade("Cuité de Mamanguape");
        coordenadasCuiteMamanguape.setLatitude(-6.9145768);
        coordenadasCuiteMamanguape.setLongitude(-35.25024273);
        coordenadasMamanguape.setDescricao("Cuité de Mamanguape, município no estado da Paraíba (Brasil), localizado na Região Geográfica Imediata de João Pessoa. De acordo com o IBGE (Instituto Brasileiro de Geografia e Estatística), no ano de 2016 sua população era estimada em 6.349 habitantes. Área territorial de 110 km².");
        coordenadasCuiteMamanguape.setId(UUID.randomUUID().toString());

        Coordenadas coordenadasItaporoca = new Coordenadas();
        coordenadasItaporoca.setNomeCidade("Itapororoca");
        coordenadasItaporoca.setLatitude(-6.8286181);
        coordenadasItaporoca.setLongitude(-35.2457153);
        coordenadasItaporoca.setDescricao("Itapororoca é um município da Região Geográfica Imediata de Mamanguape-Rio Tinto, no estado da Paraíba, no Nordeste do Brasil. De acordo com o Instituto Brasileiro de Geografia e Estatística, no ano de 2016 sua população era de 16.997 hab. Sua área é de 146 km², sendo seus biomas predominantes o cerrado que devido a exploração da monocultura da cana-de-açucar está quase todo devastado e a mata atlântica.");
        coordenadasItaporoca.setId(UUID.randomUUID().toString());

        Coordenadas coordenadasJacarau = new Coordenadas();
        coordenadasJacarau.setNomeCidade("Jacarau");
        coordenadasJacarau.setLatitude(-6.6150367);
        coordenadasJacarau.setLongitude(-35.2911358);
        coordenadasJacarau.setDescricao("Jacaraú do Estado do Paraíba. Os habitantes se chamam jacarauenses.\n" +
                "O município se estende por 253 km² e contava com 13 952 habitantes no último censo. A densidade demográfica é de 55,1 habitantes por km² no território do município.");
        coordenadasJacarau.setId(UUID.randomUUID().toString());

        Coordenadas coordenadasMataraca = new Coordenadas();
        coordenadasMataraca.setNomeCidade("Mataraca");
        coordenadasMataraca.setLatitude(-6.5928178);
        coordenadasMataraca.setLongitude(-35.0576436);
        coordenadasMataraca.setDescricao("Mataraca é um município brasileiro do estado da Paraíba localizado na Região Geográfica Imediata de Mamanguape-Rio Tinto. De acordo com o IBGE (Instituto Brasileiro de Geografia e Estatística), no ano de 2016 sua população era estimada em 8.345 habitantes. Área territorial de 174 km².");
        coordenadasMataraca.setId(UUID.randomUUID().toString());

        Coordenadas coordenadasBaia = new Coordenadas();
        coordenadasBaia.setNomeCidade("Baia da traição");
        coordenadasBaia.setLatitude(-6.6900838);
        coordenadasBaia.setLongitude(-34.93421);
        coordenadasBaia.setDescricao("Baía da Traição é um município do estado da Paraíba, no Brasil. De acordo com o Instituto Brasileiro de Geografia e Estatística, no ano de 2016 sua população era estimada em 8.951 habitantes. Cerca de 90% do município está dentro de reservas indígenas dos Potiguaras.");
        coordenadasBaia.setId(UUID.randomUUID().toString());

        databaseReference.child("Coordenadas-CidadesVale").child(coordenadasMamanguape.getId()).setValue(coordenadasMamanguape);
        databaseReference.child("Coordenadas-CidadesVale").child(coordenadasBaia.getId()).setValue(coordenadasBaia);
        databaseReference.child("Coordenadas-CidadesVale").child(coordenadasCuiteMamanguape.getId()).setValue(coordenadasCuiteMamanguape);
        databaseReference.child("Coordenadas-CidadesVale").child(coordenadasJacarau.getId()).setValue(coordenadasJacarau);
        databaseReference.child("Coordenadas-CidadesVale").child(coordenadasItaporoca.getId()).setValue(coordenadasItaporoca);
        databaseReference.child("Coordenadas-CidadesVale").child(coordenadasMataraca.getId()).setValue(coordenadasMataraca);
    }


}
