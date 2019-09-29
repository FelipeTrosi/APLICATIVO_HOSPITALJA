package br.com.fiap.hospitalja.Retrofit;

import br.com.fiap.hospitalja.Model.Hospital;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HospitalService {

    @GET("/hospital/{codigo}")
    Call<Hospital> buscarHospital(@Path("codigo") String codigo);


}
