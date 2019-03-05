package com.example.ayatbahaa.waveapp;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ayatbahaa.waveapp.activity.ElderlyProfileActivity;
import com.example.ayatbahaa.waveapp.activity.ElderlyRegistrationActivity;
import com.example.ayatbahaa.waveapp.activity.PersonProfileActivity;
import com.example.ayatbahaa.waveapp.database.Elderly;
import com.example.ayatbahaa.waveapp.database.Person;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

public class ElderlyActivity extends PersonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //searchView.setQueryHint(getString(R.string.search_elderly));
        activityLabel.setText(R.string.elderly);
    }

    @Override
    public CollectionReference getCollectionRef() {
        return FirebaseFirestore.getInstance().collection("Elderly");
    }

    @Override
    Person getPerson(DocumentChange dc) {
        return dc.getDocument().toObject(Elderly.class);
    }

    @Override
    Intent getProfileIntent(Person person) {
        Intent intent= new Intent(this, ElderlyProfileActivity.class);
        intent.putExtra(EXTRA_PERSON, person);
        return intent;
    }

    @Override
    Intent getRegistrationIntent() {
        return  new Intent(this, ElderlyRegistrationActivity.class);
    }
}
