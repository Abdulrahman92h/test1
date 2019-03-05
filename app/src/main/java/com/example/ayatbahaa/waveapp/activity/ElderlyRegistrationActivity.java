package com.example.ayatbahaa.waveapp.activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.ayatbahaa.waveapp.R;
import com.example.ayatbahaa.waveapp.database.Elderly;
import com.example.ayatbahaa.waveapp.database.Orphan;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ElderlyRegistrationActivity extends PersonRegistrationActivity {
    private EditText mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_elderly_registration);
        person = new Elderly();
        mPhone = findViewById(R.id.phone);

        super.onCreate(savedInstanceState);
    }

    @Override
    CollectionReference getPersonCollection() {
        return FirebaseFirestore.getInstance().collection("Elderly");
    }


    @Override
    StorageReference getStorageRef() {
        return FirebaseStorage.getInstance().getReference("Elderly");
    }

    @Override
    Intent getRegistrationIntent() {
        return new Intent(this, ElderlyRegistrationActivity.class);
    }


    @Override
    boolean extractDataIntoObject() {
        Elderly elderly = (Elderly) person;
        String temp = mPhone.getText().toString().trim();
        if (!temp.isEmpty()) {
            elderly.setPhone(temp);
        }

        return super.extractDataIntoObject();
    }


    @Override
    void getOldData() {


        personDocument.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                person = documentSnapshot.toObject(Elderly.class);
                ElderlyRegistrationActivity.super.getOldData();

                Elderly elderly = (Elderly) person;
                if (elderly.getPhone() != null && !elderly.getPhone().isEmpty()) {
                    mPhone.setText(elderly.getPhone());
                }
            }
        });


    }
}

