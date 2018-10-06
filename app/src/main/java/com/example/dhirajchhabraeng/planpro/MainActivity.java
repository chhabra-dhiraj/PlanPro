package com.example.dhirajchhabraeng.planpro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhirajchhabraeng.planpro.Activities.LoginActivity;
import com.example.dhirajchhabraeng.planpro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.lang.annotation.Target;
import java.lang.reflect.Field;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    //    navigation drawer identifiers below
    private DrawerLayout dl;
    private NavigationView nv;

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
                    .placeholder(R.mipmap.ic_launcher_round)
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
        Picasso.get()
                .load(firebaseAuth.getCurrentUser().getPhotoUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .into(nav_user_image);

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
                        Toast.makeText(MainActivity.this, "My Personal tasks Activity to open", Toast.LENGTH_SHORT).show();
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

    }

}
