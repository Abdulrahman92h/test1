package com.example.ayatbahaa.waveapp.activity;

import android.os.Bundle;
import android.widget.TextView;
import com.example.ayatbahaa.waveapp.R;
import com.example.ayatbahaa.waveapp.database.Orphan;

import java.util.Locale;


public class OrphanProfileActivity extends PersonProfileActivity {
    private TextView mHobbies;
    private TextView mSkills;
    private TextView mStudyingStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_orphan_profile);
        mHobbies = findViewById(R.id.hobbies);
        mSkills = findViewById(R.id.skills);
        mStudyingStatus = findViewById(R.id.studyingStatus);

        super.onCreate(savedInstanceState);
    }


    @Override
    void setData() {
        super.setData();
        Orphan orphan= (Orphan) person;

        String studyingStatus;
        if (orphan.isStudyingCurrently()) {
            int eduLvl = orphan.getEducationLevel();
            if (Locale.getDefault().getDisplayLanguage().equals("ar")) {
                studyingStatus = eduLvl + " " + getString(R.string.studying_status_description_1);
            } else {
                studyingStatus = getString(R.string.studying_status_description_1) + " " + eduLvl;
            }

        } else {
            studyingStatus = getString(R.string.studying_status_description_2);
        }
        mStudyingStatus.setText(studyingStatus);



        if (orphan.getHobbies() != null && !orphan.getHobbies().isEmpty()) {
            mHobbies.setText(orphan.getHobbies());
        }


        if (orphan.getSkills() != null && !orphan.getSkills().isEmpty()) {
            mSkills.setText(orphan.getSkills());
        }
    }

}
