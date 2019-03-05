package com.example.ayatbahaa.waveapp.activity;


import android.os.Bundle;
import android.widget.TextView;

import com.example.ayatbahaa.waveapp.R;
import com.example.ayatbahaa.waveapp.database.Elderly;

public class ElderlyProfileActivity extends PersonProfileActivity {
    private TextView mPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_elderly_profile);
        mPhone= findViewById(R.id.phone);
        super.onCreate(savedInstanceState);
    }

    @Override
    void setData() {
        super.setData();

        Elderly elderly= (Elderly) person;

        if(elderly.getPhone()!= null && !elderly.getPhone().isEmpty()){
            mPhone.setText(elderly.getPhone());
        }
    }
}
