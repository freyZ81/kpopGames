package com.example.kpopgames.activities.memberPick;

import static com.example.kpopgames.activities.MainActivity.strCountMember;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kpopgames.R;
import com.example.kpopgames.activities.MainActivity;
import com.example.kpopgames.activities.memberBirthdayOverview;
import com.example.kpopgames.database.memberDataSource;
import com.example.kpopgames.database.utils.DataSource;
import com.example.kpopgames.model.Member;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class memberPickOverview extends AppCompatActivity {

    public int countMember;
    private memberDataSource memberDataSource;
    private TextView txtNameMemberOne;
    private TextView txtNameMemberTwo;
    private TextView txtGroupMemberOne;
    private TextView txtGroupMemberTwo;
    private TextView txtWinnerText;
    private TextView txtWinnerTextMember;
    private List<Member> memberList;
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

        List<Member> memberList = memberDataSource.getListOfMember(countMember);
        setMemberOne(memberList);
        setMemberTwo(memberList);

        btnMemberOne = findViewById(R.id.btnMemberOnePick);
        btnMemberOne.setFocusable(false);
        btnMemberOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCountMember(memberList, memberOne, 2);
            }
        });

        btnMemberTwo = findViewById(R.id.btnMemberTwoPick);
        btnMemberTwo.setFocusable(false);
        btnMemberTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCountMember(memberList, memberTwo, 1);
            }
        });

    }

    public void setMemberOne(List<Member> memberList) {
        int randomNumOne;
        if (memberList.size() > 1) {
            randomNumOne = ThreadLocalRandom.current().nextInt(1, memberList.size() + 1);
        } else {
            randomNumOne = 1;
        }
        memberOne = memberList.get(randomNumOne-1);
        memberList.remove(memberOne);
        txtNameMemberOne = findViewById(R.id.txtMemberOneName);
        txtNameMemberOne.setText(memberOne.getName());
        txtGroupMemberOne = findViewById(R.id.txtMemberOneGroup);
        txtGroupMemberOne.setText(memberOne.getGruppe());
    }

    public void setMemberTwo(List<Member> memberList) {
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

    public void checkCountMember(List<Member> memberList, Member winMember, int nextMember) {
        Log.e("list", memberList.size() + ", " + memberList);
        if (memberList.size() == 0) {
            txtNameMemberOne.setVisibility(View.GONE);
            txtGroupMemberOne.setVisibility(View.GONE);
            txtNameMemberTwo.setVisibility(View.GONE);
            txtGroupMemberTwo.setVisibility(View.GONE);
            btnMemberOne.setVisibility(View.GONE);
            btnMemberTwo.setVisibility(View.GONE);
            txtWinnerText.setVisibility(View.VISIBLE);
            txtWinnerTextMember.setVisibility(View.VISIBLE);
            txtWinnerText.setText("Herzlichen Glückwunsch! Der Gewinner ist:");
            if (winMember.getGruppe() != null) {
                txtWinnerTextMember.setText(winMember.getName() + " aus " + winMember.getGruppe());
            } else {
                txtWinnerTextMember.setText(winMember.getName());
            }
        } else {
            if (nextMember == 1) {
                setMemberOne(memberList);
            } else if (nextMember == 2) {
                setMemberTwo(memberList);
            }
        }
    }
}