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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapGoogleActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final int REQUEST_ERROR_PLAY_ = 1;
    public static final int REQUEST_CHECAR_GPS = 2;

    private static GoogleMap mMap;
    private static GoogleApiClient mGoogleApiCliente;
    private static LatLng mOrigem;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Handler handler;
    private static boolean dialog;
    private int tentativas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_google);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        //mapFragment.getMapAsync((OnMapReadyCallback) this); PRECISA DO MAPREADY

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

        handler = new Handler();
        dialog = savedInstanceState == null;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        askPermissions();
        conectAPICLIENTE();


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
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(this, "XDXDXD", Toast.LENGTH_SHORT).show();
        lastLocation();
        // Metodo que pega a ultima localização conectada no smrt

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
                lastLocation();
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

    public int lastLocation() {
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
            Toast.makeText(this, "ASKPakpsAPKSApskapsk", Toast.LENGTH_SHORT).show();
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        tentativas = 0;
                        mOrigem = new LatLng(location.getAltitude(), location.getLongitude());
                        Log.d("LATITUDE", "ALTITUDE" + location.getLongitude());
                        Log.d("LONGITUDE", "LONGITUDE" + location.getLatitude());
                        updateMap();
                    } else if (tentativas < 10) {
                        tentativas++;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lastLocation();
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

    public void updateMap() {
        /*
        ATUALIZA O MAPA ;
         */
        //mMap.animateCamera(CameraUpdateFactory.newLatLng(mOrigem));
        //mMap.clear();

        /*
        adiciona marcadores na posicao especificada
         */
        //mMap.addMarker(new MarkerOptions().position(mOrigem).title("Seu local Atual"));


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
                        lastLocation();
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

}
