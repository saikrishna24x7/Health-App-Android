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
    ImageView imgPatientImage;
    EditText txtComment;
    Button btnSendToPatient;
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
        imgPatientImage = findViewById(R.id.patientimage);

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

        txtComment = findViewById(R.id.commentSpecialist);
        btnSendToPatient = findViewById(R.id.sendSpecialist);
        btnSendToPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendComment();
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
