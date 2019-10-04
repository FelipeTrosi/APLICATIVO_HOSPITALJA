package br.com.fiap.hospitalja.Retrofit;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.hospitalja.Model.Hospital;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HospitalService {

    @GET("hospital/listar/{nome}")
    Call<List<Hospital>> buscarHospital(@Path("nome") String nome);


}
