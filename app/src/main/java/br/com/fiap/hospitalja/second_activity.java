package br.com.fiap.hospitalja;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import br.com.fiap.hospitalja.Adapter.AdapterListHospitais;
import br.com.fiap.hospitalja.Model.Especialidade;
import br.com.fiap.hospitalja.Model.Hospital;
import br.com.fiap.hospitalja.Retrofit.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class second_activity extends AppCompatActivity {

    private ListView listContentView;
    private List<Hospital> hospitalList = new ArrayList<>();
    private AdapterListHospitais adapterListHospitais;
    private String SelectEspec;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        Intent intent = getIntent();

        if(intent != null){
            Bundle params = intent.getExtras();
            if (params != null){
                SelectEspec = params.getString("SelectEspec");
            }
        }

        //TODO AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA


        Call<Hospital> call = new RetrofitConfig().getHospitalService().buscarHospital(SelectEspec);
        call.enqueue(new Callback<Hospital>() {
            @Override
            public void onResponse(Call<Hospital> call, Response<Hospital> response) {
                Hospital hospital = response.body();
                hospitalList.add(hospital);
            }

            @Override
            public void onFailure(Call<Hospital> call, Throwable t) {
                Log.e("HospitalService   ", "Erro ao buscar o hospital:" + t.getMessage());
            }
        });


        /*
        //TODO criando uma lista, para teste do adapter, aqui será os dados vindos da API...


        Especialidade especialidade = new Especialidade();
        Hospital hospital =  new Hospital();

        especialidade.setCodigo(12);
        especialidade.setNome("cardio");
        especialidade.setDescricao("dawdawd");

        List<Especialidade> especialidades = new ArrayList<>();
        especialidades.add(especialidade);


        hospital.setCodigo(1337);
        hospital.setNome("Geraldinho");
        hospital.setEndereco("Rua dos testes");
        hospital.setEspecialidades(especialidades);
        hospital.setFila("2");
        hospital.setTempoEspera("20");
        hospital.setLatitude("0");
        hospital.setLongitude("0");
        */

        this.listContentView = (ListView) findViewById(R.id.listContentView);

        this.adapterListHospitais = new AdapterListHospitais( second_activity.this, this.hospitalList);

        this.listContentView.setAdapter(this.adapterListHospitais);

        }

}
