package id.mobileprogramming.tugasproject5.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import id.mobileprogramming.tugasproject5.R;

public class PariwisataActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pariwisata);

        imageView = findViewById(R.id.pariwisata_img);
        Intent i = getIntent();
        String gambar = i.getStringExtra("image");

        Glide.with(this)
                .load("https://api.sharekom.my.id/travel/assets/"+gambar)
                .into(imageView);
    }
}