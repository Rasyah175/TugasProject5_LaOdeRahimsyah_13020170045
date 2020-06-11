package id.mobileprogramming.tugasproject5.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import id.mobileprogramming.tugasproject5.models.Pariwisata;
import id.mobileprogramming.tugasproject5.models.PariwisataStatus;
import id.mobileprogramming.tugasproject5.networking.RetrofitApi;
import id.mobileprogramming.tugasproject5.networking.RetrofitClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PariwisataRepository {
    private MutableLiveData<PariwisataStatus> data;
    private static PariwisataRepository instance;

    public static PariwisataRepository getInstance(){
        if (instance == null) {
            instance = new PariwisataRepository();
        }

        return instance;
    }

    public void init(){
        data = new MutableLiveData<>();
        RetrofitApi api = RetrofitClass.getInstance().getRetrofit().create(RetrofitApi.class);
        Call<PariwisataStatus> call = api.getPariwisata();

        call.enqueue(new Callback<PariwisataStatus>() {
            @Override
            public void onResponse(Call<PariwisataStatus> call, Response<PariwisataStatus> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<PariwisataStatus> call, Throwable t) {
                Log.e("Pariwisata", t.getMessage());
            }
        });
    }

    public MutableLiveData<PariwisataStatus> getData(){
        return data;
    }
}
