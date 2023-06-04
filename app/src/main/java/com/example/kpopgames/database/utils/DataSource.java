package com.example.kpopgames.database.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kpopgames.database.memberDataSource;

public class DataSource {
    private SQLiteDatabase database;
    private final DbHelper dbHelper;

    private com.example.kpopgames.database.memberDataSource memberDataSource;


    private  static DataSource singleton;

    private DataSource(Context ctx){
        dbHelper = new DbHelper(ctx);

        memberDataSource = new memberDataSource(database);
    }

    public static DataSource get() {
        return singleton;
    }

    public static synchronized void init(Context ctx) {
        if(singleton == null) {
            singleton = new DataSource(ctx);
        }
    }

    public memberDataSource getMemberDataSource() {
        return memberDataSource;
    }

    private void open() {
        Log.d("LOG_TAG", "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        Log.d("LOG_TAG", "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    private void close(){
        dbHelper.close();
        Log.d("log", "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }


}
