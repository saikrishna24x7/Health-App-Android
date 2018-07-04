package com.example.saikrishna.healthapplication.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.saikrishna.healthapplication.R;

public class SpecialistScreenActivity extends Activity {

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

    }
}
