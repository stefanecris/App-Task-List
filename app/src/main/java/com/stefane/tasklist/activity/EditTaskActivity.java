package com.stefane.tasklist.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.stefane.tasklist.R;
import com.stefane.tasklist.helper.TaskDAO;
import com.stefane.tasklist.model.Task;

public class EditTaskActivity extends AppCompatActivity {

    private TextInputEditText fieldTask;
    private Task atualTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        fieldTask = findViewById(R.id.fieldTask);

        getTask();

        if(atualTask != null){
            fillTaskField();
        }

    }

    private void getTask(){
        atualTask = (Task) getIntent().getSerializableExtra("Task");
    }

    private void fillTaskField(){
        fieldTask.setText(atualTask.getTaskTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.iconSave){

            TaskDAO taskDAO = new TaskDAO(getApplicationContext());

            String taskTitle = fieldTask.getText().toString();

            if(atualTask != null){ // Edição
                if(!taskTitle.isEmpty()){
                    atualTask.setTaskTitle(taskTitle);
                }
                if(taskDAO.update(atualTask)){
                    Toast.makeText(getApplicationContext(), "Task updated succesfully", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Error updating the task", Toast.LENGTH_LONG).show();
                }
            }else{
                Task task = new Task();
                if(!taskTitle.isEmpty()) {
                    task.setTaskTitle(taskTitle);
                }
                if(taskDAO.save(task)){
                    Toast.makeText(getApplicationContext(), "Task saved succesfully", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Error saving the task", Toast.LENGTH_LONG).show();
                }
            }

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
