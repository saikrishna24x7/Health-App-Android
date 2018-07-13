package com.example.saikrishna.healthapplication.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.saikrishna.healthapplication.R;
import com.example.saikrishna.healthapplication.adapters.PatientCommentsAdapter;
import com.example.saikrishna.healthapplication.service.ServiceApiCallback;
import com.example.saikrishna.healthapplication.service.ServiceApiClient;
import com.example.saikrishna.healthapplication.utils.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PatientCommentsActivity extends AppCompatActivity {

    RecyclerView dataRecyclerView;
    PatientCommentsAdapter mAdapter;
    List<String> commentsList = new ArrayList<>();
    String patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_comments);
        init();
    }

    private void init(){

        Toolbar toolbar = findViewById(R.id.toolbarPatientComments);
        setSupportActionBar(toolbar);
        setTitle(R.string.title);

        patientId = getIntent().getExtras().getString("patientId");

        dataRecyclerView = findViewById(R.id.recyclerViewPatientRecords);
        mAdapter = new PatientCommentsAdapter(commentsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        dataRecyclerView.setLayoutManager(mLayoutManager);
        dataRecyclerView.setItemAnimator(new DefaultItemAnimator());
        dataRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        dataRecyclerView.setAdapter(mAdapter);
        dataRecyclerView.invalidate();

        final ProgressDialog dialog = new ProgressDialog(PatientCommentsActivity.this);
        dialog.setMessage("Processing...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        try{
            String url = getResources().getString(R.string.base_url) + getResources().getString(R.string.view_comments);
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
                        JSONArray dataArray = respObject.getJSONArray("comments");
                        for(int i=0;i<dataArray.length();i++){
                            JSONObject object = dataArray.getJSONObject(i);
                            commentsList.add("Comment from "+object.getString("commentorName")+":"+object.getString("comment"));
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
            e.printStackTrace();
        }

    }
}
