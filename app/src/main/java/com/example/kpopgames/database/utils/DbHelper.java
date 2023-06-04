package com.example.kpopgames.database.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kpopgames.database.memberDataSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mde.db";
    private static final int DB_VERSION = 4;
    private Context context;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d("LOG_TAG", "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(memberDataSource.SQL_CREATE_MEMBERS);
        }
        catch (Exception ex) {
            Log.e("LOG_TAG", "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.d("LOG_TAG", "Downgrade von Datenbank Version "+oldVersion+" zu Version "+newVersion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("LOG_TAG", "Upgrade von Datenbank Version "+oldVersion+" zu Version "+newVersion);

        try{
            for(int vc = oldVersion; vc < newVersion; vc++){
                String filename = String.format("dbUpdate_%d_to_%d.sql", vc, vc+1);
                Log.d("LOG_TAG", "Suche nach delta-skript:"+filename);
                readAndExecuteSqlScript(db,filename );
            }
        }catch(Exception e){
            Log.d("LOG_TAG", "Fehler beim ausführen des Skripts: "+e.getMessage());
        }
    }

    private void readAndExecuteSqlScript(SQLiteDatabase db, String fileName){
        BufferedReader br = null;

        try{
            InputStream is = context.getAssets().open(fileName);
            br = new BufferedReader(new InputStreamReader(is));

            String line = null;
            StringBuilder statement = new StringBuilder();
            while((line = br.readLine()) != null){
                statement.append(line);
                statement.append("\n");
                if(line.endsWith(";")){
                    db.execSQL(statement.toString());
                    statement = new StringBuilder();
                }
            }
        }catch(IOException e){
            Log.d("LOG_TAG", "Fehler beim lesen des Skripts: "+e.toString());
        }
    }
}
