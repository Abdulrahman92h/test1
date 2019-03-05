package com.example.ayatbahaa.waveapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class TeamActivity extends AppCompatActivity {
    private Toolbar toolbarlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        toolbarlogin=findViewById(R.id.sidebarlogin_id);
        setSupportActionBar(toolbarlogin);
        toolbarlogin.setNavigationIcon(R.drawable.ic_reply);
        toolbarlogin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TeamActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });
    }
}
