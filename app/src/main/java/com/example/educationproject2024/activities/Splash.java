package com.example.educationproject2024.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.educationproject2024.R;

public class Splash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new CountDownTimer(1000,1000){
                    @Override
                    public void onTick(long l) {

                    }
                    @Override
                    public void onFinish() {
                        startActivity(new Intent(Splash.this, LoginActivity.class));
                        finish();
                    }
                }.start();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}