package id.mobileprogramming.tugasproject5.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import cn.pedant.SweetAlert.SweetAlertDialog;
import id.mobileprogramming.tugasproject5.R;
import id.mobileprogramming.tugasproject5.models.Account;
import id.mobileprogramming.tugasproject5.models.LoginStatus;
import id.mobileprogramming.tugasproject5.models.Status;
import id.mobileprogramming.tugasproject5.viewmodels.LoginViewmodel;
import id.mobileprogramming.tugasproject5.viewmodels.UpdateViewmodel;
import id.mobileprogramming.tugasproject5.views.sharedpreferences.LoginPreferences;

public class EditdataActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private LoginViewmodel viewmodel;
    private UpdateViewmodel updateViewmodel;
    private SharedPreferences preferences;
    private TextInputLayout username, password, nama_lengkap, kontak, alamat;
    private Button btn;
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdata);

        username = findViewById(R.id.edit_username);
        password = findViewById(R.id.edit_password);
        nama_lengkap = findViewById(R.id.edit_namalengkap);
        kontak = findViewById(R.id.edit_kontak);
        alamat = findViewById(R.id.edit_alamat);
        btn = findViewById(R.id.btn_edit);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = LoginPreferences.getInstance().getShared(this);

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);

        updateViewmodel = new ViewModelProvider(EditdataActivity.this).get(UpdateViewmodel.class);

        viewmodel = new ViewModelProvider(this).get(LoginViewmodel.class);
        viewmodel.init(preferences.getString("username", ""), preferences.getString("password", ""));
        pDialog.show();
        viewmodel.getData().observe(this, new Observer<LoginStatus>() {
            @Override
            public void onChanged(LoginStatus loginStatus) {
                if (loginStatus.isStatus()){
                    username.getEditText().setText(loginStatus.getMessage().getUsername());
                    password.getEditText().setText(loginStatus.getMessage().getPassword());
                    nama_lengkap.getEditText().setText(loginStatus.getMessage().getNama_lengkap());
                    kontak.getEditText().setText(loginStatus.getMessage().getKontak());
                    alamat.getEditText().setText(loginStatus.getMessage().getAlamat());
                    pDialog.dismiss();
                }else{
                    pDialog.dismiss();
                    SweetAlertDialog failed = new SweetAlertDialog(EditdataActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Gagal")
                            .setContentText("Koneksi gagal")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    onBackPressed();
                                }
                            });

                    failed.setCancelable(false);
                    failed.show();
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getEditText().getText().length() < 6){
                    SweetAlertDialog failed = new SweetAlertDialog(EditdataActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Gagal")
                            .setContentText("Password tidak boleh kurang dari 6 karakter");

                    failed.show();
                    failed.setCancelable(false);
                }else{
                    pDialog.show();
                    updateViewmodel.init(new Account(username.getEditText().getText().toString(), password.getEditText().getText().toString(), nama_lengkap.getEditText().getText().toString(), kontak.getEditText().getText().toString(), alamat.getEditText().getText().toString(), null));
                    updateViewmodel.getData().observe(EditdataActivity.this, new Observer<Status>() {
                        @Override
                        public void onChanged(Status status) {
                            if (status.isStatus()){
                                pDialog.dismiss();
                                SweetAlertDialog success = new SweetAlertDialog(EditdataActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Berhasil")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                LoginPreferences.getInstance().init(EditdataActivity.this);
                                                LoginPreferences.getInstance().logOut();
                                                startActivity(new Intent(EditdataActivity.this, LoginActivity.class));
                                                finish();
                                            }
                                        })
                                        .setContentText("Edit data berhasil, silahkan login kembali");

                                success.setCancelable(false);
                                success.show();
                            }else{
                                pDialog.dismiss();
                                SweetAlertDialog failed = new SweetAlertDialog(EditdataActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Gagal")
                                        .setContentText(status.getMessage());

                                failed.setCancelable(false);
                                failed.show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}