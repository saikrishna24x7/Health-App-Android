package com.example.saikrishna.healthapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.saikrishna.healthapplication.R;

public class RequestViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("in view req1:", "view req1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_request);
//        init();
    }
}
