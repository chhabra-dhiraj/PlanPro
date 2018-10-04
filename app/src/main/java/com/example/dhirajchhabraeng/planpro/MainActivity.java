package com.example.dhirajchhabraeng.planpro;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhirajchhabraeng.planpro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    //    navigation drawer identifiers below
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView) findViewById(R.id.nv);

//        setting of logged in user image in navigation drawer header circle imageview
        View hView =  nv.getHeaderView(0);
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

                    case R.id.clients:
                        Toast.makeText(MainActivity.this, "Client Activity to open", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.about_us:
                        Toast.makeText(MainActivity.this, "Introduction of the app to open", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.logout: {
                        firebaseAuth.signOut();

                        Intent i = getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage(getBaseContext().getPackageName());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("logout", 0);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
