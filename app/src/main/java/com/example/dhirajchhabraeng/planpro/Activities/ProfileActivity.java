package com.example.dhirajchhabraeng.planpro.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dhirajchhabraeng.planpro.Pojos.Profile;
import com.example.dhirajchhabraeng.planpro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();


    }
}