package com.example.saikrishna.healthapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.saikrishna.healthapplication.R;

public class PatientScreenActivity extends Activity {

    String patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_screen);
//        NewRelic.withApplicationToken(
//                "AAecfdf3761f78935f1fcbd572534c3eab30d49b23"
//        ).start(this.getApplication());

        init();
    }

    private void init(){
        patientId = getIntent().getExtras().getString("patientId");
        Button btnViewSensorData = findViewById(R.id.sensor);
        btnViewSensorData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientScreenActivity.this, PatientRecordsActivity.class);
                intent.putExtra("patientId", patientId);
                startActivity(intent);
            }
        });

        Button btnViewSuggestions = findViewById(R.id.suggestion);
        btnViewSuggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button btnLogout = findViewById(R.id.logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void logout(){

    }
}