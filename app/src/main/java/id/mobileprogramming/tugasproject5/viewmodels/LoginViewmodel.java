package id.mobileprogramming.tugasproject5.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import id.mobileprogramming.tugasproject5.models.LoginStatus;
import id.mobileprogramming.tugasproject5.repository.LoginRepository;

public class LoginViewmodel extends ViewModel {
    public void init(String username, String password){
        LoginRepository.getInstance().init(username, password);
    }

    public LiveData<LoginStatus> getData(){
        return LoginRepository.getInstance().getData();
    }
}
