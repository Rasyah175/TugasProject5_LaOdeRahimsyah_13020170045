package id.mobileprogramming.tugasproject5.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import id.mobileprogramming.tugasproject5.R;
import id.mobileprogramming.tugasproject5.adapters.HomeAdapter;
import id.mobileprogramming.tugasproject5.models.PariwisataStatus;
import id.mobileprogramming.tugasproject5.viewmodels.PariwisataViewmodel;
import id.mobileprogramming.tugasproject5.views.sharedpreferences.LoginPreferences;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private HomeAdapter adapter;
    private LinearLayoutManager layoutManager;
    private PariwisataViewmodel viewmodel;
    private ProgressBar progressBar;
    private String location;
    private String location_prev;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        LoginPreferences.getInstance().init(getContext());
        progressBar = view.findViewById(R.id.home_progress);
        recyclerView = view.findViewById(R.id.home_recycler);

        showData("Makassar");

        return view;
    }

    protected void showData(final String location){
        recyclerView.setHasFixedSize(true);
        adapter = new HomeAdapter();
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.smoothScrollToPosition(7);

        viewmodel = new ViewModelProvider(getActivity()).get(PariwisataViewmodel.class);
        viewmodel.init();
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        viewmodel.getData().observe(getActivity(), new Observer<PariwisataStatus>() {
            @Override
            public void onChanged(PariwisataStatus pariwisataStatus) {
                if (pariwisataStatus.isStatus()){
                    adapter.init(location ,pariwisataStatus.getMessage(), getContext());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }else{
                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                    adapter.init(location, pariwisataStatus.getMessage(), getContext());
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public void onResume() {
        location = LoginPreferences.getInstance().getShared(getContext()).getString("Location", null);
        location_prev = LoginPreferences.getInstance().getShared(getContext()).getString("prevLocation", null);

        if (location != null){
            if (!location.equals(location_prev)){
                showData(location);
                Toast.makeText(getContext(), location, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "Resume", Toast.LENGTH_SHORT).show();
            }
        }

        super.onResume();
    }

    @Override
    public void onDestroy() {
        location = LoginPreferences.getInstance().getShared(getContext()).getString("Location", null);
        if (location != null) {
            LoginPreferences.getInstance().prevLocation(location);
            location_prev = LoginPreferences.getInstance().getShared(getContext()).getString("prevLocation", null);
            if (location_prev != null){
                Toast.makeText(getContext(), location_prev, Toast.LENGTH_SHORT).show();
            }
        }
        super.onDestroy();
    }

    @Override
    public void onPause() {
        location = LoginPreferences.getInstance().getShared(getContext()).getString("Location", null);
        LoginPreferences.getInstance().prevLocation(location);

        super.onPause();
    }
}