package com.example.saikrishna.healthapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.saikrishna.healthapplication.R;
import com.example.saikrishna.healthapplication.utils.Utils;
import com.newrelic.agent.android.NewRelic;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        NewRelic.withApplicationToken(
//                "AAecfdf3761f78935f1fcbd572534c3eab30d49b23"
//        ).start(this.getApplication());

        init();

    }

    private void init(){

        Button btnPatientLogin = findViewById(R.id.patientlogin);
        btnPatientLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PatientLoginActivity.class);
                startActivity(intent);
            }
        });

        Button btnPatientSignUp = findViewById(R.id.patientsignup);
        btnPatientSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PatientSignUpActivity.class);
                startActivity(intent);
            }
        });

        Button btnDoctorLogin = findViewById(R.id.doctorlogin);
        btnDoctorLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DoctorLoginActivity.class);
                startActivity(intent);
            }
        });

        Button btnDoctorSignUp = findViewById(R.id.doctorsignup);
        btnDoctorSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DoctorSignUpActivity.class);
                startActivity(intent);
            }
        });

        Button btnSpecialistLogin = findViewById(R.id.specialistlogin);
        btnSpecialistLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SpecialistLoginActivity.class);
                startActivity(intent);
            }
        });

        Button btnSpecialistSignUp = findViewById(R.id.specialistsignup);
        btnSpecialistSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SpecialistSignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}