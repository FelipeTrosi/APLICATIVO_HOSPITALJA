package br.com.fiap.hospitalja.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class RetrofitConfig {
    private final Retrofit retrofit;

    public RetrofitConfig(){
        retrofit =  new Retrofit.Builder().baseUrl("http://hospitalja.herokuapp.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public HospitalService getHospitalService(){
        return retrofit.create(HospitalService.class);
    }
}
