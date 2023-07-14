package com.example.kpopgames.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kpopgames.model.Member;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;

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
    private String[] column_id = {
            COLUMN_ID
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

    public List<Member> getListOfMember(int countMemberInGame) {
        int maxCountMember = getLastId();
        int addCount = Math.round(maxCountMember/countMemberInGame);
        int idNumber = 1;
        List<Member> memberList = new ArrayList<>();

        ArrayList<Integer> arrlist = new ArrayList<Integer>(5);


        for(int i=1;i<=countMemberInGame;i++) {
            int rndNum = (int) ((Math.random() * (maxCountMember - 1)) + 1);
            if (arrlist.contains(rndNum)) {
                while(arrlist.contains(rndNum)) {
                    rndNum = ThreadLocalRandom.current().nextInt(1, maxCountMember + 1);
                }

            }

            arrlist.add(rndNum);
            Cursor cursor = database.query(TABLE_MEMBERS,
                    columns, COLUMN_ID + " = " + rndNum, null,
                    null, null, null);
            cursor.moveToFirst();
            Member member;
            member = cursorToMember(cursor);
            memberList.add(member);
            cursor.close();


            idNumber += addCount;
        }
        return memberList;
    }

    public int getLastId() {
        Cursor cursor = database.query(TABLE_MEMBERS,
                column_id, null, null,
                null, null, COLUMN_ID + " desc");
        cursor.moveToFirst();
        int lastId = Integer.parseInt(cursor.getString(0));
        return lastId;
    }

    public List<Member> getAllMemberTodayBirthday() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("dd-MM");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        System.out.println("Date in Seoul: " + df.format(date));
        String formattedDate = df.format(date);

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

        createMember(null, "PSY", "31-12-1977");
        createMember(null, "Chungha", "09-02-1996");
        createMember(null, "Soyou", "12-02-1992");
        createMember(null, "Hyolyn", "11-12-1990");
        createMember(null, "Somi", "09-03-2001");
        createMember(null, "BabySoul", "06-07-1992");
        createMember(null, "Suzy", "10-10-1994");
        createMember(null, "Hyuna", "06-06-1992");
        createMember(null, "Sunmi", "02-05-1992");
        createMember(null, "IU", "16-05-1993");
        createMember(null, "G-Dragon", "18-08-1988");
        createMember(null, "Bibi", "27-09-1998");
        createMember(null, "Young-Ji", "10-09-2002");
        createMember(null, "Alexa", "09-12-1996");

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

        createMember("(G)I-DLE", "Shuhua", "06-01-2000");
        createMember("(G)I-DLE", "Miyeon", "31-01-1997");
        createMember("Ex-(G)I-DLE", "Soojin", "09-03-1998");
        createMember("(G)I-DLE", "Soyeon", "26-08-1998");
        createMember("(G)I-DLE", "Yuqi", "23-09-1999");
        createMember("(G)I-DLE", "Minnie", "23-10-1997");
        
        createMember("Red Velvet", "Seulgi", "10-02-1994");
        createMember("Red Velvet", "Wendy", "21-02-1994");
        createMember("Red Velvet", "Yeri", "05-03-1999");
        createMember("Red Velvet", "Irene", "29-03-1991");
        createMember("Red Velvet", "Joy", "03-09-1996");
        
        createMember("Le Sserafim", "Kazuha", "09-08-2003");
        createMember("Le Sserafim", "Yunjin", "08-10-2001");
        createMember("Le Sserafim", "Eunchae", "10-11-2006");
        createMember("Ex-Le Sserafim", "Garam", "16-11-2005");
        createMember("Le Sserafim", "Sakura", "19-03-1998");
        createMember("Le Sserafim", "Chaewon", "01-08-2000");
        createMember("Iz*One", "Chaeyeon", "11-01-2000");
        createMember("Iz*One", "Minju", "05-02-2001");
        createMember("Iz*One", "Nako", "18-06-2001");
        createMember("Iz*One", "Hyewon", "05-07-1999");
        createMember("Iz*One", "Eunbi", "27-09-1995");
        createMember("Iz*One", "Yena", "29-09-1999");
        createMember("Iz*One", "Hitomi", "06-10-2001");
        createMember("Iz*One", "Yuri", "22-10-2001");
        createMember("IVE", "Wonyoung", "31-08-2004");
        createMember("IVE", "Yujin", "01-09-2003");
        createMember("IVE", "Rei", "03-02-2004");
        createMember("IVE", "Leeseo", "21-02-2007");
        createMember("IVE", "Gaeul", "24-09-2002");
        createMember("IVE", "Liz", "21-11-2004");
        
        createMember("Twice", "Jihyo", "01-02-1997");
        createMember("Twice", "Mina", "24-03-1997");
        createMember("Twice", "Chaeyoung", "23-04-1999");
        createMember("Twice", "Dahyun", "28-05-1998");
        createMember("Twice", "Tzuyu", "14-06-1999");
        createMember("Twice", "Nayeon", "22-09-1995");
        createMember("Twice", "Jeongyeon", "01-11-1996");
        createMember("Twice", "Momo", "09-11-1996");
        createMember("Twice", "Sana", "29-12-1996");
        
        createMember("Loona", "Kim Lip", "10-02-1999");
        createMember("Loona", "Yves", "24-05-1997");
        createMember("Loona", "Choerry", "04-06-2001");
        createMember("Loona", "Jinsoul", "13-06-1997");
        createMember("Loona", "Haseul", "18-08-1997");
        createMember("Loona", "Heejin", "19-10-2000");
        createMember("Ex-Loona", "Chuu", "20-10-1999");
        createMember("Loona", "Yeojin", "11-11-2002");
        createMember("Loona", "Hyejoo", "13-11-2001");
        createMember("Loona", "Hyunjin", "15-11-2000");
        createMember("Loona", "Gowon", "19-11-2000");
        createMember("Loona", "Vivi", "09-12-1996");
        
        createMember("StayC", "Isa", "23-01-2002");
        createMember("StayC", "Sumin", "13-03-2001");
        createMember("StayC", "Yoon", "14-04-2004");
        createMember("StayC", "Seeun", "14-06-2003");
        createMember("StayC", "Sieun", "01-08-2001");
        createMember("StayC", "J", "09-12-2004");
        
        createMember("Momoland", "Hyebin", "12-01-1996");
        createMember("Ex-Momoland", "Daisy", "22-01-1999");
        createMember("Momoland", "Nancy", "13-04-2000");
        createMember("Ex-Momoland", "Taeha", "03-06-1998");
        createMember("Momoland", "Nayun", "31-07-1998");
        createMember("Ex-MML", "Yeonwoo", "01-08-1996");
        createMember("Momoland", "JooE", "18-08-1999");
        createMember("Momoland", "Ahin", "27-09-1999");
        createMember("Momoland", "Jane", "20-12-1997");
        
        createMember("Cignature", "Chloe", "06-01-2001");
        createMember("Cignature", "Jeewon", "01-04-1999");
        createMember("Cignature", "Semi", "10-04-2002");
        createMember("Cignature", "Seline", "20-07-2000");
        createMember("Cignature", "Chaesol", "14-07-1998");
        createMember("Cignature", "Dohee", "01-08-2002");
        createMember("Cignature", "Haeun aka Ye Ah", "09-10-1999");
        createMember("Cignature", "Belle", "03-11-2001");
        
        createMember("Dreamcatcher", "Yoohyeon", "07-01-1997");
        createMember("Dreamcatcher", "Gahyeon", "03-02-1999");
        createMember("Dreamcatcher", "Dami", "07-03-1997");
        createMember("Dreamcatcher", "Handong", "26-03-1996");
        createMember("Dreamcatcher", "Jiu", "17-05-1994");
        createMember("Dreamcatcher", "Sua", "10-08-1994");
        createMember("Dreamcatcher", "Siyeon", "01-10-1995");

        createMember("Everglow", "Mia", "13-01-2000");
        createMember("Everglow", "Onda", "18-05-2000");
        createMember("Everglow", "EU", "19-05-1998");
        createMember("Everglow", "Aisha", "21-07-2000");
        createMember("Everglow", "Sihyeon", "05-08-1999");
        createMember("Everglow", "Yiren", "29-12-2000");

        createMember("PurpleKiss", "Dosie", "11-02-2000");
        createMember("PurpleKiss", "Ireh", "30-04-2002");
        createMember("PurpleKiss", "Swan", "11-07-2003");
        createMember("PurpleKiss", "Goeun", "03-09-1999");
        createMember("Ex-PurpleKiss", "Jieun", "04-09-1997");
        createMember("PurpleKiss", "Yuki", "06-11-2002");
        createMember("PurpleKiss", "Chaein", "05-12-2002");

        createMember("BVNDIT", "Jungwoo", "02-04-1999");
        createMember("BVNDIT", "Simyeong", "27-05-1999");
        createMember("BVNDIT", "Yiyeon", "28-05-1995");
        createMember("BVNDIT", "Songhee", "08-11-1998");
        createMember("BVNDIT", "Seungeun", "27-12-2000");

        createMember("Stray Kids", "I.N", "08-02-2001");
        createMember("Stray Kids", "Hyunjin", "20-03-2000");
        createMember("Stray Kids", "Changbin", "11-08-1999");
        createMember("Stray Kids", "Han", "14-09-2000");
        createMember("Stray Kids", "Felix", "15-09-2000");
        createMember("Stray Kids", "Seungmin", "22-09-2000");
        createMember("Stray Kids", "Bang Chan", "03-10-1997");
        createMember("Stray Kids", "Lee Know", "25-10-1998");

        createMember("Exo", "D.O.", "12-01-1993");
        createMember("Exo", "Kai", "14-01-1994");
        createMember("Ex-Exo", "Tao", "02-03-1993");
        createMember("Exo", "Baekhyun", "06-03-1992");
        createMember("Exo", "Xiumin", "26-03-1990");
        createMember("Exo", "Sehun", "12-04-1994");
        createMember("Ex-Exo", "Luhan", "20-04-1990");
        createMember("Exo", "Suho", "22-05-1991");
        createMember("Exo", "Chen", "21-09-1992");
        createMember("Ex-Exo", "Lay", "07-10-1991");
        createMember("Ex-Exo", "Kris", "06-11-1990");
        createMember("Exo", "Chanyeol", "27-11-1992");

        createMember("Rolling Quartz", "Yeongeun", "08-07-1996");
        createMember("Rolling Quartz", "Arem", "27-09-1995");
        createMember("Rolling Quartz", "Iree", "17-10-1995");
        createMember("Rolling Quartz", "Jayoung", "27-10-1996");
        createMember("Rolling Quartz", "Hyunjung", "31-10-1996");

        createMember("Cherry Bullet", "Haeyoon", "10-01-1996");
        createMember("Cherry Bullet", "Bora", "03-03-1999");
        createMember("Cherry Bullet", "Yuju", "05-03-1997");
        createMember("Cherry Bullet", "Chaerin", "13-03-2002");
        createMember("Cherry Bullet", "Remi", "26-04-2001");
        createMember("Cherry Bullet", "Jiwon", "04-09-2000");
        createMember("Cherry Bullet", "May", "16-11-2004");

        createMember("Lightsum", "Hina", "07-04-2003");
        createMember("Lightsum", "Juhyeon", "08-04-2004");
        createMember("Lightsum", "Yujeong", "14-06-2004");
        createMember("Ex-Lightsum", "Huiyeon", "01-08-2005");
        createMember("Lightsum", "Sangah", "04-09-2002");
        createMember("Lightsum", "Chowon", "16-09-2002");
        createMember("Ex-Lightsum", "Jian", "04-11-2006");
        createMember("Lightsum", "Nayoung", "30-11-2002");

        createMember("Monsta X", "Hyungwon", "15-01-1994");
        createMember("Monsta X", "I.M", "26-01-1996");
        createMember("Ex-Monsta X", "Wonho", "01-03-1993");
        createMember("Monsta X", "Shownu", "18-06-1992");
        createMember("Monsta X", "Joohoney", "06-10-1994");
        createMember("Monsta X", "Minhyuk", "03-11-1993");
        createMember("Monsta X", "Kihyun", "22-11-1993");

        createMember("Ab6ix", "Daehwi", "29-01-2001");
        createMember("Ab6ix", "Donghyun", "17-09-1998");
        createMember("Ab6ix", "Woong", "15-10-1997");
        createMember("Ab6ix", "Woojin", "02-11-1999");
        createMember("Ex-Ab6ix", "Youngmin", "25-12-1995");

        createMember("Enhypen", "Jungwon", "09-02-2004");
        createMember("Enhypen", "Jay", "20-04-2002");
        createMember("Enhypen", "Sunoo", "24-06-2003");
        createMember("Enhypen", "Heeseung", "15-10-2001");
        createMember("Enhypen", "Jake", "15-11-2002");
        createMember("Enhypen", "Sunghoon", "08-12-2002");
        createMember("Enhypen", "Ni-Ki", "09-12-2005");

        createMember("Oneus", "Xion", "10-01-2000");
        createMember("Oneus", "Seoho", "07-06-1996");
        createMember("Oneus", "Keonhee", "27-06-1998");
        createMember("Oneus", "Leedo", "26-07-1997");
        createMember("Oneus", "Hwanwoong", "26-08-1998");
        createMember("Ex-Oneus", "Ravn", "02-09-1995");

        createMember("NCT", "Kun", "01-01-1996");
        createMember("NCT", "Lucas", "25-01-1999");
        createMember("NCT", "Doyoung", "01-02-1996");
        createMember("NCT", "Jisung", "05-02-2002");
        createMember("NCT", "Johnny", "09-02-1995");
        createMember("NCT", "Jaehyun", "14-02-1997");
        createMember("NCT", "Jungwoo", "19-02-1998");
        createMember("NCT", "Ten", "27-02-1996");
        createMember("NCT", "Renjun", "23-03-2000");
        createMember("NCT", "Jeno", "23-04-2000");
        createMember("NCT", "Haechan", "06-06-2000");
        createMember("NCT", "Taeil", "14-06-1994");
        createMember("NCT", "Taeyong", "01-07-1995");
        createMember("NCT", "Mark", "02-08-1999");
        createMember("NCT", "Xiaojun", "08-08-1999");
        createMember("NCT", "Jaemin", "13-08-2000");
        createMember("NCT", "Sungchan", "13-09-2001");
        createMember("NCT", "Hendery", "28-09-1999");
        createMember("NCT", "Yangyang", "10-10-2000");
        createMember("NCT", "Yuta", "26-10-1995");
        createMember("NCT", "Winwin", "28-10-1997");
        createMember("NCT", "Chenle", "22-11-2001");
        createMember("NCT", "Shotaro", "25-11-2000");

        createMember("BTS", "J-Hope", "18-02-1994");
        createMember("BTS", "Suga", "09-03-1993");
        createMember("BTS", "Jungkook", "01-09-1997");
        createMember("BTS", "RM", "12-09-1994");
        createMember("BTS", "Jimin", "13-10-1995");
        createMember("BTS", "Jin", "04-12-1992");
        createMember("BTS", "V", "30-12-1995");

        createMember("Super Junior", "Sungmin", "01-01-1986");
        createMember("Super Junior", "Kyuhyun", "03-02-1988");
        createMember("Super Junior", "Eunhyuk", "04-04-1986");
        createMember("Super Junior", "Siwon", "07-04-1986");
        createMember("Super Junior", "Ryeowook", "21-06-1987");
        createMember("Super Junior", "Leeteuk", "01-07-1983");
        createMember("Super Junior", "Heechul", "10-07-1983");
        createMember("Super Junior", "Yesung", "24-08-1984");
        createMember("Super Junior", "Shindong", "28-09-1985");
        createMember("Super Junior", "Donghae", "15-10-1986");

        createMember("Billlie", "Haram", "13-01-2001");
        createMember("Billlie", "Suhyeon", "15-01-2000");
        createMember("Billlie", "Sheon", "28-01-2003");
        createMember("Billlie", "Haruna", "30-01-2006");
        createMember("Billlie", "Siyoon", "16-02-2005");
        createMember("Billlie", "Moon Sua", "09-09-1999");
        createMember("Billlie", "Tsuki", "21-09-2002");

        createMember("A.C.E", "Donghun", "28-02-1993");
        createMember("A.C.E", "Wow", "15-03-1993");
        createMember("A.C.E", "Jun", "02-06-1994");
        createMember("A.C.E", "Kim Byeongkwan", "13-08-1996");
        createMember("A.C.E", "Chan", "31-12-1997");

        createMember("Ateez", "Yunho", "23-03-1999");
        createMember("Ateez", "Seonghwa", "03-04-1998");
        createMember("Ateez", "Yeosang", "15-06-1999");
        createMember("Ateez", "San", "10-07-1999");
        createMember("Ateez", "Mingi", "09-08-1999");
        createMember("Ateez", "Jongho", "12-10-2000");
        createMember("Ateez", "Hongjoong", "07-11-1998");
        createMember("Ateez", "Wooyoung", "26-11-1999");

        createMember("Astro", "Moonbin", "26-01-1998");
        createMember("Astro", "Rocky", "25-02-1999");
        createMember("Astro", "MJ", "05-03-1994");
        createMember("Astro", "JinJin", "15-03-1996");
        createMember("Astro", "Sanha", "21-03-2000");
        createMember("Astro", "Eunwoo", "30-03-1997");

        createMember("Treasure", "So Junghwan", "18-02-2005");
        createMember("Treasure", "Jihoon", "14-03-2000");
        createMember("Treasure", "Yoshi", "15-03-2000");
        createMember("Ex-Treasure", "Mashiho", "25-03-2001");
        createMember("Treasure", "Haruto", "05-04-2004");
        createMember("Treasure", "Hyunsuk", "21-04-1999");
        createMember("Ex-Treasure", "Bang Yedam", "07-05-2002");
        createMember("Treasure", "Jaehyuk", "23-07-2001");
        createMember("Treasure", "Asahi", "20-08-2001");
        createMember("Treasure", "Junkyu", "09-09-2000");
        createMember("Treasure", "Jeongwoo", "28-09-2004");
        createMember("Treasure", "Doyoung", "04-12-2003");

        createMember("CLASS:y", "Riwon", "11-01-2007");
        createMember("CLASS:y", "Boeun", "11-02-2008");
        createMember("CLASS:y", "Seonyou", "20-03-2008");
        createMember("CLASS:y", "Chaewon", "04-06-2003");
        createMember("CLASS:y", "Hyungseo", "25-06-2001");
        createMember("CLASS:y", "Jimin", "25-11-2007");
        createMember("CLASS:y", "Hyeju", "09-12-2003");

        createMember("Bebez", "Sion", "07-03-2001");
        createMember("Bebez", "YeYoung", "31-03-2000");
        createMember("Bebez", "Min", "05-08-2004");

        createMember("Ex-Oh My Girl", "JinE", "22-01-1995");
        createMember("Oh My Girl", "Seunghee", "25-01-1996");
        createMember("Oh My Girl", "Jiho", "04-04-1997");
        createMember("Oh My Girl", "Mimi", "01-05-1995");
        createMember("Oh My Girl", "Arin", "18-06-1999");
        createMember("Oh My Girl", "Hyojung", "28-07-1994");
        createMember("Oh My Girl", "Yubin", "09-09-1997");
        createMember("Oh My Girl", "YooA", "17-09-1995");

        createMember("KARD", "J.Seph", "21-06-1992");
        createMember("KARD", "Somin", "22-08-1996");
        createMember("KARD", "Jiwoo", "04-10-1996");
        createMember("KARD", "BM", "20-10-1992");

        createMember("Brave Girls", "Yuna", "06-04-1993");
        createMember("Brave Girls", "Yujeong", "02-05-1991");
        createMember("Brave Girls", "Eunji", "19-07-1992");
        createMember("Brave Girls", "Minyoung", "12-09-1990");

        createMember("CLC", "Eunbin", "06-01-2000");
        createMember("CLC", "Yeeun", "10-08-1998");
        createMember("CLC", "Seunghee", "10-10-1995");
        createMember("CLC", "Elkie", "02-11-1998");
        createMember("CLC", "Seungyeon", "06-11-1996");
        createMember("Ex-CLC", "Sorn", "18-11-1996");

        createMember("WOO!AH!", "Wooyeon", "11-02-2003");
        createMember("WOO!AH!", "Nana", "09-03-2001");
        createMember("WOO!AH!", "Lucy", "09-04-2004");
        createMember("WOO!AH!", "Minseo", "12-08-2004");
        createMember("WOO!AH!", "Sora", "30-08-2003");
        createMember("Ex-WOO!AH!", "Songyee", "25-09-2004");

        createMember("Craxy", "ChaeY", "06-01-2003");
        createMember("Craxy", "Karin", "23-04-2000");
        createMember("Craxy", "Wooah", "20-06-1997");
        createMember("Craxy", "Hyejin", "13-07-2000");
        createMember("Craxy", "Swan", "28-12-2000");

        createMember("Pixy", "Lola", "22-02-2001");
        createMember("Ex-Pixy", "Satbyeol", "27-02-2001");
        createMember("Pixy", "Sua", "24-02-2003");
        createMember("Ex-Pixy", "Ella", "26-03-1998");
        createMember("Pixy", "Rinji", "05-05-2006");
        createMember("Pixy", "Dia", "16-07-2001");
        createMember("Pixy", "Dajeong", "31-07-2003");

        createMember("Busters", "Yunji", "09-01-2007");
        createMember("Busters", "Takara", "19-01-2005");
        createMember("Busters", "Seira", "27-01-2004");
        createMember("Busters", "Jieun", "05-03-2005");
        createMember("Busters", "MinMin", "02-07-2006");
        createMember("Busters", "Minji", "26-09-2006");

        createMember("WJSN", "Yeoreum", "10-01-1999");
        createMember("WJSN", "Xuan Yi", "26-01-1995");
        createMember("WJSN", "Luda", "06-03-1997");
        createMember("WJSN", "Dawon", "16-04-1997");
        createMember("WJSN", "Dayoung", "14-05-1999");
        createMember("WJSN", "Eunseo", "27-05-1998");
        createMember("WJSN", "Cheng Xiao", "15-07-1998");
        createMember("WJSN", "Yeonjung", "03-08-1999");
        createMember("WJSN", "Bona", "19-08-1995");
        createMember("WJSN", "Soobin", "14-09-1996");
        createMember("WJSN", "Mei Qi", "15-10-1998");
        createMember("WJSN", "Exy", "06-11-1995");
        createMember("WJSN", "Seola", "24-12-1994");

        createMember("NewJeans", "Danielle", "11-04-2005");
        createMember("NewJeans", "Hyein", "21-04-2008");
        createMember("NewJeans", "Minji", "07-05-2004");
        createMember("NewJeans", "Haerin", "15-05-2006");
        createMember("NewJeans", "Hanni", "06-10-2004");

        createMember("Gugudan", "Mimi", "01-01-1993");
        createMember("Gugudan", "Hana", "30-04-1993");
        createMember("Gugudan", "Haebin", "16-08-1995");
        createMember("Gugudan", "Sejeong", "28-08-1996");
        createMember("Gugudan", "Sally", "23-10-1996");
        createMember("Gugudan", "Soyee", "21-11-1996");
        createMember("Gugudan", "Nayoung", "23-11-1995");
        createMember("Gugudan", "Kang Mina", "04-12-1999");

        createMember("tripleS", "Dahyun", "08-01-2003");
        createMember("tripleS", "Yeonji", "08-01-2008");
        createMember("tripleS", "Yubin", "03-02-2005");
        createMember("tripleS", "Yooyeon", "09-02-2001");
        createMember("tripleS", "Kotone", "10-03-2004");
        createMember("tripleS", "Hyerin", "12-04-2007");
        createMember("tripleS", "Xinyu", "25-05-2002");
        createMember("tripleS", "Nien", "02-06-2003");
        createMember("tripleS", "Seoyeon", "06-08-2003");
        createMember("tripleS", "Soomin", "03-10-2007");
        createMember("tripleS", "Nakyoung", "13-10-2002");
        createMember("tripleS", "Sohyun", "13-10-2002");
        createMember("tripleS", "Jiwoo", "24-10-2005");
        createMember("tripleS", "Chaeyeon", "04-12-2004");
        createMember("tripleS", "Kaede", "20-12-2005");

        createMember("Cravity", "Minhee", "17-09-2002");
        createMember("Cravity", "Serim", "03-03-1999");
        createMember("Cravity", "Allen", "26-04-1999");
        createMember("Cravity", "Jungmo", "05-02-2000");
        createMember("Cravity", "Woobin", "16-10-2000");
        createMember("Cravity", "Wonjin", "22-03-2001");
        createMember("Cravity", "Hyeongjun", "30-11-2002");
        createMember("Cravity", "Taeyoung", "27-01-2003");
        createMember("Cravity", "Seongmin", "01-08-2003");

        createMember("Pentagon", "Yuto", "23-01-1998");
        createMember("Pentagon", "Kino", "27-01-1998");
        createMember("Pentagon", "Wooseok", "31-01-1998");
        createMember("Pentagon", "Yeo One", "27-03-1996");
        createMember("Pentagon", "Jinho", "17-04-1992");
        createMember("Pentagon", "Hongseok", "17-04-1994");
        createMember("Pentagon", "Hui", "28-08-1993");
        createMember("Pentagon", "Yanan", "25-10-1996");
        createMember("Pentagon", "Shinwon", "11-12-1995");

        createMember("ADYA", "Yeonsu", "19-02-2003");
        createMember("ADYA", "Seowon", "26-04-2004");
        createMember("ADYA", "Sena", "12-10-2005");
        createMember("ADYA", "Seungchae", "26-10-2006");
        createMember("ADYA", "Chaeeun", "10-12-2005");

        createMember("Babymonster", "Chiquita", "17-02-2009");
        createMember("Babymonster", "Ruka", "20-03-2002");
        createMember("Babymonster", "Ahyeon", "11-04-2007");
        createMember("Babymonster", "Asa", "17-04-2006");
        createMember("Babymonster", "Pharita", "26-08-2005");
        createMember("Babymonster", "Rora", "14-10-2008");
        createMember("Babymonster", "Haram", "17-10-2007");

        createMember("XG", "Chisa", "17-01-2002");
        createMember("XG", "Hinata", "11-06-2002");
        createMember("XG", "Jurin", "19-06-2002");
        createMember("XG", "Maya", "10-08-2005");
        createMember("XG", "Juria", "28-11-2004");
        createMember("XG", "Cocona", "06-12-2005");
        createMember("XG", "Harvey", "18-12-2002");

        createMember("Lapillus", "Shana", "13-03-2003");
        createMember("Lapillus", "Yue", "03-06-2004");
        createMember("Lapillus", "Bessie", "15-07-2004");
        createMember("Lapillus", "Haeun", "02-11-2008");
        createMember("Lapillus", "Seowon", "05-12-2006");
        createMember("Lapillus", "Chanty", "15-12-2002");

        createMember("Seventeen", "Seungkwan", "16-01-1998");
        createMember("Seventeen", "Dino", "11-02-1999");
        createMember("Seventeen", "DK", "18-02-1997");
        createMember("Seventeen", "Vernon", "18-02-1998");
        createMember("Seventeen", "Mingyu", "06-04-1997");
        createMember("Seventeen", "Jun", "10-06-1996");
        createMember("Seventeen", "Hoshi", "15-06-1996");
        createMember("Seventeen", "Wonwoo", "17-07-1996");
        createMember("Seventeen", "S.Coups", "08-08-1995");
        createMember("Seventeen", "Jeonghan", "04-10-1995");
        createMember("Seventeen", "The8", "07-11-1997");
        createMember("Seventeen", "Woozi", "22-11-1996");
        createMember("Seventeen", "Joshua", "30-12-1995");

        createMember("Rocket Punch", "Suyun", "17-03-2001");
        createMember("Rocket Punch", "Dahyun", "29-04-2005");
        createMember("Rocket Punch", "Sohee", "14-08-2003");
        createMember("Rocket Punch", "Juri", "03-10-1997");
        createMember("Rocket Punch", "Yunkyoung", "01-11-2001");
        createMember("Rocket Punch", "Yeonhee", "06-12-2000");

        createMember("Weki Meki", "Sei", "07-01-2000");
        createMember("Weki Meki", "Suyeon", "20-04-1997");
        createMember("Weki Meki", "Elly", "20-07-1998");
        createMember("Weki Meki", "Lucy", "31-08-2002");
        createMember("Weki Meki", "Rina", "27-09-2001");
        createMember("Weki Meki", "Lua", "06-10-2000");
        createMember("Weki Meki", "Yoojung", "12-11-1999");
        createMember("Weki Meki", "Doyeon", "04-12-1999");

        createMember("Apink", "Chorong", "03-03-1991");
        createMember("Apink", "Namjoo", "15-04-1995");
        createMember("Apink", "Hayoung", "19-07-1996");
        createMember("Apink", "Bomi", "13-08-1993");
        createMember("Apink", "Eunji", "18-08-1993");

        createMember("SNSD", "Sooyoung", "10-02-1990");
        createMember("SNSD", "Taeyeon", "09-03-1989");
        createMember("Ex-SNSD", "Jessica", "18-04-1989");
        createMember("SNSD", "Sunny", "15-05-1989");
        createMember("SNSD", "Yoona", "30-05-1990");
        createMember("SNSD", "Seohyun", "28-06-1991");
        createMember("SNSD", "Tiffany", "01-08-1989");
        createMember("SNSD", "Hyoyeon", "22-09-1989");
        createMember("SNSD", "Yuri", "05-12-1989");

        createMember("WOO!AH!", "Wooyeon", "11-02-2003");
        createMember("WOO!AH!", "Nana", "09-03-2001");
        createMember("WOO!AH!", "Lucy", "09-04-2004");
        createMember("WOO!AH!", "Minseo", "12-08-2004");
        createMember("WOO!AH!", "Sora", "30-08-2003");
        createMember("Ex-WOO!AH!", "Songyee", "25-09-2004");
        createMember("WOO!AH!", "Wooyeon", "11-02-2003");
        createMember("WOO!AH!", "Nana", "09-03-2001");
        createMember("WOO!AH!", "Lucy", "09-04-2004");
        createMember("WOO!AH!", "Minseo", "12-08-2004");
        createMember("WOO!AH!", "Sora", "30-08-2003");

        createMember("AOA", "Seolhyun", "03-01-1995");
        createMember("Ex-AOA", "Jimin", "08-01-1991");
        createMember("Ex-AOA", "Choa", "06-03-1990");
        createMember("Ex-AOA", "Youkyung", "15-03-1993");
        createMember("AOA", "Chanmi", "19-06-1996");
        createMember("AOA", "Hyejeong", "10-08-1993");
        createMember("Ex-AOA", "Mina", "21-09-1993");
        createMember("Ex-AOA", "Yuna", "30-12-1992");

        createMember("fromis_9", "Saerom", "07-01-1997");
        createMember("fromis_9", "Seoyeon", "22-01-2000");
        createMember("fromis_9", "Jiwon", "20-03-1998");
        createMember("fromis_9", "Jiheon", "17-04-2003");
        createMember("fromis_9", "Chaeyoung", "14-05-2000");
        createMember("fromis_9", "Nagyung", "01-06-2000");
        createMember("fromis_9", "Hayoung", "29-09-1997");
        createMember("fromis_9", "Jisun", "23-11-1998");
        createMember("Ex-fromis_9", "Gyuri", "27-12-1997");

        createMember("TRI.BE", "Kelly", "16-01-2002");
        createMember("TRI.BE", "Songsun", "22-03-1997");
        createMember("TRI.BE", "Hyunbin", "26-03-2004");
        createMember("TRI.BE", "Mire", "26-03-2006");
        createMember("TRI.BE", "Jia", "30-06-2005");
        createMember("TRI.BE", "Jinha", "21-11-2003");
        createMember("TRI.BE", "Soeun", "10-12-2005");

        createMember("Shinee", "Taemin", "18-07-1993");
        createMember("April", "Rachel", "28-08-2000");
        createMember("ANS", "Dalyn", "27-08-1999");
    }
}
