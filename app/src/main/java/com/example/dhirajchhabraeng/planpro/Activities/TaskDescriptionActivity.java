package com.example.dhirajchhabraeng.planpro.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.dhirajchhabraeng.planpro.R;

public class TaskDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_description);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar_my_task_description);
        setSupportActionBar(toolbar);

        //Enables the "up" button in a custom toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
