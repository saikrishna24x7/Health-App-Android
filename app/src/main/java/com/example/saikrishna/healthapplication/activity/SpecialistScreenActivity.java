package com.example.saikrishna.healthapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.saikrishna.healthapplication.R;

public class SpecialistScreenActivity extends Activity {

    Button btnViewRequests;
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
    }
}
