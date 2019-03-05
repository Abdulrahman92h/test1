package com.example.ayatbahaa.waveapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.ayatbahaa.waveapp.activity.ElderlyRegistrationActivity;
import com.example.ayatbahaa.waveapp.activity.OrphanRegistrationActivity;
import com.example.ayatbahaa.waveapp.activity.PersonRegistrationActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LogupActivity extends AppCompatActivity {
    private Toolbar toolbarlogin;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logup);

        mFirebaseAuth= FirebaseAuth.getInstance();

        /*DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width=displayMetrics.widthPixels;
        int hight=displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(hight*.6));*/

        toolbarlogin=findViewById(R.id.sidebarlogin_id);
        setSupportActionBar(toolbarlogin);
    }

    public void logout(View view) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.sign_out)
                .setMessage(getString(R.string.do_you_want_sign_out))
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mFirebaseAuth.signOut();
                        finish();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void addorphans(View view) {
        Intent intent = new Intent(LogupActivity.this, OrphanRegistrationActivity.class);
        startActivity(intent);
    }

    public void addelderly(View view) {
        Intent intent = new Intent(LogupActivity.this, ElderlyRegistrationActivity.class);
        startActivity(intent);
    }

    public void addnews(View view) {
        Intent intent = new Intent(LogupActivity.this,AddNewsActivity.class);
        startActivity(intent);
    }
}
