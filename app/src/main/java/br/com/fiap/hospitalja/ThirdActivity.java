package br.com.fiap.hospitalja;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.hospitalja.Model.Especialidade;
import br.com.fiap.hospitalja.Model.Hospital;

public class ThirdActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView nomeTextView;
    private TextView enderecoTextView;
    private TextView filaTextView;
    private TextView tempoTextView;
    private TextView especialidadeTextView;

    private Hospital hospital;
    private double latitude;
    private double longitude;

    //Google Maps
    private MapView mMapView;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private GeoApiContext mGeoApiContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_acitivity);
        Intent intent = getIntent();

        initGoogleMap(savedInstanceState);

        this.nomeTextView = (TextView) findViewById(R.id.nomeTextView);
        this.enderecoTextView = (TextView) findViewById(R.id.enderecoTextView);
        this.filaTextView = (TextView) findViewById(R.id.filaTextView);
        this.tempoTextView = (TextView) findViewById(R.id.tempoTextView);
        this.especialidadeTextView = (TextView) findViewById(R.id.especialidadeTextView);


        if(intent != null){
            Bundle params = intent.getExtras();
            if (params != null){
                hospital = params.getParcelable("Hospital");
                latitude = params.getDouble("Latitude");
                longitude = params.getDouble("Logitude");
            }
        }




            nomeTextView.setText(hospital.getNome());
            enderecoTextView.setText(hospital.getEndereco());
            filaTextView.setText(hospital.getFila());
            tempoTextView.setText(hospital.getTempoEspera());
            for(Especialidade e: hospital.getEspecialidades()){

                especialidadeTextView.setText(especialidadeTextView.getText()+ " "+e.getNome());
            }


    }
    private void calculateDirections(Marker marker){
        Log.d("Teste", "calculateDirections: calculating directions.");

        com.google.maps.model.LatLng destination = new com.google.maps.model.LatLng(
                marker.getPosition().latitude,
                marker.getPosition().longitude
        );
        DirectionsApiRequest directions = new DirectionsApiRequest(mGeoApiContext);

        directions.alternatives(false);
        directions.origin(
                new com.google.maps.model.LatLng(
                        this.latitude,
                        this.longitude
                )
        );
        Log.d("Teste", "calculateDirections: destination: " + destination.toString());
        directions.destination(destination).setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
                Log.d("Teste", "onResult: routes: " + result.routes[0].toString());
                Log.d("Teste", "onResult: geocodedWayPoints: " + result.geocodedWaypoints[0].toString());
                Log.d("Teste", "onResult: geocodedWayPoints: " + result.routes[0].legs[0].distance);
            }

            @Override
            public void onFailure(Throwable e) {
                Log.e("Teste", "onFailure: " + e.getMessage() );

            }
        });
    }

    private void initGoogleMap(Bundle savedInstanceState){
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);


        if(mGeoApiContext == null){
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey(getString(R.string.google_maps_key))
                    .build();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng latLng = new LatLng(latitude,longitude);
        LatLng latLng1 = new LatLng(Double.parseDouble(
                hospital.getLatitude()),
                Double.parseDouble(hospital.getLongitude()));
        MarkerOptions options = new MarkerOptions().position(latLng1);
        Marker marker = map.addMarker(options);


        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1,11),4000, null );
        map.setMyLocationEnabled(true);
        calculateDirections(marker);



    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
