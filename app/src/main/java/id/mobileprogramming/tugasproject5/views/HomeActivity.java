package id.mobileprogramming.tugasproject5.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.androidbuffer.kotlinfilepicker.KotConstants;
import com.androidbuffer.kotlinfilepicker.KotResult;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import id.mobileprogramming.tugasproject5.R;
import id.mobileprogramming.tugasproject5.models.Status;
import id.mobileprogramming.tugasproject5.views.fragments.BookmarkFragment;
import id.mobileprogramming.tugasproject5.views.fragments.HomeFragment;
import id.mobileprogramming.tugasproject5.views.fragments.ProfileFragment;
import id.mobileprogramming.tugasproject5.views.sharedpreferences.LoginPreferences;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomMenu;
    private ViewPager2 viewPager;
    private SharedPreferences preferences;
    private SweetAlertDialog pDialog, positive, negative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        preferences = LoginPreferences.getInstance().getShared(this);

        bottomMenu = findViewById(R.id.bottom_menu);
        viewPager = findViewById(R.id.viewPager_home);

        viewPager.setAdapter(new ScreenSlidePagerAdapter(this));
        viewPager.setUserInputEnabled(false);

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);

        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.bookmark:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.profile:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new HomeFragment();
                case 1:
                    return new BookmarkFragment();
                case 2:
                    return new ProfileFragment();
                default:
                    return new HomeFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (110 == requestCode && resultCode == Activity.RESULT_OK) {
            ArrayList<KotResult> path = data.getParcelableArrayListExtra(KotConstants.EXTRA_FILE_RESULTS);

            AndroidNetworking.upload("https://api.sharekom.my.id/travel/upload.php")
                    .addMultipartFile("gambar", new File(path.get(0).getLocation()))
                    .addMultipartParameter("username",LoginPreferences.getInstance().getShared(HomeActivity.this).getString("username", null))
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .setUploadProgressListener(new UploadProgressListener() {
                        @Override
                        public void onProgress(long bytesUploaded, long totalBytes) {
                            pDialog.show();
                        }
                    })
                    .getAsObject(Status.class, new ParsedRequestListener<Status>() {
                        @Override
                        public void onResponse(Status response) {
                            if (response.isStatus()){
                                pDialog.dismiss();
                                positive = new SweetAlertDialog(HomeActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                LoginPreferences.getInstance().init(HomeActivity.this);
                                                LoginPreferences.getInstance().logOut();
                                                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                                                finish();
                                            }
                                        })
                                        .setTitleText("Berhasil")
                                        .setContentText(response.getMessage());
                                positive.setCancelable(false);
                                positive.show();
                            }else{
                                pDialog.dismiss();
                                negative = new SweetAlertDialog(HomeActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Gagal")
                                        .setContentText(response.getMessage());

                                negative.setCancelable(false);
                                negative.show();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            pDialog.dismiss();
                            negative = new SweetAlertDialog(HomeActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Gagal")
                                    .setContentText("Kesalahan koneksi atau filepath tidak support");

                            negative.show();
                        }
                    });
        }
    }

    @Override
    protected void onResume() {
        if (!preferences.getBoolean("status", false)){
            finish();
        }
        super.onResume();
    }
}