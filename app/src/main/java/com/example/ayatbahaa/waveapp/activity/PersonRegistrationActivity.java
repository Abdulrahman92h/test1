package com.example.ayatbahaa.waveapp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ayatbahaa.waveapp.R;
import com.example.ayatbahaa.waveapp.database.Orphan;
import com.example.ayatbahaa.waveapp.database.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.threeten.bp.DateTimeException;
import org.threeten.bp.LocalDate;

import java.util.HashMap;
import java.util.Map;

abstract public class PersonRegistrationActivity extends AppCompatActivity {
    private static final String TAG = "RegistrationActivity";
    private static final int RC_IMAGE = 42;
    private static final int RC_PERMISSION_READ_STORAGE = 0;
    private static final String IMAGE_URI_STATE = "savedImageUriState";
    public static final String EXTRA_DOCUMENT_ID = "extraDocumentId";
    public static final String EXTRA_PICTURE_URI = "extraPictureUri";

    private EditText mName;
    private EditText mPlaceOFBirth;
    private EditText mEducation;
    private EditText mDiseases;
    private EditText mDay;
    private EditText mMonth;
    private EditText mYear;
    private EditText mDescription;
    private CheckBox mIsMarried;
    private Spinner mGender;
    private ImageView mPersonalImage;
    private StorageReference mStorageReference;
    DocumentReference personDocument;
    private TextView mBackgroundLabel;
    private Uri mPictureUri;
    private UploadTask mUploadTask;
    Person person;
    private boolean mIsNewDocument;
    private boolean mIsLegal;

    abstract CollectionReference getPersonCollection();

    abstract StorageReference getStorageRef();

    abstract Intent getRegistrationIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mName = findViewById(R.id.name);
        mPlaceOFBirth = findViewById(R.id.placeOfBirth);
        mEducation = findViewById(R.id.education);
        mIsMarried = findViewById(R.id.married);
        mDiseases = findViewById(R.id.diseases);
        mDescription = findViewById(R.id.description);
        mGender = findViewById(R.id.gender);
        mDay = findViewById(R.id.day);
        mMonth = findViewById(R.id.month);
        mYear = findViewById(R.id.year);
        mPersonalImage = findViewById(R.id.personalImage);
        mBackgroundLabel = findViewById(R.id.uploadImageLabel);


        if (savedInstanceState != null) {
            mPictureUri = savedInstanceState.getParcelable(IMAGE_URI_STATE);
            Glide.with(PersonRegistrationActivity.this).load(mPictureUri).into(mPersonalImage);
        }


        if (getIntent().getStringExtra(EXTRA_DOCUMENT_ID) == null) {
            mIsNewDocument = true;
            // creating new orphan data
            mBackgroundLabel.setVisibility(View.VISIBLE);
            personDocument = getPersonCollection().document();


        } else {
            // editing old orphan data
            String documentId = getIntent().getStringExtra(EXTRA_DOCUMENT_ID);
            personDocument = getPersonCollection().document(documentId);

            if (getIntent().getParcelableExtra(EXTRA_PICTURE_URI) != null) {
                mPictureUri = getIntent().getParcelableExtra(EXTRA_PICTURE_URI);
            }
            getOldData();
        }

