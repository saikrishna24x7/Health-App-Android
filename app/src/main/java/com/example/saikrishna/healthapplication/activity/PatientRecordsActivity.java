package com.example.saikrishna.healthapplication.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.saikrishna.healthapplication.R;
import com.example.saikrishna.healthapplication.adapters.PatientRecordsAdapter;
import com.example.saikrishna.healthapplication.service.ServiceApiCallback;
import com.example.saikrishna.healthapplication.service.ServiceApiClient;
import com.example.saikrishna.healthapplication.utils.DividerItemDecoration;
import com.newrelic.com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PatientRecordsActivity extends Activity {

    ListView list;
    String[] records;
    String patientId;
    List<String> recordsList = new ArrayList<>();

    RecyclerView dataRecyclerView;
    PatientRecordsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_records);
//        NewRelic.withApplicationToken(
//                "AAecfdf3761f78935f1fcbd572534c3eab30d49b23"
//        ).start(this.getApplication());

        init();
    }

    private void init(){

        dataRecyclerView = findViewById(R.id.recyclerViewPatientRecords);
        mAdapter = new PatientRecordsAdapter(recordsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        dataRecyclerView.setLayoutManager(mLayoutManager);
        dataRecyclerView.setItemAnimator(new DefaultItemAnimator());
        dataRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        dataRecyclerView.setAdapter(mAdapter);
        dataRecyclerView.invalidate();

        patientId = getIntent().getExtras().getString("patientId");

        final ProgressDialog dialog = new ProgressDialog(PatientRecordsActivity.this);
        dialog.setMessage("Processing...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        try{
            String url = getResources().getString(R.string.device_data_url);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("patientId", patientId);
            ServiceApiClient.getResponse(getApplicationContext(), url, Request.Method.POST, jsonObject, new ServiceApiCallback()
            {
                @Override
                public void onSuccessResponse(String response)
                {
                    try
                    {
                        JSONObject respObject = new JSONObject(response);
                        dialog.hide();
                        JSONArray dataArray = respObject.getJSONArray("patientData");
                        for(int i=0;i<dataArray.length();i++){
                            recordsList.add(dataArray.getString(i));
                        }
                        mAdapter.notifyDataSetChanged();


//                        PatientRecordsAdapter adapter = new PatientRecordsAdapter(PatientRecordsActivity.this, records);
//                        list = (ListView) findViewById(R.id.list);
//                        list.setAdapter(adapter);
//                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                Intent i = new Intent(PatientRecordsActivity.this,SendToDoctor.class);
//                                i.putExtra("user1", records[+position]);
//                                i.putExtra("user",patientId);
//                                startActivity(i);
//                            }
//                        });

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
            e.printStackTrace();
        }
    }
}
