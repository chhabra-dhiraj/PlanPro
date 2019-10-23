package com.example.dhirajchhabraeng.planpro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhirajchhabraeng.planpro.Activities.LoginActivity;
import com.example.dhirajchhabraeng.planpro.Activities.MyTasksActivity;
import com.example.dhirajchhabraeng.planpro.Activities.UserProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener {

    private FirebaseAuth firebaseAuth;

    //    navigation drawer identifiers below
    private DrawerLayout dl;
    private NavigationView nv;
    private ActionBarDrawerToggle t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.tool_bar_main);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView) findViewById(R.id.nv);
        View hView = nv.getHeaderView(0);

        dl = (DrawerLayout) findViewById(R.id.activity_main);

//        t is used for displaying hamburger icon for navigation drawer with animation
//        the class of t (ActionBarDrawerToggle) does draw icon & animation in real time
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

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
                        finish();
                        firebaseAuth.addAuthStateListener(MainActivity.this);
                        firebaseAuth.signOut();
                        return true;
                    }

                    default:
                        return true;
                }

            }
        });

        if (firebaseAuth.getCurrentUser() != null) {
            CircleImageView nav_drawer_icon = findViewById(R.id.nav_drawer_icon);

            Picasso.get()
                    .load(firebaseAuth.getCurrentUser().getPhotoUrl())
                    .placeholder(R.drawable.ic_person_white_24dp)
                    .into(nav_drawer_icon);

//        setting of logged in user image in navigation drawer header circle imageview
            CircleImageView nav_user_image = hView.findViewById(R.id.nav_user_image);

            Picasso.get()
                    .load(firebaseAuth.getCurrentUser().getPhotoUrl())
                    .placeholder(R.drawable.ic_person_white_24dp)
                    .into(nav_user_image);

//        setting of logged in user name in navigation drawer header textview
            final TextView nav_user_name = hView.findViewById(R.id.nav_user_name);

//        postDelayed method is used to handle first time authentication by email
//        as it takes min 2 seconds to fetch the details from the default providers.
//        Moreover, 3 seconds is used to keep a buffer of 1 second
            nav_user_name.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        nav_user_name.setText(user.getDisplayName());
                    }
                }
            }, 3000);

            nav_user_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser() == null) {
            firebaseAuth.removeAuthStateListener(MainActivity.this);
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }
}
