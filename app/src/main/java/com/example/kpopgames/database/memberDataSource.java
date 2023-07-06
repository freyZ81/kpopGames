package com.example.kpopgames.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kpopgames.model.Member;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    public List<Member> getAllMemberTodayBirthday() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM", Locale.getDefault());
        String formattedDate = df.format(c);
        Log.e("formDate", formattedDate);

        List<Member> memberList = new ArrayList<>();

        Cursor cursor = database.query(TABLE_MEMBERS,
                columns, COLUMN_GEBURTSDATUM + " like '" + formattedDate + "%'", null,
                null, null, COLUMN_GEBURTSDATUM);

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

        createMember("aespa", "Karina", "11-04-2000");
        createMember("aespa", "Giselle", "30-10-2000");
        createMember("aespa", "Winter", "01-01-2001");
        createMember("aespa", "Ningning", "23-10-2002");

        createMember("Itzy", "Ryujin", "17-04-2001");
        createMember("Itzy", "Yeji", "26-05-2000");
        createMember("Itzy", "Chaeryeong", "05-06-2001");
        createMember("Itzy", "Lia", "21-07-2000");
        createMember("Itzy", "Yuna", "09-12-2003");

        createMember("NMIXX", "Sullyoon", "26-01-2004");
        createMember("NMIXX", "Haewon", "25-02-2003");
        createMember("NMIXX", "Jiwoo", "13-04-2005");
        createMember("NMIXX", "Kyujin", "26-05-2006");
        createMember("NMIXX", "Lily", "17-10-2002");
        createMember("NMIXX", "Bae", "28-12-2004");
        createMember("Ex-NMIXX", "Jinni", "16-04-2004");

        createMember("Blackpink", "Jisoo", "03-01-1995");
        createMember("Blackpink", "Jennie", "16-01-1996");
        createMember("Blackpink", "Rose", "11-02-1997");
        createMember("Blackpink", "Lisa", "27-03-1997");

        createMember("Mamamoo", "Solar", "21-02-1991");
        createMember("Mamamoo", "Wheein", "17-04-1995");
        createMember("Mamamoo", "Hwasa", "23-07-1995");
        createMember("Mamamoo", "Moonbyul", "22-12-1992");

        createMember("Ex-Weeekly", "Jiyoon", "02-03-2002");
        createMember("Weeekly", "Jaehee", "18-03-2004");
        createMember("Weeekly", "Monday", "10-05-2002");
        createMember("Weeekly", "Zoa", "31-05-2005");
        createMember("Weeekly", "Jihan", "12-07-2004");
        createMember("Weeekly", "Soeun", "26-10-2002");
        createMember("Weeekly", "Soojin", "12-12-2001");

        createMember("Viviz", "Eunha", "30-05-1997");
        createMember("Viviz", "SinB", "03-06-1998");
        createMember("Viviz", "Umji", "19-08-1998");

        createMember("Kep1er", "Dayeon", "02-03-2003");
        createMember("Kep1er", "Hikaru", "12-03-2004");
        createMember("Kep1er", "Chaehyun", "26-04-2002");
        createMember("Kep1er", "Bahiyyih", "27-07-2004");
        createMember("Kep1er", "Yujin", "12-08-1996");
        createMember("Kep1er", "Yeseo", "22-08-2005");
        createMember("Kep1er", "Xiaoting", "12-11-1999");
        createMember("Kep1er", "Mashiro", "16-12-1999");
        createMember("Kep1er", "Yeongeun", "27-12-2004");




    }
}
