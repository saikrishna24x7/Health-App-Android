package com.example.saikrishna.healthapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.saikrishna.healthapplication.R;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbarLogin);
        setSupportActionBar(toolbar);
        setTitle(R.string.title);

        Button btnLoginDoctor = findViewById(R.id.button);
        btnLoginDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,DoctorLoginActivity.class);
                startActivity(intent);
            }
        });
        Button btnLoginSpecialist = findViewById(R.id.button2);
        btnLoginSpecialist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SpecialistLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
