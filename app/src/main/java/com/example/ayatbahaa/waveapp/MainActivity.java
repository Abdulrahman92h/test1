package com.example.ayatbahaa.waveapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout menuDrawableLayout;
    private ActionBarDrawerToggle menuToggle;
    private List<Upload> mUploads;
    private Toolbar toolbar;
    private FirebaseAuth mFirebaseAuth;
    private CollectionReference mNewsCollection;
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);


        mFirebaseAuth= FirebaseAuth.getInstance();
        menuDrawableLayout=findViewById(R.id.drawerLayout_id);
        toolbar =findViewById(R.id.nav_bar_id);
        setSupportActionBar(toolbar);
        menuToggle=new ActionBarDrawerToggle(this,menuDrawableLayout,R.string.open,R.string.close);
        menuDrawableLayout.addDrawerListener(menuToggle);
        menuToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNewsCollection= FirebaseFirestore.getInstance().collection("News");
        mRecyclerView=findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUploads=new ArrayList<>();

        mNewsCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    Upload upload= documentSnapshot.toObject(Upload.class);
                    mUploads.add(upload);
                }
                mAdapter=new ImageAdapter(MainActivity.this,mUploads);
                mRecyclerView.setAdapter(mAdapter);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return menuToggle.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void news(MenuItem item) {
        Intent orphansIntent = new Intent(MainActivity.this,MainActivity.class);
        startActivity(orphansIntent);
    }
    public void orphans(MenuItem item) {
        Intent orphansIntent = new Intent(MainActivity.this,OrphansActivity.class);
        startActivity(orphansIntent);
    }

    public void elderly(MenuItem item) {
        Intent elderlyIntent = new Intent(MainActivity.this,ElderlyActivity.class);
        startActivity(elderlyIntent);
    }

    public void donate(MenuItem item) {
        Intent donateIntent = new Intent(MainActivity.this,Donate1Activity.class);
        startActivity(donateIntent);
    }

    public void contact(MenuItem item) {
        Intent contactIntent = new Intent(MainActivity.this,ContactActivity.class);
        startActivity(contactIntent);
    }

    public void about(MenuItem item) {
        Intent aboutIntent = new Intent(MainActivity.this,AboutActivity.class);
        startActivity(aboutIntent);
    }

    public void login(MenuItem item) {
        Intent loginIntent;
        if(mFirebaseAuth.getCurrentUser() != null) {
            loginIntent = new Intent(MainActivity.this,LogupActivity.class);
        }else {
            loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        }

        startActivity(loginIntent);
    }

}
