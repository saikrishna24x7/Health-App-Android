package com.example.saikrishna.healthapplication.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.saikrishna.healthapplication.R;
import com.example.saikrishna.healthapplication.service.ServiceApiCallback;
import com.example.saikrishna.healthapplication.service.ServiceApiClient;
import com.example.saikrishna.healthapplication.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class PatientSignUpActivity extends Activity {

    EditText txtUsername, txtPassword, txtPatientId, txtEmail, txtContact, txtDoctorName;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_signup);
//        NewRelic.withApplicationToken(
//                "AAecfdf3761f78935f1fcbd572534c3eab30d49b23"
//        ).start(this.getApplication());

        init();
    }

    private void init(){
        txtUsername = findViewById(R.id.username);
        txtPassword = findViewById(R.id.password);
        txtPatientId = findViewById(R.id.patientId);
        txtEmail = findViewById(R.id.email);
        txtContact = findViewById(R.id.contact);
        txtDoctorName = findViewById(R.id.doctorName);
        btnSignUp = findViewById(R.id.signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
    }

    private void signup(){
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        String patientId = txtPatientId.getText().toString();
        String email = txtEmail.getText().toString();
        String contact = txtContact.getText().toString();
        String doctorName = txtDoctorName.getText().toString();

        if(username.trim().length() == 0 ||username == null){
            Toast.makeText(PatientSignUpActivity.this, "Please enter username", Toast.LENGTH_LONG).show();
            txtUsername.requestFocus();
            return;
        }
        if(password.trim().length() == 0 ||password == null){
            Toast.makeText(PatientSignUpActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
            txtPassword.requestFocus();
            return;
        }
        if(patientId.trim().length() == 0 ||patientId == null){
            Toast.makeText(PatientSignUpActivity.this, "Please enter patient id", Toast.LENGTH_LONG).show();
            txtPatientId.requestFocus();
            return;
        }
        if(email.trim().length() == 0 ||email == null){
            Toast.makeText(PatientSignUpActivity.this, "Please enter emailid", Toast.LENGTH_LONG).show();
            txtEmail.requestFocus();
            return;
        }
        if(!Utils.checkMailFormat(email)){
            Toast.makeText(PatientSignUpActivity.this, "Please enter valid email id", Toast.LENGTH_LONG).show();
            txtEmail.requestFocus();
            return;
        }
        if(contact.trim().length() == 0 || contact == null){
            Toast.makeText(PatientSignUpActivity.this, "Please enter contact no", Toast.LENGTH_LONG).show();
            txtContact.requestFocus();
            return;
        }
        if(doctorName.trim().length() == 0 || doctorName == null){
            Toast.makeText(PatientSignUpActivity.this, "Please enter doctor name", Toast.LENGTH_LONG).show();
            txtDoctorName.requestFocus();
            return;
        }

        final ProgressDialog dialog = new ProgressDialog(PatientSignUpActivity.this);
        dialog.setMessage("Processing...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        try{
            String url = getResources().getString(R.string.doctor_base_url) + getResources().getString(R.string.patient_base_url) + getResources().getString(R.string.signup_url);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            jsonObject.put("patientId", patientId);
            jsonObject.put("email", email);
            jsonObject.put("phone", contact);
            jsonObject.put("doctor", doctorName);

            ServiceApiClient.getResponse(getApplicationContext(), url, Request.Method.POST, jsonObject, new ServiceApiCallback()
            {
                @Override
                public void onSuccessResponse(String response)
                {
                    try
                    {
                        JSONObject respObject = new JSONObject(response);
                        dialog.hide();
                        boolean status = respObject.getBoolean("responseStatus");
                        if(status){
                            Intent intent = new Intent(PatientSignUpActivity.this, PatientLoginActivity.class);
                            startActivity(intent);
                        }else{

                            Toast.makeText(getApplicationContext(), respObject.getString("responseMessage"), Toast.LENGTH_LONG).show();
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
            e.printStackTrace();
        }
    }

}
