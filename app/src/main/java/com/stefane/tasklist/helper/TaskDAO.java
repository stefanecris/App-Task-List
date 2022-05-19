package com.stefane.tasklist.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.stefane.tasklist.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDAO implements ITaskDAO {

    private SQLiteDatabase write, read;

    public TaskDAO(Context context) {

        DbHelper db = new DbHelper(context);
        write = db.getWritableDatabase(); // getWritableDatabase() é usado para escrever no bd
        read = db.getReadableDatabase(); // getReadableDatabase() é usado para ler do bd

    }

    @Override
    public boolean save(Task task) {
        try{
            ContentValues cv = new ContentValues();
            cv.put("title", task.getTaskTitle()); // o primeiro argumento deve ser exatamente o nome da coluna no bd

            write.insert(DbHelper.NAME_TABLE_TASKS, null, cv);
            Log.i("INFO DB", "Sucesso ao salvar a tarefa");
        }catch(Exception e){
            Log.i("INFO DB", "Falha ao salvar a tarefa" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean update(Task task) {
        try{

            ContentValues cv = new ContentValues();
            cv.put("title", task.getTaskTitle());

            String[] args = {task.getId().toString()};

            write.update(DbHelper.NAME_TABLE_TASKS, cv, "id = ?", args);

            Log.i("INFO DB", "Sucesso ao atualizar a tarefa!");
        }catch(Exception e){
            Log.i("INFO DB", "Falha ao atualizar a tarefa!");
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Task task) {
        try{

            String[] args = {task.getId().toString()};

            write.delete(DbHelper.NAME_TABLE_TASKS, "id = ?", args);

            Log.i("INFO DB", "Falha ao excluir a tarefa!");

        }catch(Exception e){
            Log.i("INFO DB", "Falha ao excluir a tarefa!");
            return false;
        }
        return true;
    }

    @Override
    public List<Task> list() {

        List<Task> tasks = new ArrayList<>();

        Cursor cursor = read.rawQuery("SELECT * FROM " + DbHelper.NAME_TABLE_TASKS, null); // selectionArgs -> poderíamos passar filtros

        int indexColumnId = cursor.getColumnIndex("id");
        int indexColumnTask = cursor.getColumnIndex("title");

        while(cursor.moveToNext()){

            Task task = new Task();

            task.setId(cursor.getLong(indexColumnId));
            task.setTaskTitle(cursor.getString(indexColumnTask));

            tasks.add(task);

        }

        return tasks;

    }
}
