package com.example.OnliDealz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(Splash.this, MainActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            vibrator.vibrate(500);
            finish();
        }, 3000);
    }
}