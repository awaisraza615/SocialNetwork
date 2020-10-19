package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private Button PublicPostBtn,GlobalPostBtn,ScholarshipPostBtn,CovidButton,TwitterBtn;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        PublicPostBtn=findViewById(R.id.publicpostbutton);
        GlobalPostBtn=findViewById(R.id.globalnewsbtn);
        ScholarshipPostBtn=findViewById(R.id.scholarshipbtn);
        TwitterBtn=findViewById(R.id.twitterBtn);
        CovidButton=findViewById(R.id.Covidbutton);


      /*  fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Dashboard dashboardFragment = new Dashboard();*/

       /* fragmentTransaction.replace(R.id.container, dashboardFragment);
        fragmentTransaction.commit();*/
        PublicPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(in);
              //  finish();
               // Toast.makeText(HomeActivity.this, "button", Toast.LENGTH_SHORT).show();
            }
        });
        GlobalPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(HomeActivity.this,GlobalNewsActivity.class);
                startActivity(in);
              //  finish();
            }
        });
        ScholarshipPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(HomeActivity.this,ScholarshipsActivity.class);
                startActivity(in);
             //   finish();
            }
        });
        TwitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent in = new Intent(HomeActivity.this,TwitterActivity.class);
                startActivity(in);
              //  finish();
            }
        });
        CovidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(HomeActivity.this,CovidActivity.class);
                startActivity(in);
                //  finish();
            }
        });

    }
}