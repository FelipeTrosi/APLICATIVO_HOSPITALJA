package br.com.fiap.hospitalja;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    private FloatingActionButton botaoVoltar;

    private String distancia;
    private double myLatitude;
    private double myLongitude;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        Intent intent = getIntent();
        listContentView = (ListView) findViewById(R.id.listContentView);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.title_toolbar);

        this.botaoVoltar = (FloatingActionButton) findViewById(R.id.botaoVoltar);

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SecondActivity.this, MainActivity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(it);
            }
        });


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


