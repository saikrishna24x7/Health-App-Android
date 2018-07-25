package com.example.saikrishna.healthapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.saikrishna.healthapplication.R;

public class SpecialistScreenActivity extends AppCompatActivity {

    Button btnViewRequests, btnLogout;
    String specialistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist_screen);
//        NewRelic.withApplicationToken(
//                "AAecfdf3761f78935f1fcbd572534c3eab30d49b23"
//        ).start(this.getApplication());

        init();
    }

    private void init(){

        Toolbar toolbar = findViewById(R.id.toolbarSpecialistScreen);
        setSupportActionBar(toolbar);
        setTitle(R.string.title);

        specialistName = getIntent().getExtras().getString("specialistName");
        btnViewRequests = findViewById(R.id.viewrequestSpecialist);
        btnViewRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SpecialistScreenActivity.this, SpecialistRequestsActivity.class);
                i.putExtra("specialistName", specialistName);
                startActivity(i);
            }
        });

        btnLogout = findViewById(R.id.logoutSpecialist);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SpecialistScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
