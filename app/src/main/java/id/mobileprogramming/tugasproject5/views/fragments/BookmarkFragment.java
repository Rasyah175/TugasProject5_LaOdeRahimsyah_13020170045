package id.mobileprogramming.tugasproject5.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import id.mobileprogramming.tugasproject5.R;
import id.mobileprogramming.tugasproject5.adapters.BookmarkAdapter;
import id.mobileprogramming.tugasproject5.views.HomeActivity;
import id.mobileprogramming.tugasproject5.views.sharedpreferences.LoginPreferences;

public class BookmarkFragment extends Fragment {
    private RecyclerView recyclerView;
    private BookmarkAdapter adapter;
    private List<String> list;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_baseline_sort_by_alpha_24));

        recyclerView = view.findViewById(R.id.bookmark_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookmarkAdapter();

        list = new ArrayList<>();
        list.add("Makassar");
        list.add("Bone");
        list.add("Maros");
        list.add("Selayar");
        list.add("Tana Toraja");
        list.add("Gowa");
        list.add("Bulukumba");
        list.add("Luwu Timur");
        list.add("Pangkep");
        list.add("Barru");
        Collections.sort(list);

        adapter.init(list, getContext());

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.a_z:
                        Collections.sort(list);
                        TransitionManager.beginDelayedTransition(recyclerView);
                        recyclerView.setAdapter(adapter);
                        break;
                    case R.id.z_a:
                        Collections.sort(list, Collections.reverseOrder());
                        TransitionManager.beginDelayedTransition(recyclerView);
                        recyclerView.setAdapter(adapter);
                        break;
                    case R.id.random:
                        Collections.shuffle(list);
                        TransitionManager.beginDelayedTransition(recyclerView);
                        recyclerView.setAdapter(adapter);
                        break;
                }
                return false;
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        recyclerView.setAdapter(adapter);
        super.onResume();
    }
}