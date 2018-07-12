package com.example.saikrishna.healthapplication.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.saikrishna.healthapplication.R;
import com.example.saikrishna.healthapplication.adapters.DoctorRequestsAdapter;
import com.example.saikrishna.healthapplication.adapters.SpecialistRequestsAdapter;
import com.example.saikrishna.healthapplication.models.PatientReport;
import com.example.saikrishna.healthapplication.service.ServiceApiCallback;
import com.example.saikrishna.healthapplication.service.ServiceApiClient;
import com.example.saikrishna.healthapplication.utils.DividerItemDecoration;
import com.example.saikrishna.healthapplication.utils.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SpecialistRequestsActivity extends Activity {

    List<PatientReport> reportList = new ArrayList<>();

    RecyclerView requestRecyclerView;
    SpecialistRequestsAdapter mAdapter;
    String specialistName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist_requests);
        init();
    }

    private void init(){
        specialistName = getIntent().getExtras().getString("specialistName");
        requestRecyclerView = findViewById(R.id.recyclerViewSpecialistRequests);
        mAdapter = new SpecialistRequestsAdapter(reportList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        requestRecyclerView.setLayoutManager(mLayoutManager);
        requestRecyclerView.setItemAnimator(new DefaultItemAnimator());
        requestRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        requestRecyclerView.setAdapter(mAdapter);
        requestRecyclerView.invalidate();

        requestRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), requestRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(SpecialistRequestsActivity.this, SpecialistViewRequestActivity.class);
                PatientReport report = reportList.get(position);
                i.putExtra("specialistName", report.getSpecialistName());
                i.putExtra("patientId", report.getPatientID());
                i.putExtra("position", position);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        final ProgressDialog dialog = new ProgressDialog(SpecialistRequestsActivity.this);
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
                        dialog.hide();
                        JSONArray dataArray = respObject.getJSONArray("patientReports");
                        for (int i=0;i<dataArray.length();i++){
                            JSONObject object = dataArray.getJSONObject(i);
                            PatientReport report = new PatientReport();
                            report.setSpecialistName(object.getString("specialistName"));
                            report.setPatientData(object.getString("patientData"));
                            report.setPatientID(object.getString("patientID"));
                            byte[] image = Base64.decode(object.getString("image"), 0);
                            report.setImage(image);
                            reportList.add(report);
                        }
                        mAdapter.notifyDataSetChanged();
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
