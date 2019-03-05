package com.example.ayatbahaa.waveapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;

public class Donate1Activity extends AppCompatActivity {
    private Toolbar toolbarlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate1);


        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width=displayMetrics.widthPixels;
        int hight=displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(hight*.6));
    }

    protected void phone(View v) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Donate1Activity.this,new String[]{Manifest.permission.CALL_PHONE},1);
        }
        Intent cal = new Intent(Intent.ACTION_CALL);
        cal.setData(Uri.parse("tel:07710 754 217"));
        startActivity(cal);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){

            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                Object v = null;
                phone(null);

            }
        }
    }

}
