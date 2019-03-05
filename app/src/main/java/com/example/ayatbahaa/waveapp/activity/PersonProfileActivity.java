package com.example.ayatbahaa.waveapp.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ayatbahaa.waveapp.OrphansActivity;
import com.example.ayatbahaa.waveapp.PersonActivity;
import com.example.ayatbahaa.waveapp.R;
import com.example.ayatbahaa.waveapp.database.Person;

import java.util.Locale;

abstract public class PersonProfileActivity extends AppCompatActivity {
    private TextView mName;
    private TextView mBirthday;
    private TextView mPlaceOfBirth;
    private TextView mMartialStatus;
    private TextView mGender;
    private TextView mEducation;
    private TextView mDiseases;
    private TextView mDescription;
    private TextView mAge;
    private ImageView mPersonalImage;
    private TextView mLoadingImageLabel;
    Person person;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        person = getIntent().getParcelableExtra(PersonActivity.EXTRA_PERSON);

        mName = findViewById(R.id.name);
        mBirthday = findViewById(R.id.birthday);
        mAge = findViewById(R.id.age);
        mPlaceOfBirth = findViewById(R.id.placeOfBirth);
        mMartialStatus = findViewById(R.id.martialStatus);
        mGender = findViewById(R.id.gender);
        mEducation = findViewById(R.id.education);
        mDiseases = findViewById(R.id.diseases);
        mDescription = findViewById(R.id.description);
        mPersonalImage = findViewById(R.id.personalImage);
        mLoadingImageLabel = findViewById(R.id.loadingImageLabel);

       setData();
    }

    void setData() {
        if (person != null) {
            if (person.getName() != null && !person.getName().isEmpty()) {
                mName.setText(person.getName());
            }

            if (person.getBirthday() != null && !person.getBirthday().isEmpty()) {
                mBirthday.setText(person.getBirthday());

                String age;
                if (Locale.getDefault().getDisplayLanguage().equals("ar")) {
                    age = getString(R.string.years_old) + " " + person.getAge();
                } else {
                    age = person.getAge() + " " + getString(R.string.years_old);
                }
                mAge.setText(age);
            }
            if (person.getPlaceOfBirth() != null && !person.getPlaceOfBirth().isEmpty()) {
                mPlaceOfBirth.setText(person.getPlaceOfBirth());
            }

            if (person.getDiseases() != null && !person.getDiseases().isEmpty()) {
                mDiseases.setText(person.getDiseases());
            }

            String martialStatus;
            if (person.isMarried()) {
                martialStatus = getString(R.string.married);
            } else {
                martialStatus = getString(R.string.single);
            }
            mMartialStatus.setText(martialStatus);
            mGender.setText(person.getGender());

            if (person.getEducation() != null && !person.getEducation().isEmpty()) {
                mEducation.setText(person.getEducation());
            }


            if (person.getDescription() != null && !person.getDescription().isEmpty()) {
                mDescription.setText(person.getDescription());
            }

            if (person.getPictureUri() != null) {
                Glide.with(PersonProfileActivity.this).load(person.getPictureUri()).into(mPersonalImage);
            } else if (person.getPictureUrl() != null) {

                Glide
                        .with(PersonProfileActivity.this.getApplicationContext())
                        .load(person.getPictureUrl())
                        .into(mPersonalImage);
            } else {
                mLoadingImageLabel.setText(R.string.no_image);
            }
        }
    }

    public void zoom(View v) {
        if (person.getPictureUri() == null && person.getPictureUrl() == null) {
            return;
        }
        View cardView = findViewById(R.id.imageFrame);
        Intent intent = new Intent(this, ZoomedPictureActivity.class);
        intent.putExtra(PersonActivity.EXTRA_PERSON, person);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Pair<View, String> pair = new Pair<>(cardView, getString(R.string.personal_image_transition));
            ActivityOptions ao = ActivityOptions.makeSceneTransitionAnimation(this, pair);
            startActivity(intent, ao.toBundle());
            return;
        }

        startActivity(intent);
    }
}
