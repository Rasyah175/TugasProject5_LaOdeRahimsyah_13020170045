package id.mobileprogramming.tugasproject5.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import id.mobileprogramming.tugasproject5.R;
import maes.tech.intentanim.CustomIntent;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(i);
                CustomIntent.customType(SplashScreenActivity.this, "fadein-to-fadeout");
                finish();
            }
        }, 2000);
    }
}