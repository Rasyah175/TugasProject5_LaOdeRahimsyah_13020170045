package id.mobileprogramming.tugasproject5.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import id.mobileprogramming.tugasproject5.models.Account;
import id.mobileprogramming.tugasproject5.models.Status;
import id.mobileprogramming.tugasproject5.networking.RetrofitApi;
import id.mobileprogramming.tugasproject5.networking.RetrofitClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateRepository {
    private static UpdateRepository instance;
    private MutableLiveData<Status> data;

    public static UpdateRepository getInstance(){
        if (instance == null){
            instance = new UpdateRepository();
        }

        return instance;
    }

    public MutableLiveData<Status> getData(Account account){
        data = new MutableLiveData<>();
        RetrofitApi api = RetrofitClass.getInstance().getRetrofit().create(RetrofitApi.class);
        Call<Status> call = api.getUpdate(account.getUsername(), account.getPassword(), account.getNama_lengkap(), account.getAlamat(), account.getKontak());
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                data.setValue(new Status(false, t.getMessage()));
                Log.e("Update", t.getMessage());
            }
        });

        return data;
    }
}
