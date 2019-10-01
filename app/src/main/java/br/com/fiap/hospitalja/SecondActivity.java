package br.com.fiap.hospitalja;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import br.com.fiap.hospitalja.Adapter.AdapterListHospitais;
import br.com.fiap.hospitalja.Model.Hospital;
import br.com.fiap.hospitalja.Retrofit.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SecondActivity extends AppCompatActivity {

    private ListView listContentView;
    private List<Hospital> hospitalList = new ArrayList<>();
    private AdapterListHospitais adapterListHospitais;
    private String SelectEspec;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        Intent intent = getIntent();
        listContentView = (ListView) findViewById(R.id.listContentView);


        if(intent != null){
            Bundle params = intent.getExtras();
            if (params != null){
                SelectEspec = params.getString("SelectEspec");
            }
        }

        Call<Hospital> call = new RetrofitConfig().getHospitalService().buscarHospital(SelectEspec);
        call.enqueue(new Callback<Hospital>() {
            @Override
            public void onResponse(Call<Hospital> call, Response<Hospital> response) {
                Hospital hospital = response.body();
                hospitalList.add(hospital);
                adapterListHospitais = new AdapterListHospitais( SecondActivity.this, hospitalList);
                listContentView.setAdapter(adapterListHospitais);
            }

            @Override
            public void onFailure(Call<Hospital> call, Throwable t) {
                Log.e("HospitalService   ", "Erro ao buscar o hospital:" + t.getMessage());
                 }
            });



            //TODO ADICIONANDO A AÇÃO DE CLICK EM UM ITEM DA LISTA
            listContentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // ListView Clicked item index
                    int itemPosition = position;

                    Intent intent = new Intent(SecondActivity.this, ThirdAcitivity.class);
                    Bundle params = new Bundle();
                    params.putParcelableArrayList("Lista", (ArrayList<? extends Parcelable>) hospitalList);
                    intent.putExtras(params);
                    startActivity(intent);
                }
            });


    }

  }
