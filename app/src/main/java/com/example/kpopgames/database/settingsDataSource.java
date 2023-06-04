package com.example.kpopgames.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class settingsDataSource {
    private static final String TABLE_SETTINGS = "SETTINGS";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_SETTING = "SETTING";
    private static final String COLUMN_VALUE = "VALUE";

    public static final String SQL_CREATE_SETTINGS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_SETTINGS + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_SETTING + " CHAR(50) UNIQUE,"
                    + COLUMN_VALUE + " CHAR(50))";

    public static final String SQL_DROP_SETTINGS = "DROP TABLE IF EXISTS " + TABLE_SETTINGS;

    private final String[] columns = {
            COLUMN_ID,
            COLUMN_SETTING,
            COLUMN_VALUE
    };

    private SQLiteDatabase database;


}
