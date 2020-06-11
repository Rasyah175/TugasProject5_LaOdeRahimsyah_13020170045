package id.mobileprogramming.tugasproject5.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import cn.pedant.SweetAlert.SweetAlertDialog;
import id.mobileprogramming.tugasproject5.R;
import id.mobileprogramming.tugasproject5.models.Account;
import id.mobileprogramming.tugasproject5.models.Status;
import id.mobileprogramming.tugasproject5.viewmodels.RegisterViewmodel;

public class SignupActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextInputLayout username, password, namalengkap, alamat, kontak;
    private RegisterViewmodel viewmodel;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        toolbar = findViewById(R.id.toolbar);
        username = findViewById(R.id.signup_username);
        password = findViewById(R.id.signup_password);
        namalengkap = findViewById(R.id.signup_namalengkap);
        alamat = findViewById(R.id.signup_alamat);
        kontak = findViewById(R.id.signup_kontak);
        btn = findViewById(R.id.btn_signup);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SweetAlertDialog pDialog = new SweetAlertDialog(SignupActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                pDialog.show();

                if (username.getEditText().getText().length() < 6 || password.getEditText().getText().length() < 6) {
                    pDialog.dismiss();
                    SweetAlertDialog failed = new SweetAlertDialog(SignupActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Gagal")
                            .setContentText("Username atau password tidak boleh kurang dari 6 karakter");

                    failed.setCancelable(false);
                    failed.show();
                }else{
                    viewmodel = new ViewModelProvider(SignupActivity.this).get(RegisterViewmodel.class);
                    viewmodel.init(new Account(username.getEditText().getText().toString(), password.getEditText().getText().toString(), namalengkap.getEditText().getText().toString(), kontak.getEditText().getText().toString(), alamat.getEditText().getText().toString(), null));
                    viewmodel.getData().observe(SignupActivity.this, new Observer<Status>() {
                        @Override
                        public void onChanged(Status status) {
                            if (status.isStatus()){
                                pDialog.dismiss();
                                SweetAlertDialog success = new SweetAlertDialog(SignupActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Berhasil")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                                finish();
                                            }
                                        })
                                        .setContentText("Registrasi berhasil");

                                success.setCancelable(false);
                                success.show();
                            }else{
                                pDialog.dismiss();
                                SweetAlertDialog failed = new SweetAlertDialog(SignupActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Gagal")
                                        .setContentText("Registrasi gagal");

                                failed.setCancelable(false);
                                failed.show();
                            }
                        }
                    });
                }
            }
        });

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}