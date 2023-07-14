package com.example.kpopgames.activities.memberList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kpopgames.R;
import com.example.kpopgames.adapter.MemberListAdapter;
import com.example.kpopgames.database.memberDataSource;
import com.example.kpopgames.model.Member;
import com.example.kpopgames.database.utils.DataSource;


import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class memberListOverview extends AppCompatActivity {

    private memberDataSource memberDataSource;
    private ListView memberListView;
    private MemberListAdapter memberListAdapter;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);
        memberDataSource = DataSource.get().getMemberDataSource();

        showAllMembers();

        btnAdd = findViewById(R.id.btnAddMember);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memberDataSource.addMembers();
                recreate();
                Toast.makeText(memberListOverview.this, "Die Liste wurde erfolgreich aktualisiert",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showAllMembers () {
        final List<Member> memberList = memberDataSource.getAllMember();
        memberListView = findViewById(R.id.listview_member);
        memberListView.setTextFilterEnabled(true);
        memberListAdapter = new MemberListAdapter(this, memberList);
        memberListView.setAdapter(memberListAdapter);
    }

}
