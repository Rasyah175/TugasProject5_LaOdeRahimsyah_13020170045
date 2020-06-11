package id.mobileprogramming.tugasproject5.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import cn.pedant.SweetAlert.SweetAlertDialog;
import id.mobileprogramming.tugasproject5.R;
import id.mobileprogramming.tugasproject5.models.LoginStatus;
import id.mobileprogramming.tugasproject5.viewmodels.LoginViewmodel;
import id.mobileprogramming.tugasproject5.views.sharedpreferences.LoginPreferences;

public class LoginActivity extends AppCompatActivity {
    private Button login_signup, sign_in;
    private TextInputLayout username, password;
    private SharedPreferences preferences;
    private LoginViewmodel viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_signup = findViewById(R.id.login_signup);
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        viewmodel = new ViewModelProvider(this).get(LoginViewmodel.class);
        preferences = LoginPreferences.getInstance().getShared(this);

        login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        if (preferences.getBoolean("status", false)){
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }

        sign_in = findViewById(R.id.sign_in);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                pDialog.show();

                viewmodel.init(username.getEditText().getText().toString(), password.getEditText().getText().toString());
                viewmodel.getData().observe(LoginActivity.this, new Observer<LoginStatus>() {
                    @Override
                    public void onChanged(final LoginStatus loginStatus) {
                        if (loginStatus.isStatus()){
                            pDialog.dismiss();
                            SweetAlertDialog success = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Berhasil")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            LoginPreferences.getInstance().init(LoginActivity.this);
                                            LoginPreferences.getInstance().setData(loginStatus.isStatus(), loginStatus.getMessage().getUsername(), loginStatus.getMessage().getPassword());
                                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                            finish();
                                        }
                                    })
                                    .setContentText("Login berhasil");

                            success.setCancelable(false);
                            success.show();
                        }else{
                            SweetAlertDialog failed = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Gagal")
                                    .setContentText("Login gagal");

                            failed.setCancelable(false);
                            failed.show();
                            pDialog.dismiss();
                        }
                    }
                });
            }
        });
    }
}