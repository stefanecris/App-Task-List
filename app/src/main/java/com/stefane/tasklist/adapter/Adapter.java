package com.stefane.tasklist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stefane.tasklist.R;
import com.stefane.tasklist.model.Task;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Task> taskList;

    public Adapter(List<Task> taskList){
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list, parent, false);

        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Task task = taskList.get(position);

        holder.viewTask.setText(task.getTaskTitle());

    }

    @Override
    public int getItemCount() {
        return this.taskList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView viewTask;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            viewTask = itemView.findViewById(R.id.task);

        }
    }

}
