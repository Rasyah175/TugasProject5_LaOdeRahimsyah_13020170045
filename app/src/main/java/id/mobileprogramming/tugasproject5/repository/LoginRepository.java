package id.mobileprogramming.tugasproject5.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import id.mobileprogramming.tugasproject5.models.Account;
import id.mobileprogramming.tugasproject5.models.LoginStatus;
import id.mobileprogramming.tugasproject5.networking.RetrofitApi;
import id.mobileprogramming.tugasproject5.networking.RetrofitClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private static LoginRepository instance;
    private MutableLiveData<LoginStatus> loginData;

    public static LoginRepository getInstance(){
        if (instance == null){
            instance = new LoginRepository();
        }
        return instance;
    }

    public void init(String username, String password){
        loginData = new MutableLiveData<>();
        RetrofitApi api = RetrofitClass.getInstance().getRetrofit().create(RetrofitApi.class);
        Call<LoginStatus> call = api.getLogin(username, password);
        call.enqueue(new Callback<LoginStatus>() {
            @Override
            public void onResponse(Call<LoginStatus> call, Response<LoginStatus> response) {
                loginData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<LoginStatus> call, Throwable t) {
                loginData.setValue(new LoginStatus(false, new Account(null, null, null, null, null, null)));
                Log.e("Login", t.getMessage());
            }
        });
    }

    public MutableLiveData<LoginStatus> getData(){
        return loginData;
    }
}
