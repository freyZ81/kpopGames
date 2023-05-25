package com.example.kpopgames.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kpopgames.R;
import com.example.kpopgames.activities.memberPick.memberPickOverview;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickBtnMemberPick(View v)
    {
        Toast.makeText(this, "Clicked on Button", Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(MainActivity.this, memberPickOverview.class);
        MainActivity.this.startActivity(myIntent);
    }


}