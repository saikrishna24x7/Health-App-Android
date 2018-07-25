package com.example.saikrishna.healthapplication.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.saikrishna.healthapplication.R;
import com.example.saikrishna.healthapplication.service.ServiceApiCallback;
import com.example.saikrishna.healthapplication.service.ServiceApiClient;
import com.example.saikrishna.healthapplication.utils.SessionManager;
import com.example.saikrishna.healthapplication.utils.Utils;
import com.newrelic.agent.android.NewRelic;

import org.json.JSONException;
import org.json.JSONObject;

public class DoctorSignUpActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword, txtAddress, txtEmail, txtContact;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_signup);
//        NewRelic.withApplicationToken(
//                "AAecfdf3761f78935f1fcbd572534c3eab30d49b23"
//        ).start(this.getApplication());

        init();
    }

    private void init(){

        Toolbar toolbar = findViewById(R.id.toolbarDoctorSignup);
        setSupportActionBar(toolbar);
        setTitle(R.string.title);

        txtUsername = findViewById(R.id.username);
        txtPassword = findViewById(R.id.password);
        txtAddress = findViewById(R.id.address);
        txtEmail = findViewById(R.id.email);
        txtContact = findViewById(R.id.contact);

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
        String address = txtAddress.getText().toString();
        String email = txtEmail.getText().toString();
        String contact = txtContact.getText().toString();

        if(username.trim().length() == 0 ||username == null){
            Toast.makeText(DoctorSignUpActivity.this, "Please enter username", Toast.LENGTH_LONG).show();
            txtUsername.requestFocus();
            return;
        }
        if(password.trim().length() == 0 ||password == null){
            Toast.makeText(DoctorSignUpActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
            txtPassword.requestFocus();
            return;
        }
        if(address.trim().length() == 0 ||address == null){
            Toast.makeText(DoctorSignUpActivity.this, "Please enter address", Toast.LENGTH_LONG).show();
            txtAddress.requestFocus();
            return;
        }
        if(email.trim().length() == 0 ||email == null){
            Toast.makeText(DoctorSignUpActivity.this, "Please enter emailid", Toast.LENGTH_LONG).show();
            txtEmail.requestFocus();
            return;
        }
        if(!Utils.checkMailFormat(email)){
            Toast.makeText(DoctorSignUpActivity.this, "Please enter valid email id", Toast.LENGTH_LONG).show();
            txtEmail.requestFocus();
            return;
        }
        if(contact.trim().length() == 0 || contact == null){
            Toast.makeText(DoctorSignUpActivity.this, "Please enter contact no", Toast.LENGTH_LONG).show();
            txtContact.requestFocus();
            return;
        }

        final ProgressDialog dialog = new ProgressDialog(DoctorSignUpActivity.this);
        dialog.setMessage("Processing...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        try{
            String url = getResources().getString(R.string.base_url) + getResources().getString(R.string.doctor_base_url)+getResources().getString(R.string.signup_url);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            jsonObject.put("email", email);
            jsonObject.put("address", address);
            jsonObject.put("phone", contact);

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
                            Intent intent = new Intent(DoctorSignUpActivity.this, DoctorLoginActivity.class);
                            startActivity(intent);
                        }else{

                            Toast.makeText(getApplicationContext(), respObject.getString("responseMessage"), Toast.LENGTH_LONG).show();
                        }
//                        Intent intent = new Intent(DoctorSignUpActivity.this, DoctorLoginActivity.class);
//                        startActivity(intent);
//                        if(respObject.has("message"))
//                        {
//                            dialog.hide();
//                            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
//                        }
//                        else
//                        {
//                            dialog.hide();
//                            user = new AppUser();
//                            Settings userSettings = new Settings();
//                            JSONObject settingsObject = respObject.getJSONObject("Settings");
//                            userSettings.setmAppUserId(settingsObject.getInt("userId"));
//                            userSettings.setmFormId(settingsObject.getString("formId"));
//                            userSettings.setmInsuranceState(settingsObject.getString("applicationState"));
////                            user.setAppUserFirstName(respObject.getString("AppUserFirstName"));
////                            user.setAppUserLastName(respObject.getString("AppUserLastName"));
////                            user.setAppUserEmail(respObject.getString("AppUserEmail"));

//                        }
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
