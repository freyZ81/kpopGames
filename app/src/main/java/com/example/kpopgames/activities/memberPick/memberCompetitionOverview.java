package com.example.kpopgames.activities.memberPick;

import static com.example.kpopgames.activities.MainActivity.strCountMember;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kpopgames.R;
import com.example.kpopgames.database.memberDataSource;
import com.example.kpopgames.database.utils.DataSource;
import com.example.kpopgames.model.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class memberCompetitionOverview extends AppCompatActivity {

    public int countMember;
    private memberDataSource memberDataSource;
    private TextView txtNameMemberOne;
    private TextView txtNameMemberTwo;
    private TextView txtGroupMemberOne;
    private TextView txtGroupMemberTwo;
    private TextView txtWinnerText;
    private TextView txtWinnerTextMember;
    private TextView txtRound;
    private int countRound;
    private int countRoundMax;
    private List<Member> memberList;
    private List<Member> memberListNext;
    private Member memberOne;
    private Member memberTwo;
    private Button btnMemberOne;
    private Button btnMemberTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        countMember = (int) getIntent().getSerializableExtra(strCountMember);
        memberDataSource = DataSource.get().getMemberDataSource();
        setContentView(R.layout.activity_member_pick_overview);

        txtWinnerText = findViewById(R.id.txtWinnerText);
        txtWinnerText.setVisibility(View.GONE);
        txtWinnerTextMember = findViewById(R.id.txtWinnerTextMember);
        txtWinnerTextMember.setVisibility(View.GONE);
        txtRound = findViewById(R.id.txtRound);
        countRoundMax = countMember;

        memberList = memberDataSource.getListOfMember(countMember);
        memberListNext = new ArrayList<>();
        setMember(memberList);


        btnMemberOne = findViewById(R.id.btnMemberOnePick);
        btnMemberOne.setFocusable(false);
        btnMemberOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCountMember(memberOne);
            }
        });

        btnMemberTwo = findViewById(R.id.btnMemberTwoPick);
        btnMemberTwo.setFocusable(false);
        btnMemberTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCountMember(memberTwo);
            }
        });
    }

    public void setMember(List<Member> memberList) {
        setRound();
        int randomNumOne;
        randomNumOne = ThreadLocalRandom.current().nextInt(1, memberList.size() + 1);

        memberOne = memberList.get(randomNumOne-1);
        memberList.remove(memberOne);
        txtNameMemberOne = findViewById(R.id.txtMemberOneName);
        txtNameMemberOne.setText(memberOne.getName());
        txtGroupMemberOne = findViewById(R.id.txtMemberOneGroup);
        txtGroupMemberOne.setText(memberOne.getGruppe());

        int randomNumTwo;
        if (memberList.size() > 1) {
            randomNumTwo = ThreadLocalRandom.current().nextInt(1, memberList.size() + 1);
        } else {
            randomNumTwo = 1;
        }

        memberTwo = memberList.get(randomNumTwo-1);
        memberList.remove(memberTwo);
        txtNameMemberTwo = findViewById(R.id.txtMemberTwoName);
        txtNameMemberTwo.setText(memberTwo.getName());
        txtGroupMemberTwo = findViewById(R.id.txtMemberTwoGroup);
        txtGroupMemberTwo.setText(memberTwo.getGruppe());
    }

    public void checkCountMember(Member winMember) {
        memberListNext.add(winMember);
        if (memberList.size() == 0) {
            memberList = memberListNext;
            memberListNext = new ArrayList<>();
        }
        if (memberList.size() > 1) {
            setMember(memberList);
        } else if (memberList.size() == 1) {
            txtNameMemberOne.setVisibility(View.GONE);
            txtGroupMemberOne.setVisibility(View.GONE);
            txtNameMemberTwo.setVisibility(View.GONE);
            txtGroupMemberTwo.setVisibility(View.GONE);
            btnMemberOne.setVisibility(View.GONE);
            btnMemberTwo.setVisibility(View.GONE);
            txtWinnerText.setVisibility(View.VISIBLE);
            txtWinnerTextMember.setVisibility(View.VISIBLE);
            txtWinnerText.setText("Congratulation! The winner is:");
            if (winMember.getGruppe() != null) {
                txtWinnerTextMember.setText(winMember.getName() + " from " + winMember.getGruppe());
            } else {
                txtWinnerTextMember.setText(winMember.getName());
            }
        }
    }

    public void setRound() {
        int memberCount = memberList.size();
        int memberNextCount = memberListNext.size();
        if (memberNextCount == 0) {
            if (memberCount == 2) {
                txtRound.setText("Final");
            } else if (memberCount == 4) {
                txtRound.setText("Semi-Final");
            } else if (memberCount == 8) {
                txtRound.setText("Quarter Final");
            } else {
                txtRound.setText("Qualifications");
            }
        }
    }
}
