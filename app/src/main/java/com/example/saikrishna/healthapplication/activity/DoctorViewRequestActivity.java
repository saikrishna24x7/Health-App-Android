package com.example.saikrishna.healthapplication.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.saikrishna.healthapplication.R;
import com.example.saikrishna.healthapplication.service.ServiceApiCallback;
import com.example.saikrishna.healthapplication.service.ServiceApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DoctorViewRequestActivity extends AppCompatActivity {

    ImageView imgPatientImage;
    EditText txtComment;
    Button btnSendToPatient, btnSendToSpecialist;
    String doctorName, patientId;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_request);
        init();
    }

    private void init(){

        Toolbar toolbar = findViewById(R.id.toolbarDoctorViewRequest);
        setSupportActionBar(toolbar);
        setTitle(R.string.title);

        doctorName = getIntent().getExtras().getString("doctorName");
        patientId = getIntent().getExtras().getString("patientId");
        position = getIntent().getExtras().getInt("position");
        imgPatientImage = findViewById(R.id.patientimage);

        final ProgressDialog dialog = new ProgressDialog(DoctorViewRequestActivity.this);
        dialog.setMessage("Processing...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        try {
            String url = getResources().getString(R.string.base_url) + getResources().getString(R.string.view_requests);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("doctorName", doctorName);
            ServiceApiClient.getResponse(getApplicationContext(), url, Request.Method.POST, jsonObject, new ServiceApiCallback()
            {
                @Override
                public void onSuccessResponse(String response)
                {
                    try
                    {
                        JSONObject respObject = new JSONObject(response);
                        Log.d("response:", respObject.toString());
                        dialog.hide();
                        JSONArray dataArray = respObject.getJSONArray("patientReports");
                        JSONObject patientReportObject = dataArray.getJSONObject(position);
                        byte[] image = Base64.decode(patientReportObject.getString("image"), 0);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                        imgPatientImage.setImageBitmap(bitmap);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailureResponse(String message)
                {
                    dialog.hide();
                    Toast.makeText(getApplicationContext(), "Error:"+message, Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){

        }
        txtComment = findViewById(R.id.comment);
        btnSendToPatient = findViewById(R.id.send);
        btnSendToPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendComment();
            }
        });

        btnSendToSpecialist = findViewById(R.id.sendToSpecialist);
        btnSendToSpecialist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToSpecialist();
            }
        });
    }

    private void sendComment(){
        String comment = txtComment.getText().toString();
        final ProgressDialog dialog = new ProgressDialog(DoctorViewRequestActivity.this);
        dialog.setMessage("Processing...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        try {
            String url = getResources().getString(R.string.base_url) + getResources().getString(R.string.send_comments);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("commentorName", doctorName);
            jsonObject.put("comment", comment);
            jsonObject.put("patientId", patientId);

            ServiceApiClient.getResponse(getApplicationContext(), url, Request.Method.POST, jsonObject, new ServiceApiCallback()
            {
                @Override
                public void onSuccessResponse(String response)
                {
                    try
                    {
                        JSONObject respObject = new JSONObject(response);
                        Log.d("response:", respObject.toString());
                        dialog.hide();
                        Toast.makeText(DoctorViewRequestActivity.this, "Comments sent to patient", Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(DoctorViewRequestActivity.this,DoctorScreenActivity.class);
//                        intent.putExtra("doctorName",doctorName);
//                        startActivity(intent);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailureResponse(String message)
                {
                    dialog.hide();
                    Toast.makeText(getApplicationContext(), "Error:"+message, Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){

        }
    }

    private void sendToSpecialist(){
        final ProgressDialog dialog = new ProgressDialog(DoctorViewRequestActivity.this);
        dialog.setMessage("Processing...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        try {

            String url = getResources().getString(R.string.base_url) + getResources().getString(R.string.send_to_specialist);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("specialistName", "Vinay");
            jsonObject.put("patientID", patientId);
//            Log.d("json:", jsonObject.toString());
            ServiceApiClient.getResponse(getApplicationContext(), url, Request.Method.POST, jsonObject, new ServiceApiCallback()
            {
                @Override
                public void onSuccessResponse(String response)
                {
                    try
                    {
                        JSONObject respObject = new JSONObject(response);
//                        Log.d("response:", respObject.toString());
                        dialog.hide();
                        Toast.makeText(DoctorViewRequestActivity.this, "Patient Data sent to Specialist", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(DoctorViewRequestActivity.this,DoctorScreenActivity.class);
                        intent.putExtra("doctorName",doctorName);
                        startActivity(intent);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailureResponse(String message)
                {
                    dialog.hide();
                    Toast.makeText(getApplicationContext(), "Error:"+message, Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){

        }
    }
}