        mStorageReference = getStorageRef().child(personDocument.getId());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_person_registration, menu);
        menu.getItem(0).setVisible(true);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addPersonMenuItem:
                uploadAndFinish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void uploadAndFinish() {

        if (!extractDataIntoObject()) return;

        personDocument.set(person)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "upload-successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "upload-failed");
                    }
                });


        if (getIntent().getStringExtra(EXTRA_DOCUMENT_ID) == null) {
            Log.d(TAG, "Before-Intent");
            startActivity(getRegistrationIntent());
        }

        sendUri();
        mIsLegal = true;
        finish();
    }

    boolean extractDataIntoObject() {
        String temp = mName.getText().toString().trim();
        if (!temp.isEmpty()) {
            person.setName(temp);
        }

        temp = mPlaceOFBirth.getText().toString().trim();
        if (!temp.isEmpty()) {
            person.setPlaceOfBirth(temp);
        }


        String dayString = mDay.getText().toString();
        String monthString = mMonth.getText().toString();
        String yearString = mYear.getText().toString();


        if (!dayString.isEmpty() &&
                !monthString.isEmpty() &&
                !yearString.isEmpty()) {

            if (validateDate(dayString, monthString, yearString)) {
                person.setBirthday(dayString + "/" + monthString + "/" + yearString);
            } else {
                return false;
            }
        }


        temp = mGender.getSelectedItem().toString().trim();
        person.setGender(temp);

        person.setMarried(mIsMarried.isChecked());

        temp = mEducation.getText().toString().trim();
        if (!temp.isEmpty()) {
            person.setEducation(temp);
        }


        temp = mDiseases.getText().toString().trim();
        if (!temp.isEmpty()) {
            person.setDiseases(temp);
        }

        temp = mDescription.getText().toString().trim();
        if (!temp.isEmpty()) {
            person.setDescription(temp);
        }
        return true;
    }


    void getOldData() {

        if (person.getName() != null && !person.getName().isEmpty()) {
            mName.setText(person.getName());
        }
        if (person.getPlaceOfBirth() != null && !person.getPlaceOfBirth().isEmpty()) {
            mPlaceOFBirth.setText(person.getPlaceOfBirth());
        }

        if (person.getEducation() != null && !person.getEducation().isEmpty()) {
            mEducation.setText(person.getEducation());
        }

        if (person.getDescription() != null && !person.getDescription().isEmpty()) {
            mDescription.setText(person.getDescription());
        }

        if (person.getDiseases() != null && !person.getDiseases().isEmpty()) {
            mDiseases.setText(person.getDiseases());
        }

        mIsMarried.setChecked(person.isMarried());

        if (person.getBirthday() != null) {
            String[] parts = person.getBirthday().split("/");
            mDay.setText(parts[0]);
            mMonth.setText(parts[1]);
            mYear.setText(parts[2]);
        }

        if (person.getGender() != null && person.getGender().equals("Female")) {
            mGender.setSelection(1);
        }

        mBackgroundLabel.setVisibility(View.VISIBLE);
        if (mPictureUri != null) {
            Glide.with(PersonRegistrationActivity.this).load(mPictureUri).into(mPersonalImage);
        } else if (person.getPictureUrl() != null) {
            mBackgroundLabel.setText(R.string.loading);

            Glide
                    .with(PersonRegistrationActivity.this.getApplicationContext())
                    .load(person.getPictureUrl())
                    .into(mPersonalImage);
        }
    }

    private boolean validateDate(String dayString, String monthString, String yearString) {

        int day = Integer.parseInt(dayString);
        int month = Integer.parseInt(monthString);
        int year = Integer.parseInt(yearString);

        boolean isValid = true;

        if (year < 1900 || year > 9999) {
            mYear.setError(getString(R.string.invalid_year));
            isValid = false;
        }

        try {
            // LocalDate used to validate day, month, year
            LocalDate.of(year, month, day);

        } catch (DateTimeException e) {
            if (e.getMessage().contains("DayOfMonth")) {
                mDay.setError(getString(R.string.invalid_day));

            } else if (e.getMessage().contains("MonthOfYear")) {
                mMonth.setError(getString(R.string.invalid_month));
            }
            Log.d(TAG, e.getMessage());
            isValid = false;
        }
        return isValid;
    }

    public void pickImage(View v) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    RC_PERMISSION_READ_STORAGE);

            return;
        }

        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        startActivityForResult(photoPicker, RC_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_IMAGE && resultCode == Activity.RESULT_OK) {
            mPictureUri = data.getData();
            uploadImage();
        }
    }

    private void uploadImage() {
        mPersonalImage.setImageResource(0);

        if (mPictureUri != null) {
            findViewById(R.id.uploadImageLabel).setVisibility(View.GONE);
            findViewById(R.id.uploadingLayout).setVisibility(View.VISIBLE);
            final ProgressBar uploadProgress = findViewById(R.id.progressBar);
            final TextView percentageText = findViewById(R.id.percentageText);

            mUploadTask = mStorageReference.putFile(mPictureUri);
            mUploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    int percentage = (int) (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    uploadProgress.setProgress(percentage);
                    percentageText.setText(getResources().getString(R.string.percentage, percentage));
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                Glide.with(PersonRegistrationActivity.this).load(mPictureUri).into(mPersonalImage);

                                mStorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(final Uri uri) {
                                        person.setPictureUrl(uri.toString());
                                        personDocument.set(person);

                                        Glide
                                                .with(PersonRegistrationActivity.this)
                                                .downloadOnly()
                                                .load(uri.toString())
                                                .submit();
                                    }
                                });
                            } else {
                                mPersonalImage = null;
                            }

                        }
                    });
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            if (requestCode == RC_PERMISSION_READ_STORAGE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage(null);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(IMAGE_URI_STATE, mPictureUri);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!mIsLegal) {
            cleanupOperation();
        }
    }

    private void cleanupOperation() {
        if (mUploadTask != null) {
            if (mUploadTask.isInProgress()) {
                mUploadTask.cancel();
            } else if (mUploadTask.isComplete()) {
                // delete photo from storage and delete the link from firestore
                mStorageReference.delete();

                if (mIsNewDocument) {
                    personDocument.delete();
                } else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("pictureUrl", FieldValue.delete());
                    personDocument.set(data, SetOptions.merge());
                }
            }
            mPictureUri = null;
        }
    }

    @Override
    public void onBackPressed() {
        // notify parent activity to refresh recycler view item
        // must be called here because onActivityResult gets called before onStop()
        setResult(Activity.RESULT_OK);
        super.onBackPressed();
    }

    private void sendUri() {
        Intent data = new Intent();
        data.putExtra(EXTRA_PICTURE_URI, mPictureUri);
        setResult(Activity.RESULT_OK, data);
    }
}
