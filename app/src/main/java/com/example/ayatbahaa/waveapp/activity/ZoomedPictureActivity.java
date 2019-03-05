package com.example.ayatbahaa.waveapp.activity;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ayatbahaa.waveapp.PersonActivity;
import com.example.ayatbahaa.waveapp.R;
import com.example.ayatbahaa.waveapp.database.Person;

public class ZoomedPictureActivity extends AppCompatActivity implements View.OnClickListener {
    Person mPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoomed_picture);

        View mainLayout = findViewById(R.id.mainLayout);
        final ImageView personalImage = findViewById(R.id.personalImage);
        mainLayout.setOnClickListener(this);

        mPerson = getIntent().getParcelableExtra(PersonActivity.EXTRA_PERSON);

        if (mPerson.getPictureUri() != null) {
            Glide
                    .with(this)
                    .load(mPerson.getPictureUri())
                    .into(personalImage);
        } else {
            Glide
                    .with(ZoomedPictureActivity.this.getApplicationContext())
                    .load(mPerson.getPictureUrl())
                    .into(personalImage);
        }
    }

    @Override
    public void onClick(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }
}
