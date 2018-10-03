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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            launchHomeScreen();
            finish();
        }

        setContentView(R.layout.activity_login_intent_base);

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

        if (firebaseAuth.getCurrentUser() != null) {
            launchHomeScreen();
            finish();
        }

        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && firebaseAuth.getCurrentUser() == null) {
            Toast.makeText(this, "The application needs to be logged in to continue", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser() == null) {
            firebaseAuth.removeAuthStateListener(this);

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

        }
    }

    private void launchHomeScreen() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}