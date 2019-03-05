package com.example.ayatbahaa.waveapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ayatbahaa.waveapp.activity.OrphanProfileActivity;
import com.example.ayatbahaa.waveapp.activity.PersonProfileActivity;
import com.example.ayatbahaa.waveapp.activity.OrphanRegistrationActivity;
import com.example.ayatbahaa.waveapp.database.Orphan;
import com.example.ayatbahaa.waveapp.database.Person;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;


public class OrphansActivity extends PersonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //searchView.setQueryHint(getString(R.string.search_orphans));
        activityLabel.setText(R.string.orphans);
    }

    @Override
    public CollectionReference getCollectionRef() {
        return FirebaseFirestore.getInstance().collection("Orphans");
    }

    @Override
    Person getPerson(DocumentChange dc) {
        return dc.getDocument().toObject(Orphan.class);
    }

    @Override
    Intent getProfileIntent(Person person) {
        Intent intent= new Intent(this, OrphanProfileActivity.class);
        intent.putExtra(EXTRA_PERSON, person);

        return intent;
    }

    @Override
    Intent getRegistrationIntent() {
        return  new Intent(this, OrphanRegistrationActivity.class);
    }
}