package com.example.ayatbahaa.waveapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    final static String TAG = LoginActivity.class.getSimpleName();
    private TextInputEditText Email,Password;
    Button btnLogin;
    private Toolbar toolbarlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
/*
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width=displayMetrics.widthPixels;
        int hight=displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(hight*.6));*/

        toolbarlogin=findViewById(R.id.sidebarlogin_id);
        setSupportActionBar(toolbarlogin);

        mAuth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.email_input);
        Password = findViewById(R.id.password_input);
        btnLogin = findViewById(R.id.btnLogin);


    }

    public void LoginBtn(View view) {
        final String emailtext , passwordtext ;
        emailtext = Email.getText().toString();
        passwordtext = Password.getText().toString();
        mAuth.signInWithEmailAndPassword(emailtext,passwordtext).addOnCompleteListener(this
                , new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent intent = new Intent(LoginActivity.this,LogupActivity.class);
                            //intent.putExtra("Email", emailtext);
                            startActivity(intent);
                            finish();
                        } else {

                            Toast.makeText(LoginActivity.this,R.string.error,Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }
}
