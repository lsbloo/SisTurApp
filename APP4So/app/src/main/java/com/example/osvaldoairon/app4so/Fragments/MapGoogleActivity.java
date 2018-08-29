package com.example.osvaldoairon.app4so.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
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
import com.example.osvaldoairon.app4so.Exceptions.ErrosDeConnexao;
import com.example.osvaldoairon.app4so.MainActivity;
import com.example.osvaldoairon.app4so.Modelo.AtrativosTuristicos;
import com.example.osvaldoairon.app4so.Modelo.Coordenadas;
import com.example.osvaldoairon.app4so.Modelo.Municipios;
import com.example.osvaldoairon.app4so.R;
import com.example.osvaldoairon.app4so.Sqlite.HelperSQLAtrativos;
import com.example.osvaldoairon.app4so.Sqlite.HelperSQLMunicipios;
import com.example.osvaldoairon.app4so.rest.CriarConexaoAtrativoTuristico;
import com.example.osvaldoairon.app4so.rest.CriarConexaoMunicipios;
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

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class MapGoogleActivity extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback , Serializable {


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
    public static ArrayList<Coordenadas> list_buscaSql = new ArrayList<Coordenadas>();
    private static int tipo_mapa = 0;

    private static HelperSQLMunicipios helper;
    private static HelperSQLAtrativos helperAtratativos;

    private static ArrayList<Municipios> lista_municipios_rest = new ArrayList<Municipios>();
    private static ArrayList<AtrativosTuristicos> lista_atrativos_rest = new ArrayList<AtrativosTuristicos>();


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

        helper = new HelperSQLMunicipios(getActivity());
        helperAtratativos = new HelperSQLAtrativos(getActivity());

        //helper.reset();
        //helperAtratativos.reset();

        GetJsonMunicipios download = new GetJsonMunicipios();
        download.execute();

        GetJsonAtrativos downloadAtrativos = new GetJsonAtrativos();
        downloadAtrativos.execute();


    }

    private class GetJsonAtrativos extends AsyncTask<Void, Void, ArrayList<AtrativosTuristicos>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<AtrativosTuristicos> doInBackground(Void... voids) {
            CriarConexaoAtrativoTuristico utilat = new CriarConexaoAtrativoTuristico();

            try {
                lista_atrativos_rest = utilat.getInformacao("https://apps4society.herokuapp.com/rest_atrativosTuristicos");
            } catch (ErrosDeConnexao errosDeConnexao) {
                errosDeConnexao.printStackTrace();
            }
            helperAtratativos.recoverDataSQLAtrativos();
            try {
                salvarDadosRest_atrativos();
            } catch (IOException e) {
                e.printStackTrace();
            }
            helperAtratativos.limparArray();
            return lista_atrativos_rest;

        }

        @Override
        protected void onPostExecute(ArrayList<AtrativosTuristicos> atrativosTuristicos) {
            super.onPostExecute(atrativosTuristicos);
        }
    }


    public class GetJsonMunicipios extends AsyncTask<Void, Void, ArrayList<Municipios>> {
        @Override
        protected ArrayList<Municipios> doInBackground(Void... voids) {
            CriarConexaoMunicipios util = new CriarConexaoMunicipios();


            try {
                lista_municipios_rest = util.getInformacao("https://apps4society.herokuapp.com/rest_municipios");
            } catch (ErrosDeConnexao errosDeConnexao) {
                errosDeConnexao.printStackTrace();
            }
            helper.recoverDataSQL();
            try {
                salvarDadosRest_city();
            } catch (IOException e) {
                e.printStackTrace();
            }
            helper.limparArray();
            return lista_municipios_rest;
        }

        @Override
        protected void onPostExecute(ArrayList<Municipios> municipios) {
            super.onPostExecute(municipios);
        }
    }


    public void salvarDadosRest_atrativos() throws IOException {
        if (lista_atrativos_rest != null) {
            for (int i = 0; i < lista_atrativos_rest.size(); i++) {
                helperAtratativos.inserirAtrativo(lista_atrativos_rest.get(i));
            }
        }
    }

    public void salvarDadosRest_city() throws IOException {
        if (lista_municipios_rest != null) {
            for (int i = 0; i < lista_municipios_rest.size(); i++) {
                Log.v("LOOL", "lool" + lista_municipios_rest.size());
                helper.inserir(lista_municipios_rest.get(i));
            }
        } else {
            Log.d("Lista Municipios", "Lista municipios Vazia REST");
        }
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

    public int changeMAP(int type) {
        tipo_mapa = type;
        Log.v("TIPOMAPA", "TIPOMAPA" + type);
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
    public MapGoogleActivity(Context ctx, ArrayList<Coordenadas> list) {
        this.ctx = ctx;
        this.list_buscaSql = list;
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
                    localizacaoAtual = new LatLng(location.getLatitude(), location.getLongitude());
                    //Toast.makeText(getActivity(), ""+tipo_mapa, Toast.LENGTH_SHORT).show();
                    updateMap(map, localizacaoAtual, tipo_mapa);
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
    public void updateMap(GoogleMap map, LatLng localizacaoAtual, int type) {
        // mMap = mapFragment.getMap(); // metodo depreciado api google 8.1.0 \ ATUAL 12.1.0
        // mMap.getUiSettings().setMapToolbarEnabled(true);

        /*
                \/ >> objeto Mapa do tipo hibrido:

                Dados fotográficos de satélite com mapas de vias adicionados.
                Os rótulos de vias e recursos também são visíveis.
                 //Metódo setMapType
         */
        if (type == 1) {
            map.setMyLocationEnabled(true);
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacaoAtual, 13));
            map.addMarker(new MarkerOptions().title("Localização Atual").position(localizacaoAtual));
        } else if (type == 2) {
            map.setMyLocationEnabled(true);
            map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacaoAtual, 13));
            map.addMarker(new MarkerOptions().title("Localização Atual").position(localizacaoAtual));
        } else if (type == 3) {
            map.setMyLocationEnabled(true);
            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacaoAtual, 13));
            map.addMarker(new MarkerOptions().title("Localização Atual").position(localizacaoAtual));
        } else if (type == 4) {
            map.setMyLocationEnabled(true);
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacaoAtual, 13));
            map.addMarker(new MarkerOptions().title("Localização Atual").position(localizacaoAtual));
        }


    }

    public Fragment startActivite() {
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

    public void askPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ) {//Can add more as per requirement

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        } else if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                ) {
            boolean completed = true;
        }

    }

    public void iniciarFirebaseDatabase() {
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = firebaseDatabase.getInstance();
        //fireBaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();

    }

    public void marcarPontosBusca(final GoogleMap map) {

        if (list_buscaSql.size() != 0) {
            for (int i = 0; i < list_buscaSql.size(); i++) {
                double latitude = list_buscaSql.get(i).getLatitude();
                double longitude = list_buscaSql.get(i).getLongitude();
                //Toast.makeText(getActivity(), "APSPASKAPSKPASK"+latitude, Toast.LENGTH_SHORT).show();

                LatLng lat = new LatLng(latitude, longitude);
                map.addMarker(new MarkerOptions().position(lat).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
        } else {
            Toast.makeText(getActivity(), "Pesquisas Armazenadas: " + list_buscaSql.size(), Toast.LENGTH_SHORT).show();
        }


    }

    public void actived() {
        Log.d("ACT", "act");
    }

    public void eventListener(final GoogleMap map) {
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {


            }
        });
    }


    public void recuperarDadosCidades(final GoogleMap map) {
        helper.limparArray();
        helper.recoverDataSQL();
        if (helper.getReturnList().size() != 0) {
            for (int i = 0; i < helper.getReturnList().size(); i++) {

                double latitude = helper.getReturnList().get(i).getLatitude();
                double longitude = helper.getReturnList().get(i).getLongitude();
                LatLng locate = new LatLng(latitude, longitude);
                map.addMarker(new MarkerOptions().title(helper.getReturnList().get(i).getNome()).snippet(helper.getReturnList().get(i).getAreaTerritorial()).position(locate).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            }

            helper.limparArray();
            MainActivity main = new MainActivity();
            //Toast.makeText(getActivity(), "LEN:>" + helper.getReturnList().size(), Toast.LENGTH_SHORT).show();

            if (localizacaoAtual != null) {
                main.recebeLocalizacao(localizacaoAtual);
            } else {
                Log.d("LOCATION MAP", "FAIL NULL LOCATION MAPGOOGLEACT");

            }
        }

    }

    public void recuperarDadosPontos(final GoogleMap map) {
        helperAtratativos.limparArray();
        helperAtratativos.recoverDataSQLAtrativos();
        if (helperAtratativos.getReturnList().size() != 0) {

            for (int i = 0; i < helperAtratativos.getReturnList().size(); i++) {

                double latitude = helperAtratativos.getReturnList().get(i).getLatitude();
                double longitude = helperAtratativos.getReturnList().get(i).getLongitude();
                LatLng locate = new LatLng(latitude, longitude);
                map.addMarker(new MarkerOptions().title(helperAtratativos.getReturnList().get(i).getNome()).snippet(helperAtratativos.getReturnList().get(i).getDescricao()).position(locate).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            }
            helperAtratativos.limparArray();
            MainActivity m = new MainActivity();

        }


    }
}


