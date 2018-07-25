package com.example.saikrishna.healthapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.saikrishna.healthapplication.R;

public class SignupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = findViewById(R.id.toolbarSignup);
        setSupportActionBar(toolbar);
        setTitle(R.string.title);

        Button btnSignupDoctor = findViewById(R.id.button3);
        btnSignupDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this,DoctorSignUpActivity.class);
                startActivity(intent);
            }
        });
        Button btnSignupSpecialist = findViewById(R.id.button4);
        btnSignupSpecialist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this,SpecialistSignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
