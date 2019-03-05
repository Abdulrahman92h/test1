package com.example.ayatbahaa.waveapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;

public class AboutActivity extends AppCompatActivity {
    private LinearLayout mlisl;
    private LinearLayout mlis2;
    private LinearLayout mlis4;

    private View img;
    private View text;
    private View img2;
    private View text2;
    private View img3;
    private View text3;
    private Toolbar toolbarlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        toolbarlogin=findViewById(R.id.sidebarlogin_id);
        setSupportActionBar(toolbarlogin);
        toolbarlogin.setNavigationIcon(R.drawable.ic_reply);
        toolbarlogin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AboutActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        text = findViewById(R.id.tv1);
        img = findViewById(R.id.img);
        mlisl = findViewById(R.id.rel2);
        mlis2 = findViewById(R.id.rel3);
        mlis4 = findViewById(R.id.rel4);
        text2 = findViewById(R.id.tv2);
        img2 = findViewById(R.id.img2);
        text3 = findViewById(R.id.tv3);
        img3 = findViewById(R.id.img3);
        mlisl.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AboutActivity.this, CorprateActivity.class);
                Pair<View, String>[] pairs = new Pair[2];
                pairs[0] = new Pair<>(text, "descTransition");
                pairs[1] = new Pair<>(img, "imageTransition");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AboutActivity.this, pairs);
                startActivity(i, options.toBundle());
            }
        });
        mlis2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AboutActivity.this,TeamActivity .class);
                Pair<View, String>[] pairs = new Pair[2];
                pairs[0] = new Pair<>(text2, "descTransition2");
                pairs[1] = new Pair<>(img2, "imageTransition2");
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AboutActivity.this, pairs);
                startActivity(i, options.toBundle());
            }
        });
        mlis4.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AboutActivity.this, ProjectsActivity.class);
                Pair<View, String>[] pairs = new Pair[2];
                pairs[0] = new Pair<>(text3, "descTransition3");
                pairs[1] = new Pair<>(img3, "imageTransition3");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AboutActivity.this, pairs);
                startActivity(i, options.toBundle());
            }
        });

    }
}
