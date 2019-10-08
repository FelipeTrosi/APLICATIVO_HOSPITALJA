package br.com.fiap.hospitalja;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import br.com.fiap.hospitalja.Permissions.AndroidPermissions;

public class MainActivity extends AppCompatActivity {

    //Permissões
    //Permission
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

     //Localização
    FusedLocationProviderClient client;

    //Interface
    private Button buttonSend;
    private Spinner SelectEspec;
    private double longitude;
    private double latitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Location
        client = LocationServices.getFusedLocationProviderClient(this);

        //Interface
        this.buttonSend = (Button) findViewById(R.id.buttonSend);
        this.SelectEspec = (Spinner) findViewById(R.id.SelectEspec);

        //Adapter
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.especialidades_selecionada, android.R.layout.simple_spinner_item);
        SelectEspec.setAdapter(adapter);



        //Envio de especialidade para a SecondActivity
        this.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                Bundle params = new Bundle();
                params.putString("SelectEspec", SelectEspec.getSelectedItem().toString());
                params.putDouble("Latitude", latitude);
                params.putDouble("Longitude", longitude);
                intent.putExtras(params);

                startActivity(intent);
            }
        });

        checkLocationPermission();
    }
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {



            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();

            } else  {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                Log.i("Teste", location.getLatitude() + " " +location.getLongitude());
                                longitude = location.getLongitude();
                                latitude = location.getLatitude();

                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.i("Teste", "Erro ao pegar a localização");
                                    }
                                });

                    }

                }
                else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    finish();
                }
                return;
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        //Verificação do Google play Services
        int codErro = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        switch (codErro) {
            case ConnectionResult
                    .SERVICE_MISSING:
            case ConnectionResult
                    .SERVICE_VERSION_UPDATE_REQUIRED:
            case ConnectionResult
                    .SERVICE_DISABLED:
                GoogleApiAvailability.getInstance().getErrorDialog(this, codErro, 0, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                }).show();
                break;
            case ConnectionResult.SUCCESS:
                Log.d("teste", "Google Play Services up-to-date");
                break;
        }

        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Log.i("Teste", location.getLatitude() + " " +location.getLongitude());
                longitude = location.getLongitude();
                latitude = location.getLatitude();

            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Teste", "Erro ao pegar a localização");
            }
        });

        //Definindo os intevalos para requisitar a localização
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(15 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);


        //Checagem de localizao por Internet(GPS, Dados Móveis)
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(builder.build()).addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Log.i("Teste",  " "+locationSettingsResponse.getLocationSettingsStates().isNetworkLocationPresent());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException){
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(MainActivity.this, 10);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }

                }
            }
        });

        //Recuperando localizações pelos intervalos de tempo
        LocationCallback locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if(locationResult == null){
                    Log.i("Teste2", "Não encontrou um local");
                    return;
                }
                for(Location location : locationResult.getLocations()){
                    Log.i("Teste2", location.getLatitude() + " ");
                }

            }

            //Retorno bool se há uma localização disponivel
            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
                Log.i("teste", locationAvailability.isLocationAvailable() + " ");
            }

        };

    }



}
