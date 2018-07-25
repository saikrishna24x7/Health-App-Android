package com.example.saikrishna.healthapplication.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.saikrishna.healthapplication.R;
import com.example.saikrishna.healthapplication.adapters.DoctorRequestsAdapter;
import com.example.saikrishna.healthapplication.adapters.PatientRecordsAdapter;
import com.example.saikrishna.healthapplication.models.PatientReport;
import com.example.saikrishna.healthapplication.service.ServiceApiCallback;
import com.example.saikrishna.healthapplication.service.ServiceApiClient;
import com.example.saikrishna.healthapplication.utils.DividerItemDecoration;
import com.example.saikrishna.healthapplication.utils.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DoctorRequestsActivity extends AppCompatActivity {

    List<PatientReport> reportList = new ArrayList<>();

    RecyclerView requestRecyclerView;
    DoctorRequestsAdapter mAdapter;
    String doctorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_requests);
        init();
    }

    private void init(){

        Toolbar toolbar = findViewById(R.id.toolbarDoctorRequests);
        setSupportActionBar(toolbar);
        setTitle(R.string.title);

        doctorName = getIntent().getExtras().getString("doctorName");
        requestRecyclerView = findViewById(R.id.recyclerViewDoctorRequests);
        mAdapter = new DoctorRequestsAdapter(reportList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        requestRecyclerView.setLayoutManager(mLayoutManager);
        requestRecyclerView.setItemAnimator(new DefaultItemAnimator());
        requestRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        requestRecyclerView.setAdapter(mAdapter);
        requestRecyclerView.invalidate();

        requestRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), requestRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(DoctorRequestsActivity.this, DoctorViewRequestActivity.class);
                PatientReport report = reportList.get(position);
                i.putExtra("doctorName", report.getDoctorName());
                i.putExtra("patientId", report.getPatientID());
                i.putExtra("position", position);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        final ProgressDialog dialog = new ProgressDialog(DoctorRequestsActivity.this);
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
//                        Log.d("response:", respObject.toString());
                        dialog.hide();
                        JSONArray dataArray = respObject.getJSONArray("patientReports");
                        for (int i=0;i<dataArray.length();i++){
                            JSONObject object = dataArray.getJSONObject(i);
                            PatientReport report = new PatientReport();
                            report.setDoctorName(object.getString("doctorName"));
                            report.setPatientData(object.getString("patientData"));
                            report.setPatientID(object.getString("patientID"));
                            if (object.getString("image1") != null){
                                byte[] image = Base64.decode(object.getString("image1"), 0);
                                report.setImage1(image);
                            }
                            if (object.getString("image2") != null){
                                byte[] image = Base64.decode(object.getString("image2"), 0);
                                report.setImage2(image);
                            }
                            if (object.getString("image3") != null){
                                byte[] image = Base64.decode(object.getString("image3"), 0);
                                report.setImage3(image);
                            }

                            reportList.add(report);
                        }
                        mAdapter.notifyDataSetChanged();
//                        Log.d("data:", ""+dataArray.length());
//                        JSONObject patientReportObject = dataArray.getJSONObject(0);
//                        byte[] image = Base64.decode(patientReportObject.getString("image"), 0);
//                        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
//                        imgPatientImage.setImageBitmap(bitmap);
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
