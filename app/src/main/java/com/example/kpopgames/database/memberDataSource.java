package com.example.kpopgames.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kpopgames.model.Member;

import java.util.ArrayList;
import java.util.List;

public class memberDataSource {

    private static final String TABLE_MEMBERS = "MEMBERS";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_GRUPPE = "GRUPPE";


    public static final String SQL_CREATE_MEMBERS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_MEMBERS + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " CHAR(50) NOT NULL,"
                    + COLUMN_GRUPPE + " CHAR(50))";

    public static final String SQL_DROP_MEMBERS = "DROP TABLE IF EXISTS " + TABLE_MEMBERS;

    private String[] columns = {
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_GRUPPE
    };

    private static SQLiteDatabase database;

    public memberDataSource(SQLiteDatabase database) {this.database = database;}



    public void createMember(String name, String gruppe) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_GRUPPE, gruppe);

        database.insert(TABLE_MEMBERS, null, values);

        Log.e("create", "Member " + name + " wurde mit der Gruppe " + gruppe + " hinzugefügt.");
    }

    private Member cursorToMember(Cursor cursor) {
        int idId = cursor.getColumnIndex(COLUMN_ID);
        int idName = cursor.getColumnIndex(COLUMN_NAME);
        int idGruppe = cursor.getColumnIndex(COLUMN_GRUPPE);

        int id = cursor.getInt(idId);
        String name = cursor.getString(idName);
        String gruppe = cursor.getString(idGruppe);

        return new Member (id, name, gruppe);
    }

    public List<Member> getAllMember() {
        database.execSQL(SQL_CREATE_MEMBERS);

        List<Member> memberList = new ArrayList<>();

        Cursor cursor = database.query(TABLE_MEMBERS,
                columns, null, null,
                null, null, null);

        cursor.moveToFirst();
        Member member;

        while(!cursor.isAfterLast()) {
            member = cursorToMember(cursor);
            memberList.add(member);
            cursor.moveToNext();
        }

        cursor.close();
        return memberList;
    }
}
