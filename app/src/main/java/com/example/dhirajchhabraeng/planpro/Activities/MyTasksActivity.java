package com.example.dhirajchhabraeng.planpro.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.dhirajchhabraeng.planpro.Adapters.MyTasksRecyclerAdapter;
import com.example.dhirajchhabraeng.planpro.R;

import java.util.ArrayList;

public class MyTasksActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> tasksList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar_my_tasks);
        setSupportActionBar(toolbar);

        //Enables the "up" button in a custom toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tasksList.add("Title 1");
        tasksList.add("Title 2");
        tasksList.add("Title 3");

        recyclerView = findViewById(R.id.recycler_view_my_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyTasksRecyclerAdapter(this, tasksList));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
