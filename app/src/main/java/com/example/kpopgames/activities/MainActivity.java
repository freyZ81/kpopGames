package com.example.kpopgames.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kpopgames.R;
import com.example.kpopgames.activities.memberList.memberListOverview;
import com.example.kpopgames.activities.memberPick.memberPickOverview;
import com.example.kpopgames.activities.memberBirthdayOverview;
import com.example.kpopgames.database.utils.DataSource;


public class MainActivity extends AppCompatActivity {
    private static Context appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = this;
        DataSource.init(appContext);

        setContentView(R.layout.activity_main);
    }

    public static Context getAppContext() {
        return appContext;
    }

    public void onClickBtnMemberPick(View v) {
        Intent myIntent = new Intent(MainActivity.this, memberPickOverview.class);
        MainActivity.this.startActivity(myIntent);
    }
    public void onClickBtnMemberBirthday(View v) {
        Intent myIntent = new Intent(MainActivity.this, memberBirthdayOverview.class);
        MainActivity.this.startActivity(myIntent);
    }
    public void onClickBtnMemberList(View v) {
        Intent myIntent = new Intent(MainActivity.this, memberListOverview.class);
        MainActivity.this.startActivity(myIntent);
    }


}