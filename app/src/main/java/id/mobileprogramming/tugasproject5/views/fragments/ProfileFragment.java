package id.mobileprogramming.tugasproject5.views.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidbuffer.kotlinfilepicker.KotRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import de.hdodenhof.circleimageview.CircleImageView;
import id.mobileprogramming.tugasproject5.R;
import id.mobileprogramming.tugasproject5.models.LoginStatus;
import id.mobileprogramming.tugasproject5.viewmodels.LoginViewmodel;
import id.mobileprogramming.tugasproject5.views.EditdataActivity;
import id.mobileprogramming.tugasproject5.views.LoginActivity;
import id.mobileprogramming.tugasproject5.views.sharedpreferences.LoginPreferences;

public class ProfileFragment extends Fragment {
    private Toolbar toolbar;
    private TextView namalengkap_title, alamat_title, username, namalengkap, kontak, alamat, load_message;
    private LoginViewmodel viewmodel;
    private CircleImageView imageView;
    private SharedPreferences preferences;
    private ProgressBar progressBar, detail_progress;
    private LinearLayout detail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        namalengkap_title = view.findViewById(R.id.namalengkap_title);
        alamat_title = view.findViewById(R.id.alamat_title);
        username = view.findViewById(R.id.username);
        namalengkap = view.findViewById(R.id.namalengkap);
        kontak = view.findViewById(R.id.kontak);
        alamat = view.findViewById(R.id.alamat);
        imageView = view.findViewById(R.id.profile_image);
        progressBar = view.findViewById(R.id.progress);
        detail_progress = view.findViewById(R.id.detail_progress);
        detail = view.findViewById(R.id.detail);
        load_message = view.findViewById(R.id.load_message);

        Bundle bundle = this.getArguments();
        if (bundle != null){
            Toast.makeText(getContext(), bundle.getString("Lokasi"), Toast.LENGTH_SHORT).show();
        }

        preferences = LoginPreferences.getInstance().getShared(getActivity());

        if (!preferences.getBoolean("status", false)) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }

        viewmodel = new ViewModelProvider(getActivity()).get(LoginViewmodel.class);

        getData();

        toolbar = view.findViewById(R.id.profile_toolbar);
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_baseline_settings_24));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.change_picture:
                        new KotRequest.File(getActivity(), 110).isMultiple(false).pick();
                        break;
                    case R.id.edit_data:
                        startActivity(new Intent(getActivity(), EditdataActivity.class));
                        break;
                    case R.id.logout:
                        LoginPreferences.getInstance().init(getActivity());
                        LoginPreferences.getInstance().logOut();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                        break;
                    default:
                        Toast.makeText(getActivity(), "None", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        return view;
    }

    public void getData() {
        viewmodel.init(preferences.getString("username", ""), preferences.getString("password", ""));
        detail_progress.setVisibility(View.VISIBLE);
        load_message.setVisibility(View.INVISIBLE);
        detail.setVisibility(View.INVISIBLE);
        viewmodel.getData().observe(getActivity(), new Observer<LoginStatus>() {
            @Override
            public void onChanged(LoginStatus loginStatus) {
                if (loginStatus.isStatus()) {
                    namalengkap_title.setText(loginStatus.getMessage().getNama_lengkap());
                    alamat_title.setText(loginStatus.getMessage().getAlamat());
                    username.setText(loginStatus.getMessage().getUsername());
                    namalengkap.setText(loginStatus.getMessage().getNama_lengkap());
                    kontak.setText(loginStatus.getMessage().getKontak());
                    alamat.setText(loginStatus.getMessage().getAlamat());
                    Glide.with(getActivity().getApplicationContext())
                            .load("https://api.sharekom.my.id/travel/assets/" + loginStatus.getMessage().getPhoto()) //+loginStatus.getMessage().getPhoto())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .placeholder(android.R.color.white)
                            .error(R.drawable.profile)
                            .into(imageView);

                    detail.setVisibility(View.VISIBLE);
                    load_message.setVisibility(View.INVISIBLE);
                    detail_progress.setVisibility(View.INVISIBLE);
                } else {
                    load_message.setVisibility(View.VISIBLE);
                    detail_progress.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}