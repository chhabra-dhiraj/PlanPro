package com.example.dhirajchhabraeng.planpro.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.dhirajchhabraeng.planpro.Activities.TaskDescriptionActivity;
import com.example.dhirajchhabraeng.planpro.R;

import java.util.ArrayList;

public class MyTasksRecyclerAdapter extends RecyclerView.Adapter<MyTasksRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> tasksList;

    public MyTasksRecyclerAdapter(Context ctx, ArrayList<String> tasksList) {
        this.context = ctx;
        this.tasksList = tasksList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            taskTitle = itemView.findViewById(R.id.task_title_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyTasksRecyclerAdapter.this.context, TaskDescriptionActivity.class);
                    MyTasksRecyclerAdapter.this.context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(this.context).inflate(R.layout.list_item_my_tasks, parent, false);

        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.taskTitle.setText(this.tasksList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.tasksList.size();
    }
}
