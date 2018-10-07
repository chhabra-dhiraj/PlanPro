package com.example.dhirajchhabraeng.planpro;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhirajchhabraeng.planpro.Activities.LoginActivity;
import com.example.dhirajchhabraeng.planpro.Activities.MyTasksActivity;
import com.example.dhirajchhabraeng.planpro.Activities.UserProfileActivity;
import com.example.dhirajchhabraeng.planpro.Pojos.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    //    navigation drawer identifiers below
    private DrawerLayout dl;
    private NavigationView nv;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar_main);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final CircleImageView nav_drawer_icon = findViewById(R.id.nav_drawer_icon);

        if (firebaseAuth.getCurrentUser() != null) {
            Picasso.get()
                    .load(firebaseAuth.getCurrentUser().getPhotoUrl())
                    .placeholder(R.drawable.ic_person_white_24dp)
                    .into(nav_drawer_icon);
        }

        dl = (DrawerLayout) findViewById(R.id.activity_main);

        nav_drawer_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl.openDrawer(GravityCompat.START);
            }
        });

        nv = (NavigationView) findViewById(R.id.nv);

//        setting of logged in user image in navigation drawer header circle imageview
        View hView = nv.getHeaderView(0);
        CircleImageView nav_user_image = hView.findViewById(R.id.nav_user_image);

        if (firebaseAuth.getCurrentUser() != null) {
            Picasso.get()
                    .load(firebaseAuth.getCurrentUser().getPhotoUrl())
                    .placeholder(R.drawable.ic_person_white_24dp)
                    .into(nav_user_image);
        }

//        setting of logged in user name in navigation drawer header textview
        TextView nav_user_name = hView.findViewById(R.id.nav_user_name);
        nav_user_name.setText(firebaseAuth.getCurrentUser().getDisplayName());

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {

                    case R.id.home:
                        Toast.makeText(MainActivity.this, "Priority Clients Activity to open", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.my_clients:
                        Toast.makeText(MainActivity.this, "My Clients Activity to open", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.my_tasks:
                        Intent intent = new Intent(MainActivity.this, MyTasksActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.about_us:
                        Toast.makeText(MainActivity.this, "Introduction of the app to open", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.logout: {
                        firebaseAuth.signOut();
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                        return true;
                    }

                    default:
                        return true;
                }

            }
        });

        nav_user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });

        FirebaseUser currUser = firebaseAuth.getCurrentUser();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference rootReference = firebaseDatabase.getReference();
        DatabaseReference ProfileReference = rootReference.child("Profiles");
        DatabaseReference currUserReference = ProfileReference.child(currUser.getUid()).child("User Details");

        if (currUser != null) {
            if (currUser.getPhotoUrl() != null) {
                user = new User(currUser.getDisplayName(), currUser.getEmail(), currUser.getPhoneNumber(), currUser.getPhotoUrl().toString());
            } else {
                user = new User(currUser.getDisplayName(), currUser.getEmail(), currUser.getPhoneNumber(), "");
            }
        }

        currUserReference.setValue(user);
    }

}
