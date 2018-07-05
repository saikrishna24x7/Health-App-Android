package com.example.saikrishna.healthapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.saikrishna.healthapplication.R;

public class DoctorScreenActivity extends Activity {

    Button btnViewRequests;
    String doctorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_screen);
        init();
    }

    private void init(){

        doctorName = getIntent().getExtras().getString("doctorName");

        btnViewRequests = findViewById(R.id.viewrequest);
        btnViewRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorScreenActivity.this, DoctorViewRequestsActivity.class);
                intent.putExtra("doctorName", doctorName);
                startActivity(intent);
            }
        });
    }
}
