package com.example.dhirajchhabraeng.planpro.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.dhirajchhabraeng.planpro.MainActivity;
import com.example.dhirajchhabraeng.planpro.R;
import com.example.dhirajchhabraeng.planpro.StorageManagement.PrefManager;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private PrefManager prefManager;

    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        firebaseAuth = FirebaseAuth.getInstance();

        Intent i;
        prefManager = new PrefManager(SplashScreenActivity.this);
        if (firebaseAuth.getCurrentUser() != null) {
            i = new Intent(SplashScreenActivity.this, MainActivity.class);
        } else if (prefManager.isFirstTimeLaunch()) {
            i = new Intent(SplashScreenActivity.this, WelcomeActivity.class);
        } else {
            i = new Intent(SplashScreenActivity.this, LoginActivity.class);
        }
        startActivity(i);

        // close this activity
        finish();
    }
}