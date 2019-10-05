package br.com.fiap.hospitalja;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {

    //Localização
    FusedLocationProviderClient client;

    //Interface
    private Button buttonSend;
    private Spinner SelectEspec;


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
                intent.putExtras(params);

                startActivity(intent);
            }
        });

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

        //Pegando a ultima localicão do user
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Log.i("Teste", location.getLatitude() + " " +location.getLongitude());
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

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
                Log.i("teste", locationAvailability.isLocationAvailable() + " ");
            }

        };
        client.requestLocationUpdates(locationRequest, locationCallback, null);
    }
}
