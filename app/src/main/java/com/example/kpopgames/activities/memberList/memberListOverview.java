package com.example.kpopgames.activities.memberList;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kpopgames.R;
import com.example.kpopgames.database.memberDataSource;
import com.example.kpopgames.model.Member;
import com.example.kpopgames.database.utils.DataSource;


import android.util.Log;
import java.util.List;


public class memberListOverview extends AppCompatActivity {

    private memberDataSource memberDataSource;
    private ListView memberListView;
    private MemberListAdapter memberListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);
        Log.e("test", "test");
        memberDataSource = DataSource.get().getMemberDataSource();


        showAllListEntries();
    }



    private void showAllListEntries () {
        final List<Member> memberList = memberDataSource.getAllMember();
        memberListView = findViewById(R.id.listview_member);
        memberListView.setTextFilterEnabled(true);
        memberListAdapter = new MemberListAdapter(this, memberList);
        memberListView.setAdapter(memberListAdapter);
    }

}
