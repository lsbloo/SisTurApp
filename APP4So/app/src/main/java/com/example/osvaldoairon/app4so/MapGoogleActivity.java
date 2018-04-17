package com.example.osvaldoairon.app4so;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapGoogleActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {

    public static final int REQUEST_ERROR_PLAY_ = 1;

    private static GoogleMap mMap;
    private static GoogleApiClient mGoogleApiCliente;
    private static LatLng mOrigem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_google);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mMap = mapFragment.getMap(); // metodo depreciado api google 8.1.0 \ ATUAL 12.1.0
        mMap.getUiSettings().setMapToolbarEnabled(true);

        /*
                \/ >> objeto Mapa do tipo hibrido:

                Dados fotográficos de satélite com mapas de vias adicionados.
                Os rótulos de vias e recursos também são visíveis.
                 //Metódo setMapType
         */
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        mMap.setMyLocationEnabled(true);
        mGoogleApiCliente = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();


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
        // Metodo que pega a ultima localização conectada no smrt
        lastLocation();

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
    protected void onStart() {
        mGoogleApiCliente.connect();
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
        }
    }


    public void showDetails(Context ctx, int resultado) {
        final String TAG = "DIALOG_ERRO_PLAY_SERVICES";
        if(getSupportFragmentManager().findFragmentByTag(TAG) == null){
            Log.d("ERROR FRAGMENT" , "ERROR FRAGMENT METODO SHOWDETAILS");
        }

    }

    public void lastLocation() {
        Location locate = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiCliente);
        if(locate != null){
            mOrigem = new LatLng(locate.getAltitude(),locate.getLongitude());
            updateMap();
        }
    }
    public void updateMap(){
        /*
        ATUALIZA O MAPA ;
         */
        //!
        mMap.animateCamera(CameraUpdateFactory.newLatLng(mOrigem));
        mMap.clear();


        mMap.addMarker(new MarkerOptions().position(mOrigem).title("Seu local Atual"));


    }
}
