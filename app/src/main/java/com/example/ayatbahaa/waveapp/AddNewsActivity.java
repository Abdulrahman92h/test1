package com.example.ayatbahaa.waveapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.net.Uri;

import com.example.ayatbahaa.waveapp.database.DBSchema;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddNewsActivity extends AppCompatActivity {
    private EditText editTextname;
    private EditText editTextdescription;
    private ImageView addimageView;
    private static final int pick_imag_req=1;
    private Uri Imageuri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private DatabaseReference mDatabase;
    private Button add_info;
    private FirebaseFirestore mFirestore;
    private DocumentReference mNewsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        mFirestore= FirebaseFirestore.getInstance();
        mNewsRef= mFirestore.collection("News").document();

        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        editTextname=findViewById(R.id.edit_text_name);
        editTextdescription=findViewById(R.id.edit_text_description);
        addimageView=findViewById(R.id.add_image);
        add_info=findViewById(R.id.btn_add);
/////////// Add image Form device ..................
        addimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openfile();
            }
        });

////////// Button UpLoad File To Data Base..................
        add_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Imageuri !=null) {
                    final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(Imageuri));
                    fileReference.putFile(Imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(AddNewsActivity.this,"Upload successful",Toast.LENGTH_LONG).show();
                                    Upload upload=new Upload(editTextname.getText().toString().trim(),uri.toString(),editTextdescription.getText().toString().trim());
                                    //String uploadid=databaseReference.push().getKey();
                                    //databaseReference.child(uploadid).setValue(upload);
                                    mNewsRef.set(upload, SetOptions.merge());

                                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                    Bitmap bitmap = BitmapFactory.decodeResource(AddNewsActivity.this.getResources(),R.drawable.ic_logo);
                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(AddNewsActivity.this)
                                            .setContentTitle("Iraqi House Foundation for Creativity")
                                            .setSmallIcon(R.drawable.ic_logo)
                                            .setLargeIcon(bitmap)
                                            .setAutoCancel(true)
                                            .setNumber(1);

                                    builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
                                    builder.setVibrate(new long[]{500,1000,500,1000,500});
                                    builder.setSound(Uri.parse("android.resource ://" +getPackageName() + "/" + R.raw.sound ));

                                    notificationManager.notify(1,builder.build());
                                }
                            });
                            Intent intent=new Intent(AddNewsActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddNewsActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    Toast.makeText(AddNewsActivity.this,"No Image selected",Toast.LENGTH_SHORT).show();

                }
                ///////////////

            }
        });




    }


///Open File................

    public void openfile(){
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,pick_imag_req);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== pick_imag_req && resultCode==RESULT_OK && data!=null&&data.getData()!=null){

            Imageuri=data.getData();
            Picasso.get().load(Imageuri).into(addimageView);

        }
    }
    //// UpLoad File ......................................
    private String getFileExtension (Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

    }
}
