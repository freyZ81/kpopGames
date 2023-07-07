package com.example.kpopgames.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.kpopgames.R;
import com.example.kpopgames.activities.memberList.memberListOverview;
import com.example.kpopgames.activities.memberPick.memberPickOverview;
import com.example.kpopgames.activities.memberBirthdayOverview;
import com.example.kpopgames.database.utils.DataSource;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    public static final String strCountMember = "countMember";
    private static Context appContext;

    private int countMember;

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
        AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_DeviceDefault_Dialog_Alert);

                dlgBuilder.setMessage("Wie viele Member möchtest Du haben?");
                dlgBuilder.setCancelable(true);

                final CharSequence[] memberCount = {"2","4","8","16","32","64","128"};
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Wie viele Member sollen ausgewählt werden?");
                alert.setSingleChoiceItems(memberCount,-1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        countMember = Integer.parseInt(memberCount[which].toString());
                        Log.e("dies", "nuts: " + memberCount[which] + ", countMember: " + countMember);
                    }
                });
                alert.setPositiveButton("Weiter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent = new Intent(MainActivity.this, memberPickOverview.class);
                        myIntent.putExtra(strCountMember, countMember);
                        MainActivity.this.startActivity(myIntent);
                    }
                });
                alert.show();
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