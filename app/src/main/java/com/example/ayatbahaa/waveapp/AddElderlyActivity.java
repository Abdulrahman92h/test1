package com.example.ayatbahaa.waveapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddElderlyActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private static final String ke1 ="fullname";
    private static final String ke2 = "IDNumber";
    private static final String ke3 = "Telephonenumber";
    private static final String ke4 = "address";
    private static final String ke5 = "placeofbirth";
    private static final String ke6 = "dateofbirth";
    private static final String ke7 = "Socialstatus";
    private static final String ke8 = "Healthstatus";

//////////////////////////////////////////////////////////////////////

    private ImageView img;
    private ImageView img2;
    private EditText etname;
    private EditText etid;
    private EditText ettp;
    private EditText etad;
    private EditText etpob;
    private EditText etdob;
    private EditText etss;
    private EditText eths;
    private StorageReference mstore;
    private Uri imguri;
    private ProgressDialog pd;

//////////////////////////////////////////////////////////////////////////////




    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_elderly);
        img = findViewById(R.id.img1);
        etname = findViewById(R.id.edit_text_name);
        etid = findViewById(R.id.edit_text_id);
        ettp = findViewById(R.id.edit_text_tp);
        etad = findViewById(R.id.edit_text_ad);
        etpob = findViewById(R.id.edit_text_place);
        etdob = findViewById(R.id.edit_text_db);
        etss = findViewById(R.id.edit_text_s);
        eths = findViewById(R.id.edit_text_h);
        pd = new ProgressDialog(this);
        mstore = FirebaseStorage.getInstance().getReference();
    }

    public void SaveData(View view) {
        String fullname = etname.getText().toString();
        String IDNumber = etid.getText().toString();
        String Telephonenumber = ettp.getText().toString();
        String address = etad.getText().toString();
        String placeofbirth = etpob.getText().toString();
        String dateofbirth = etdob.getText().toString();
        String Socialstatus = etss.getText().toString();
        String Healthstatus = eths.getText().toString();

////////////////////////////////////////////////////////////////////////////////////////////


        Map<String, Object> note = new HashMap<>();
        note.put(ke1,fullname);
        note.put(ke2, IDNumber);
        note.put(ke3,Telephonenumber);
        note.put(ke4, address);
        note.put(ke5, placeofbirth);
        note.put(ke6, dateofbirth);
        note.put(ke7, Socialstatus);
        note.put(ke8, Healthstatus);


////////////////////////////////////////////////////////////////////////////////////
        db.collection("Elderly").document("addElderly").set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddElderlyActivity.this, "saved !", Toast.LENGTH_SHORT).show();
                        upload();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddElderlyActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }

    public void choose(View view) {
        Intent gallary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallary, 200);




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && requestCode == 200 && data != null) {
            Uri imgeruri = data.getData();
            img.setImageURI(imgeruri);
            imguri = imgeruri;
            Toast.makeText(getApplicationContext(), imgeruri.toString(), Toast.LENGTH_LONG).show();
        }


    }
    public void upload() {
        Calendar calendar = Calendar.getInstance();
        pd.setMessage("wait to load photo");
        pd.show();
        StorageReference fillpath = mstore.child("Photos").child("img_" + calendar.getTimeInMillis());
        fillpath.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Toast.makeText(AddElderlyActivity.this, "image uploaded", Toast.LENGTH_LONG).show();
                Picasso.get().load(taskSnapshot.getUploadSessionUri()).into(img2);

            }
        });


    }
}
