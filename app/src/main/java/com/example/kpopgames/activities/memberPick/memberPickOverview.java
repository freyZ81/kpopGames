package com.example.kpopgames.activities.memberPick;

import static com.example.kpopgames.activities.MainActivity.strCountMember;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.kpopgames.R;
import com.example.kpopgames.database.memberDataSource;
import com.example.kpopgames.database.utils.DataSource;

public class memberPickOverview extends AppCompatActivity {

    public int countMember;
    private memberDataSource memberDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        countMember = (int) getIntent().getSerializableExtra(strCountMember);
        memberDataSource = DataSource.get().getMemberDataSource();

        memberDataSource.getListOfMember(countMember);


        setContentView(R.layout.activity_member_pick_overview);
        Toast.makeText(memberPickOverview.this, "strCountMember: " + countMember,
                Toast.LENGTH_LONG).show();
    }
}