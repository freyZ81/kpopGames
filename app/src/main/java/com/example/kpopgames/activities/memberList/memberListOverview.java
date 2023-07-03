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
import java.util.List;


public class memberListOverview extends AppCompatActivity {

    private memberDataSource memberDataSource;
    private ListView memberListView;
    private MemberListAdapter memberListAdapter;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);
        Log.e("test", "test");
        memberDataSource = DataSource.get().getMemberDataSource();

        showAllListEntries();

        btnAdd = findViewById(R.id.btnAddMember);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(memberListOverview.this, android.R.style.Theme_DeviceDefault_Dialog_Alert);

                dlgBuilder.setMessage("Gruppe:");
                dlgBuilder.setCancelable(true);

                final EditText inputGruppe = new EditText(
                        memberListOverview.this);
                dlgBuilder.setView(inputGruppe);
                inputGruppe.setSingleLine();
                dlgBuilder.setPositiveButton("Weiter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("inputGruppe", inputGruppe.getText().toString());
                        if (inputGruppe.getText().toString().length() != 0) {
                            AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(memberListOverview.this, android.R.style.Theme_DeviceDefault_Dialog_Alert);

                            dlgBuilder.setMessage("Member:");
                            dlgBuilder.setCancelable(true);
                            final EditText inputMember = new EditText(
                                    memberListOverview.this);
                            dlgBuilder.setView(inputMember);
                            inputMember.setSingleLine();
                            dlgBuilder.setPositiveButton("Hinzufügen", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (inputMember.getText().toString().length() != 0) {
                                        memberDataSource.createMember(inputMember.getText().toString(),
                                                inputGruppe.getText().toString());
                                        recreate();
                                    } else {
                                        Log.e("else", inputMember.getText().toString());
                                    }

                                }
                            });
                            AlertDialog alert = dlgBuilder.create();
                            alert.setIcon(android.R.drawable.ic_dialog_alert);
                            alert.show();
                        }
                    }
                });


                AlertDialog alert = dlgBuilder.create();
                alert.setIcon(android.R.drawable.ic_dialog_alert);
                alert.show();
            }
        });

    }



    private void showAllListEntries () {
        final List<Member> memberList = memberDataSource.getAllMember();
        Log.e("memberList", memberList.toString());
        memberListView = findViewById(R.id.listview_member);
        memberListView.setTextFilterEnabled(true);
        memberListAdapter = new MemberListAdapter(this, memberList);
        memberListView.setAdapter(memberListAdapter);
    }

}
