package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.data.MyDbHandler;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();
        MyDbHandler mydb = new MyDbHandler(SplashScreen.this);

        // Navigating to HomeActivity after 3 seconds...
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                    Intent intent = new Intent(SplashScreen.this,MainActivity .class);
                    startActivity(intent);
                    if(this != null)
                    {
                        finish();
                    }
                }
        }, 1000);

    }
}