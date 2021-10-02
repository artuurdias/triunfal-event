package br.unicamp.apptriunfalevent.APIconfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig   {

    private static final String BASE_URL = "http://127.0.0.1:5000/api";
    //private static final String BASE_URL = "http://192.168.0.16:3000/api/dog/get/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

