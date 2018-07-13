package com.example.saikrishna.healthapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.saikrishna.healthapplication.R;

public class DoctorScreenActivity extends AppCompatActivity {

    Button btnViewRequests;
    String doctorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_screen);
        init();
    }

    private void init(){

        Toolbar toolbar = findViewById(R.id.toolbarDoctorScreen);
        setSupportActionBar(toolbar);
        setTitle(R.string.title);

        doctorName = getIntent().getExtras().getString("doctorName");

        btnViewRequests = findViewById(R.id.viewrequest);
        btnViewRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorScreenActivity.this, DoctorRequestsActivity.class);
                intent.putExtra("doctorName", doctorName);
                startActivity(intent);
            }
        });
    }
}
