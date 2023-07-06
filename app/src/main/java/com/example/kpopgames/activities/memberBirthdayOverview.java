package com.example.kpopgames.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kpopgames.R;
import com.example.kpopgames.adapter.MemberListAdapter;
import com.example.kpopgames.database.memberDataSource;
import com.example.kpopgames.database.utils.DataSource;
import com.example.kpopgames.model.Member;

import java.util.List;

public class memberBirthdayOverview extends AppCompatActivity {

    private memberDataSource memberDataSource;
    private ListView memberListView;
    private MemberListAdapter memberListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_birthday);
        findViewById(R.id.txtMemberBirthdayDefault).setVisibility(GONE);
        memberDataSource = DataSource.get().getMemberDataSource();



        showAllMembersWithBirthday();
    }

    private void showAllMembersWithBirthday () {
        final List<Member> memberList = memberDataSource.getAllMemberTodayBirthday();

        if (memberList.toString().equals("[]")) {
            findViewById(R.id.txtMemberBirthdayDefault).setVisibility(VISIBLE);
        } else {
            memberListView = findViewById(R.id.listview_member);
            memberListAdapter = new MemberListAdapter(this, memberList);
            memberListView.setAdapter(memberListAdapter);
        }
    }
}
