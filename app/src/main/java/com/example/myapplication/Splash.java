package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new  Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(Splash.this,HomeActivity.class);
                startActivity(in);
                finish();
            }
        },SPLASH_TIME);

    }
}
