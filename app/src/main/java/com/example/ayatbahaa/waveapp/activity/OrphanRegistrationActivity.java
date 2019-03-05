package com.example.ayatbahaa.waveapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.ayatbahaa.waveapp.R;
import com.example.ayatbahaa.waveapp.database.Orphan;
import com.example.ayatbahaa.waveapp.database.Person;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class OrphanRegistrationActivity extends PersonRegistrationActivity {
    private EditText mEducationLevel, mSkills, mHobbies;
    private CheckBox mIsStudyingCurrently;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_orphans_registration);
        person = new Orphan();

        mEducationLevel = findViewById(R.id.educationLevel);
        mSkills = findViewById(R.id.skills);
        mHobbies = findViewById(R.id.hobbies);
        mIsStudyingCurrently = findViewById(R.id.studyingCurrently);

        mIsStudyingCurrently.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mEducationLevel.setVisibility(View.VISIBLE);
                } else {
                    mEducationLevel.setVisibility(View.INVISIBLE);
                }
            }
        });

        super.onCreate(savedInstanceState);
    }

    @Override
    CollectionReference getPersonCollection() {
        return FirebaseFirestore.getInstance().collection("Orphans");
    }

    @Override
    StorageReference getStorageRef() {
        return FirebaseStorage.getInstance().getReference("Orphans");

    }

    @Override
    Intent getRegistrationIntent() {
        return new Intent(this, OrphanRegistrationActivity.class);
    }


    @Override
    boolean extractDataIntoObject() {
        Orphan orphan = (Orphan) person;
        String temp = mHobbies.getText().toString().trim();
        if (!temp.isEmpty()) {
            orphan.setHobbies(temp);
        }

        temp = mSkills.getText().toString().trim();
        if (!temp.isEmpty()) {
            orphan.setSkills(temp);
        }

        orphan.setStudyingCurrently(mIsStudyingCurrently.isChecked());

        temp = mEducationLevel.getText().toString().trim();
        if (mEducationLevel.getVisibility() == View.VISIBLE) {

            if (temp.isEmpty() || Integer.parseInt(temp) < 1) {
                mEducationLevel.setError(getString(R.string.empty_value_error));
                mEducationLevel.requestFocus();
                return false;
            } else {
                orphan.setEducationLevel(Integer.parseInt(temp));
            }
        }

        return super.extractDataIntoObject();
    }

    @Override
    void getOldData() {

        personDocument.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                person = documentSnapshot.toObject(Orphan.class);
                OrphanRegistrationActivity.super.getOldData();

                Orphan orphan = (Orphan) person;
                if (orphan.getSkills() != null && !orphan.getSkills().isEmpty()) {
                    mSkills.setText(orphan.getSkills());
                }

                if (orphan.getHobbies() != null && !orphan.getHobbies().isEmpty()) {
                    mHobbies.setText(orphan.getHobbies());
                }
                mIsStudyingCurrently.setChecked(orphan.isStudyingCurrently());
                mEducationLevel.setText(String.valueOf(orphan.getEducationLevel()));
            }
        });




    }
}
