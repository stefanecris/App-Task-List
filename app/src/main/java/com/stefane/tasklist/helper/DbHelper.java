package com.stefane.tasklist.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NAME_DB = "DB_TASKS";
    public static String NAME_TABLE_TASKS = "tasks";

    public DbHelper(@Nullable Context context) {
        super(context, NAME_DB, null, VERSION);
    }

    @Override
    // Utilizado quando o BD é criado pela primeira vez
    // Chamado apenas uma vez, quando o usuáro instala o app
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + NAME_TABLE_TASKS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL); ";

        try{
            db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar a tabela");
        }catch(Exception e){
            Log.i("INFO DB", "Erro ao criar a tabela" + e.getMessage());
        }

    }

    @Override
    // Chamado quando o usuário está apenas atualizando o app
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

}
