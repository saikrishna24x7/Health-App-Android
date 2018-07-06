package com.example.saikrishna.healthapplication.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.saikrishna.healthapplication.R;
import com.example.saikrishna.healthapplication.models.PatientReport;
import com.example.saikrishna.healthapplication.service.ServiceApiCallback;
import com.example.saikrishna.healthapplication.service.ServiceApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DoctorViewRequestActivity extends Activity {

    ImageView imgPatientImage;
    EditText txtComment;
    Button btnSend;
    String doctorName, patientId;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_request);
        init();
    }

    private void init(){

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
        btnSend = findViewById(R.id.send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendComment();
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
