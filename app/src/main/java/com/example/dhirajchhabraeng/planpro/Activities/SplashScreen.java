package com.example.dhirajchhabraeng.planpro.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.dhirajchhabraeng.planpro.MainActivity;
import com.example.dhirajchhabraeng.planpro.R;
import com.example.dhirajchhabraeng.planpro.StorageManagement.PrefManager;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private PrefManager prefManager;

    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        firebaseAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // It launches app's Main activity if user is already logged in,
                // launches the Welcome activity if it is the app's first time run,
                // launches the Login activity if just the user is currently not signed in.

                Intent i;
                prefManager = new PrefManager(SplashScreen.this);
                if (firebaseAuth.getCurrentUser() != null) {
                    i = new Intent(SplashScreen.this, MainActivity.class);
                } else if (prefManager.isFirstTimeLaunch()) {
                    i = new Intent(SplashScreen.this, WelcomeActivity.class);
                } else {
                    i = new Intent(SplashScreen.this, LoginActivity.class);
                }
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}

