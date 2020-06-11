package id.mobileprogramming.tugasproject5.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import id.mobileprogramming.tugasproject5.models.Account;
import id.mobileprogramming.tugasproject5.models.Status;
import id.mobileprogramming.tugasproject5.repository.UpdateRepository;

public class UpdateViewmodel extends ViewModel {
    private MutableLiveData<Status> data;

    public void init(Account account){
        data = UpdateRepository.getInstance().getData(account);
    }

    public LiveData<Status> getData(){
        return data;
    }
}
