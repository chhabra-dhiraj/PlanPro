package com.example.dhirajchhabraeng.planpro.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dhirajchhabraeng.planpro.R;
import com.example.dhirajchhabraeng.planpro.StorageManagement.PrefManager;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    private int backCount;
    Button btnGetStarted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        btnGetStarted = findViewById(R.id.btnActWelcomeGetStarted);
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLoginActivity();
            }
        });
    }

    //Reset the backCount every time the activity resumes
    @Override
    protected void onResume() {
        super.onResume();
        backCount = 0;
    }

    private void launchLoginActivity() {
        Intent i = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    //overriding method to prevent an unintentional back press
    @Override
    public void onBackPressed() {
        if(backCount == 0){
            Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT).show();
            backCount++;
        }else{
            super.onBackPressed();
        }
    }

}
