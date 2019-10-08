package br.com.fiap.hospitalja;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;

import java.util.List;
import br.com.fiap.hospitalja.Adapter.AdapterListHospitais;
import br.com.fiap.hospitalja.Model.Hospital;
import br.com.fiap.hospitalja.Retrofit.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondActivity extends AppCompatActivity {

    private ListView listContentView;
    private List<Hospital> hospitalList ;
    private AdapterListHospitais adapterListHospitais;
    private String SelectEspec;
    private GeoApiContext mGeoApiContext;
    private String distancia;
    private double myLatitude;
    private double myLongitude;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        Intent intent = getIntent();
        listContentView = (ListView) findViewById(R.id.listContentView);

        if(mGeoApiContext == null){
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey(getString(R.string.google_maps_key))
                    .build();
        }

        if(intent != null){
            Bundle params = intent.getExtras();
            if (params != null){
                SelectEspec = params.getString("SelectEspec");
                myLongitude = params.getDouble("Longitude");
                myLatitude = params.getDouble("Latitude");
            }
        }

        Call<List<Hospital>> call = new RetrofitConfig().getHospitalService().buscarHospital(SelectEspec);
        call.enqueue(new Callback<List<Hospital>>() {
            @Override
            public void onResponse(Call<List<Hospital>> call, Response<List<Hospital>> response) {
                hospitalList =  response.body();
                adapterListHospitais = new AdapterListHospitais( SecondActivity.this, hospitalList);
                listContentView.setAdapter(adapterListHospitais);
            }

            @Override
            public void onFailure(Call<List<Hospital>> call, Throwable t) {
                Log.e("HospitalService   ", "Erro ao buscar o hospital:" + t.getMessage());
            }
        });

            listContentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                    Bundle params = new Bundle();

                    Hospital hospital = hospitalList.get(position);
                    params.putParcelable("Hospital",hospital);
                    intent.putExtras(params);
                    startActivity(intent);
                }
            });
    }
    /**/


  }


