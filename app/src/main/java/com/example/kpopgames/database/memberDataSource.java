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
    private static final String COLUMN_GEBURTSDATUM = "GEBURTSDATUM";


    public static final String SQL_CREATE_MEMBERS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_MEMBERS + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " CHAR(50) NOT NULL,"
                    + COLUMN_GRUPPE + " CHAR(50),"
                    + COLUMN_GEBURTSDATUM + " CHAR(50))";

    public static final String SQL_DROP_MEMBERS = "DROP TABLE IF EXISTS " + TABLE_MEMBERS;

    private String[] columns = {
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_GRUPPE,
            COLUMN_GEBURTSDATUM
    };

    private static SQLiteDatabase database;

    public memberDataSource(SQLiteDatabase database) {this.database = database;}



    public void createMember(String gruppe, String name, String geburtsdatum) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_GRUPPE, gruppe);
        values.put(COLUMN_GEBURTSDATUM, geburtsdatum);

        database.insert(TABLE_MEMBERS, null, values);
    }

    private Member cursorToMember(Cursor cursor) {
        int idId = cursor.getColumnIndex(COLUMN_ID);
        int idName = cursor.getColumnIndex(COLUMN_NAME);
        int idGruppe = cursor.getColumnIndex(COLUMN_GRUPPE);
        int idGeburtstag = cursor.getColumnIndex(COLUMN_GEBURTSDATUM);

        int id = cursor.getInt(idId);
        String name = cursor.getString(idName);
        String gruppe = cursor.getString(idGruppe);
        String geburtstag = cursor.getString(idGeburtstag);

        return new Member (id, name, gruppe, geburtstag);
    }

    public List<Member> getAllMember() {
        database.execSQL(SQL_CREATE_MEMBERS);

        List<Member> memberList = new ArrayList<>();

        Cursor cursor = database.query(TABLE_MEMBERS,
                columns, null, null,
                null, null, "LOWER(" + COLUMN_GRUPPE + "), " + COLUMN_NAME);

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

    public List<Member> getAllMemberTodayBirthday(Long timestamp) {

        List<Member> memberList = new ArrayList<>();

        Cursor cursor = database.query(TABLE_MEMBERS,
                columns, null, null,
                null, null, "LOWER(" + COLUMN_GRUPPE + "), " + COLUMN_NAME);

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

    public void addMembers() {
        database.execSQL(SQL_DROP_MEMBERS);
        database.execSQL(SQL_CREATE_MEMBERS);

        createMember("IVE", "Yujin", "01-09-2003");
        createMember("IVE", "Wonyoung", "31-08-2004");
        createMember("IVE", "Gaeul", "24-09-2002");
        createMember("IVE", "Leeseo", "21-02-2007");
        createMember("IVE", "Rei", "03-02-2004");
        createMember("IVE", "Liz", "21-11-2004");

        createMember("aespa", "Karina", null);
        createMember("aespa", "Giselle", null);
        createMember("aespa", "Winter", null);
        createMember("aespa", "Ningning", null);

        createMember("Itzy", "Lia", null);
        createMember("Itzy", "Yeji", null);
        createMember("Itzy", "Ryujin", null);
        createMember("Itzy", "Chaeryeong", null);
        createMember("Itzy", "Yuna", null);

        createMember("NMIXX", "Lily", null);
        createMember("NMIXX", "Bae", null);
        createMember("NMIXX", "Kyujin", null);
        createMember("NMIXX", "Haewon", null);
        createMember("NMIXX", "Jiwoo", null);
        createMember("NMIXX", "Sullyoon", null);
        createMember(null, "Jinni", null);

        createMember("Kep1er", "Yujin", null);
        createMember("Kep1er", "Xiaoting", null);
        createMember("Kep1er", "Dayeon", null);
        createMember("Kep1er", "Chaehyun", null);
        createMember("Kep1er", "Yeseo", null);
        createMember("Kep1er", "Yeongeun", null);
        createMember("Kep1er", "Mashiro", null);
        createMember("Kep1er", "Hikaru", null);
    }
}
