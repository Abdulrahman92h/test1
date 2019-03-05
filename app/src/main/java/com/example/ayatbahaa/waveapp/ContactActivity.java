package com.example.ayatbahaa.waveapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

public class ContactActivity extends AppCompatActivity {
    private Toolbar toolbarlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        toolbarlogin=findViewById(R.id.sidebarlogin_id);
        setSupportActionBar(toolbarlogin);
        toolbarlogin.setNavigationIcon(R.drawable.ic_reply);
        toolbarlogin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ContactActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout h=(LinearLayout)findViewById(R.id.second);
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdi(R.style.ani,R.layout.uides2);
            }
        });

        // ImageView a12=findViewById(R.id.imageView2);

        LinearLayout a12=(LinearLayout)findViewById(R.id.first);

        a12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdi(R.style.ani,R.layout.uides);
            }
        });


        //ImageView a2=findViewById(R.id.imageView2o0);
        LinearLayout a2=(LinearLayout)findViewById(R.id.third);
        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdi(R.style.ani,R.layout.uides3);
            }
        });
        //ImageView a3=findViewById(R.id.imageView20);
        LinearLayout a3=(LinearLayout)findViewById(R.id.forth);
        a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdi(R.style.ani,R.layout.uides4);
            }
        });
    }

    public void go(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("geo:33.2546288,44.353373?z=15"));

        startActivity(Intent.createChooser(i,"launch Maps"));

        // AnimationUtils.loadAnimation(this,)


    }

    private void showdi(int type, int activity) {

        AlertDialog.Builder di = new AlertDialog.Builder(ContactActivity.this);
        View v = getLayoutInflater().inflate(activity, null);
        di.setView(v);
        AlertDialog hi = di.create();
        hi.getWindow().getAttributes().windowAnimations = type;
        hi.show();


    }


    public void facebook(View view) {
        Intent fac =openface(ContactActivity.this);
        startActivity(fac);


    }
    public static  Intent openface(Context context){

        try{

            context.getPackageManager().getPackageInfo("com.example.nabaa96.task",0);
            return  new Intent(Intent.ACTION_VIEW,Uri.parse("http://iraqhome.org/"));

        }
        catch (Exception e){

            return  new Intent(Intent.ACTION_VIEW,Uri.parse("http://iraqhome.org/"));


        }

    }
    public void webs(View view) {


        Intent browser=new Intent(Intent.ACTION_VIEW,Uri.parse("http://iraqhome.org"));
        startActivity(browser);

    }
    public void email(View view) {
        Intent n=new Intent(Intent.ACTION_SEND);
        n.setData(Uri.parse("mailto:"));
        String [] s={"althahpea@yahoo.com "};
        n.putExtra(Intent.EXTRA_EMAIL,s);
        n.setType("message/rfc822");
        startActivity(Intent.createChooser(n,"send feedback"));


    }
}
