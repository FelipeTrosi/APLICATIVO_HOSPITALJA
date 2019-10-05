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
    private List<Hospital> hospitalList ;
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
            //ADICIONANDO A AÇÃO DE CLICK EM UM ITEM DA LISTA (ARRUMAR O ENVIO PARA RECEBER O HOSPITAL CERTO)
            listContentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                    Bundle params = new Bundle();
                    Toast.makeText(SecondActivity.this, "Posição: " + position, Toast.LENGTH_SHORT).show();
                    params.putParcelableArrayList("Lista", (ArrayList<? extends Parcelable>) hospitalList);
                    params.putInt("Position",position);
                    intent.putExtras(params);
                    startActivity(intent);
                }
            });
    }

  }


