package com.example.ayatbahaa.waveapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ProjectsActivity extends AppCompatActivity {
    private Toolbar toolbarlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        toolbarlogin=findViewById(R.id.sidebarlogin_id);
        setSupportActionBar(toolbarlogin);
        toolbarlogin.setNavigationIcon(R.drawable.ic_reply);
        toolbarlogin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProjectsActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });
    }
}
