package com.example.saikrishna.healthapplication.activity;

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

public class SpecialistViewRequestActivity extends AppCompatActivity {
    ImageView imgPatientImage1, imgPatientImage2, imgPatientImage3;
    EditText txtComment;
    Button btnSendToPatient, btnReturnToRecords, btnLogout;
    String specialistName, patientId;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist_view_request);
        init();
    }

    private void init(){

        Toolbar toolbar = findViewById(R.id.toolbarSpecialistViewRequest);
        setSupportActionBar(toolbar);
        setTitle(R.string.title);

        specialistName = getIntent().getExtras().getString("specialistName");
        patientId = getIntent().getExtras().getString("patientId");
        position = getIntent().getExtras().getInt("position");
        imgPatientImage1 = findViewById(R.id.patientimage1);
        imgPatientImage2 = findViewById(R.id.patientimage2);
        imgPatientImage3 = findViewById(R.id.patientimage3);

        final ProgressDialog dialog = new ProgressDialog(SpecialistViewRequestActivity.this);
        dialog.setMessage("Processing...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        try {
            String url = getResources().getString(R.string.base_url) + getResources().getString(R.string.view_specialist_requests);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("specialistName", specialistName);
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
                        if (patientReportObject.getString("image1") != null){
                            byte[] image = Base64.decode(patientReportObject.getString("image1"), 0);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                            imgPatientImage1.setImageBitmap(bitmap);
                        }
                        if (patientReportObject.getString("image2") != null){
                            byte[] image = Base64.decode(patientReportObject.getString("image2"), 0);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                            imgPatientImage2.setImageBitmap(bitmap);
                        }
                        if (patientReportObject.getString("image3") != null){
                            byte[] image = Base64.decode(patientReportObject.getString("image3"), 0);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                            imgPatientImage3.setImageBitmap(bitmap);
                        }
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

        txtComment = findViewById(R.id.commentSpecialist);
        btnSendToPatient = findViewById(R.id.sendSpecialist);
        btnSendToPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendComment();
            }
        });

        btnReturnToRecords = findViewById(R.id.returnToRecordsSpecialist);
        btnReturnToRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SpecialistViewRequestActivity.this,SpecialistRequestsActivity.class);
                intent.putExtra("specialistName",specialistName);
                startActivity(intent);
            }
        });

        btnLogout = findViewById(R.id.logoutSpecialistViewRequest);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SpecialistViewRequestActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void sendComment(){
        String comment = txtComment.getText().toString();
        final ProgressDialog dialog = new ProgressDialog(SpecialistViewRequestActivity.this);
        dialog.setMessage("Processing...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        try {
            String url = getResources().getString(R.string.base_url) + getResources().getString(R.string.send_comments);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("commentorName", specialistName);
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
                        Toast.makeText(SpecialistViewRequestActivity.this, "Comments sent to patient", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SpecialistViewRequestActivity.this,DoctorScreenActivity.class);
                        intent.putExtra("specialistName",specialistName);
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
