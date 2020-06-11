package id.mobileprogramming.tugasproject5.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClass {
    private static RetrofitClass instance;

    public static RetrofitClass getInstance(){
        if (instance == null){
            instance = new RetrofitClass();
        }

        return instance;
    }

    public Retrofit getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.sharekom.my.id/travel/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
