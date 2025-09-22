package com.example.pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class SplashInicialActivity extends AppCompatActivity {

    private final Handler handler = new Handler();
    private final Runnable splashRunnable = () -> {
        Intent rota = new Intent(SplashInicialActivity.this, MainActivity.class);
        rota.putExtra("offset", getIntent().getIntExtra("offset", 0));
        rota.putExtra("limit", getIntent().getIntExtra("limit", 151));
        startActivity(rota);
        finish();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_inicial);

        ImageView imgSplash = findViewById(R.id.splashInicial);
        Glide.with(this)
                .load("https://media4.giphy.com/media/v1.Y2lkPTc5MGI3NjExNXc5NWQ1bDlzcGVidzRhbnNyZDJxY3pyMzYzemhta2dtbHZpNTlmaiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/OyYK79ZIfWfCM/giphy.gif")
                .into(imgSplash);

        handler.postDelayed(splashRunnable, 5000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(splashRunnable);
    }

}