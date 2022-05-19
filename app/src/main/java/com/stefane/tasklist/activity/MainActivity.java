package com.stefane.tasklist.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.stefane.tasklist.adapter.Adapter;
import com.stefane.tasklist.R;
import com.stefane.tasklist.helper.RecyclerItemClickListener;
import com.stefane.tasklist.helper.TaskDAO;
import com.stefane.tasklist.model.Task;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView taskList;
    private List<Task> tasks = new ArrayList<>();
    private Task selectedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        taskList = findViewById(R.id.recyclerTaskList);

        taskList.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                taskList,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        openEditTaskActivityForUpdate(tasks.get(position));

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                        selectedTask = tasks.get(position);

                        openAlertDialog();

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        ));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditTaskActivityForInsert();
            }
        });
    }

    private void openEditTaskActivityForUpdate(Task task){
        Intent intent = new Intent(getApplicationContext(), EditTaskActivity.class);
        intent.putExtra("Task", task);
        startActivity(intent);
    }

    private void openEditTaskActivityForInsert(){
        Intent intent = new Intent(getApplicationContext(), EditTaskActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadTasks();
    }

    public void loadTasks(){

        TaskDAO taskDAO = new TaskDAO(getApplicationContext());
        tasks = taskDAO.list();

        Adapter adapter = new Adapter(tasks);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        taskList.setLayoutManager(layoutManager);
        taskList.setHasFixedSize(true);
        taskList.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        taskList.setAdapter(adapter);

    }

    public void openAlertDialog(){

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Delete");
        dialog.setMessage("Are you sure you want to delete this task?");

        dialog.setCancelable(false);

        dialog.setIcon(android.R.drawable.ic_btn_speak_now);

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TaskDAO taskDAO = new TaskDAO(getApplicationContext());
                if(taskDAO.delete(selectedTask)){
                    loadTasks();
                    Toast.makeText(getApplicationContext(), "Task deleted succesfully", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Error deleting the task", Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.setNegativeButton("No", null);

        dialog.create();
        dialog.show();

    }

}
