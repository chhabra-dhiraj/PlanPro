package com.example.dhirajchhabraeng.planpro.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dhirajchhabraeng.planpro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    Button btnDeleteAccount;
    EditText etEmail, etMobileNum, etFirstName, etMiddleName, etLastName, etCity;
    CircleImageView civProfilePicture;
    FirebaseUser currUser;
    DatabaseReference currUserReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        currUser = FirebaseAuth.getInstance().getCurrentUser();
        currUserReference = FirebaseDatabase.getInstance()
                .getReference().child("Users").child(currUser.getUid());

        btnDeleteAccount = findViewById(R.id.btn_user_profile_activity_delete_account);
        civProfilePicture = findViewById(R.id.civ_upa_profile_picture);

        if(currUser.getPhotoUrl()!=null){
            Picasso.get().load(currUser.getPhotoUrl()).into(civProfilePicture);
        }else{
            Picasso.get().load(R.drawable.ic_person_white_150dp).into(civProfilePicture);
        }

        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Get auth credentials from the user for re-authentication. The example below shows
                // email and password credentials but there are multiple possible providers,
                // such as GoogleAuthProvider or FacebookAuthProvider.

                String password = null;
                AuthCredential credential = EmailAuthProvider
                        .getCredential(currUser.getEmail(), password);
                reAuthenticateAndDelete(currUser, credential);

//                user.getIdToken(false).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<GetTokenResult> task) {
//                        String token = task.getResult().toString();
//                        AuthCredential credential = GoogleAuthProvider.getCredential(token, null);
//                        reAuthenticateAndDelete(user, credential);
//                    }
//                });


                // Prompt the user to re-provide their sign-in credentials

            }
        });
    }

    private void reAuthenticateAndDelete(final FirebaseUser currUser, AuthCredential credential){
        currUser.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.e("TAG", "onComplete: authentication complete");
                            currUser.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.e("TAG", "User account deleted.");
                                                currUserReference.removeValue(new DatabaseReference.CompletionListener() {
                                                    @Override
                                                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                                        Toast.makeText(UserProfileActivity.this,
                                                                "Your account has been successfully deleted"
                                                                , Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                Intent intent = new Intent(UserProfileActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Log.e("TAG", "User account deletion unsucessful.");
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(UserProfileActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}