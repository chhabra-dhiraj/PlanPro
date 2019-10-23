package com.example.dhirajchhabraeng.planpro.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dhirajchhabraeng.planpro.MainActivity;
import com.example.dhirajchhabraeng.planpro.Pojos.User;
import com.example.dhirajchhabraeng.planpro.R;
import com.example.dhirajchhabraeng.planpro.StorageManagement.PrefManager;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.Timer;

public class LoginActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener {

    private PrefManager prefManager;
    private FirebaseAuth firebaseAuth;
    private Button btnLogin;
    private int backCount;
    private int firebase_login_intent = 0;
    private String userFirstName, userMiddleName, userLastName, userProfilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        backCount = 0;

        firebaseAuth = FirebaseAuth.getInstance();

        prefManager = new PrefManager(LoginActivity.this);

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
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == firebase_login_intent) {
            if (firebaseAuth.getCurrentUser() == null) {
                Toast.makeText(this, "You need to log in to continue", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        //This if block is called everytime user clicks on "Login" button
        if (firebaseAuth.getCurrentUser() == null) {
            Intent loginIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setIsSmartLockEnabled(false)
                    .setAvailableProviders(Arrays.asList(
                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                            new AuthUI.IdpConfig.EmailBuilder().build(),
                            new AuthUI.IdpConfig.PhoneBuilder().build()))
                    .setTheme(R.style.FirebaseLoginIntentTheme)
                    .setLogo(R.drawable.common_google_signin_btn_icon_dark)
                    .build();

            startActivityForResult(loginIntent, firebase_login_intent);
        }
//        This blocks executes after successful login
        else {
            Toast.makeText(this, "Login Successful :)", Toast.LENGTH_SHORT).show();

//          postDelayed method is used to handle first time authentication by email
//          as it takes min 2 seconds to fetch the details from the default providers.
//          Moreover, 3 seconds is used to keep a buffer of 1 second
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    createCurrentUserNodeInDatabase();
                }
            }, 3000);


            //checks if it is first time login after launch of the app and sets the boolean accordingly.
            if (prefManager.isFirstTimeLaunch()) {
                prefManager.setFirstTimeLaunch(false);
            }

            finish();

            launchHomeScreen();
        }
    }

    private void createCurrentUserNodeInDatabase() {
        FirebaseUser currUser = firebaseAuth.getCurrentUser();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference rootReference = firebaseDatabase.getReference();
        DatabaseReference UsersReference = rootReference.child("Users");

        if (currUser != null) {
            DatabaseReference currUserDetailsReference = UsersReference.child(currUser.getUid()).child("User Details");

//        Log.e("TAG", "createCurrentUserNodeInDatabase: about to enter");
            if (currUser.getDisplayName() != null) {
//            Log.e("TAG", "createCurrentUserNodeInDatabase: " + currUser.getDisplayName());
                String[] splitStrings = currUser.getDisplayName().split("\\s+");
//            Log.e("TAG", "createCurrentUserNodeInDatabase: "+ splitStrings );
                if (splitStrings.length == 1) {
                    userFirstName = splitStrings[0];
//                Log.e("TAG", "createCurrentUserNodeInDatabase: " + userFirstName);
                } else if (splitStrings.length == 2) {
                    userFirstName = splitStrings[0];
                    userLastName = splitStrings[1];
//                Log.e("TAG", "createCurrentUserNodeInDatabase: " + userFirstName);
//                Log.e("TAG", "createCurrentUserNodeInDatabase: " + userLastName);
                } else {
                    userFirstName = splitStrings[0];
                    userMiddleName = splitStrings[1];
                    userLastName = splitStrings[2];
//                Log.e("TAG", "createCurrentUserNodeInDatabase: " + userFirstName);
//                Log.e("TAG", "createCurrentUserNodeInDatabase: " + userMiddleName);
//                Log.e("TAG", "createCurrentUserNodeInDatabase: " + userLastName);
                }
            }

            if (currUser.getPhotoUrl() != null) {
                userProfilePhoto = currUser.getPhotoUrl().toString();
            }

            User user = new User(userFirstName, userMiddleName, userLastName, currUser.getEmail(), currUser.getPhoneNumber(), "", "", userProfilePhoto);
            currUserDetailsReference.setValue(user);
        }
    }

    private void launchHomeScreen() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        firebaseAuth.removeAuthStateListener(this);
    }

    @Override
    public void onBackPressed() {
        if (backCount == 0) {
            Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT).show();
            backCount++;
        } else {
            finish();
            super.onBackPressed();
        }
    }
}
