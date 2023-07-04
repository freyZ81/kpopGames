package com.example.kpopgames.activities;

import static android.view.View.GONE;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kpopgames.R;
import com.example.kpopgames.adapter.MemberListAdapter;
import com.example.kpopgames.database.memberDataSource;
import com.example.kpopgames.model.Member;

import java.util.List;

public class memberBirthdayOverview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_birthday);
        findViewById(R.id.txtMemberBirthdayDefault).setVisibility(GONE);



        showAllMembersWithBirthday();
    }

    private void showAllMembersWithBirthday () {
        Long timestampLong = System.currentTimeMillis();
        Log.e("TS", timestampLong.toString());

        final List<Member> memberList = memberDataSource.getAllMemberTodayBirthday(timestampLong);
        Log.e("memberList", memberList.toString());
        /*memberListView = findViewById(R.id.listview_member);
        memberListView.setTextFilterEnabled(true);
        memberListAdapter = new MemberListAdapter(this, memberList);
        memberListView.setAdapter(memberListAdapter);*/
    }
}
