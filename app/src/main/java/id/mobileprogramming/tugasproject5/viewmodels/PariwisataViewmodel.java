package id.mobileprogramming.tugasproject5.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import id.mobileprogramming.tugasproject5.models.PariwisataStatus;
import id.mobileprogramming.tugasproject5.repository.PariwisataRepository;

public class PariwisataViewmodel extends ViewModel {
    public void init(){
        PariwisataRepository.getInstance().init();
    }

    public LiveData<PariwisataStatus> getData(){
        return PariwisataRepository.getInstance().getData();
    }
}
