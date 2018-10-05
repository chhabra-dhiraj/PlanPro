package com.example.dhirajchhabraeng.planpro.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dhirajchhabraeng.planpro.MainActivity;
import com.example.dhirajchhabraeng.planpro.R;
import com.example.dhirajchhabraeng.planpro.StorageManagement.PrefManager;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener {

    private PrefManager prefManager;
    private FirebaseAuth firebaseAuth;
    private Button btnLogin;
    private int backCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        prefManager = new PrefManager(LoginActivity.this);


        if (firebaseAuth.getCurrentUser() != null) {
            launchHomeScreen();
            finish();
        }

        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.login_button);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.addAuthStateListener(LoginActivity.this);

            }
        });

    }

    @Override
    protected void onResume() {
        backCount = 0;
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0 && firebaseAuth.getCurrentUser() == null) {
            Toast.makeText(this, "You need to log in to continue", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser() == null) {
            Intent loginIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setIsSmartLockEnabled(false)
                    .setAvailableProviders(Arrays.asList(
                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                            new AuthUI.IdpConfig.EmailBuilder().build(),
                            new AuthUI.IdpConfig.PhoneBuilder().build()))
                    .build();


            startActivityForResult(loginIntent, 0);
        } else {
            Toast.makeText(this, "Login Successful :)", Toast.LENGTH_SHORT).show();
            if(prefManager.isFirstTimeLaunch()) {
                prefManager.setFirstTimeLaunch(false);
            }
            launchHomeScreen();
            finish();
            firebaseAuth.removeAuthStateListener(this);
        }
    }

    private void launchHomeScreen() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivityForResult(intent, 0);
        finish();
    }

    @Override
    public void onBackPressed() {
        if(backCount == 0){
            Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT).show();
            backCount++;
        }else{
            super.onBackPressed();
            finish();
        }
    }

}
