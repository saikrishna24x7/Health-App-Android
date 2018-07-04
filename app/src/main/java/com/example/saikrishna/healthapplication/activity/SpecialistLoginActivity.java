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

import org.json.JSONException;
import org.json.JSONObject;

public class SpecialistLoginActivity extends Activity {
    EditText txtUsername, txtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist_login);
//        NewRelic.withApplicationToken(
//                "AAecfdf3761f78935f1fcbd572534c3eab30d49b23"
//        ).start(this.getApplication());

        init();
    }

    private void init(){
        txtUsername = findViewById(R.id.username);
        txtPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.signin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login(){
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        if(username.trim().length() == 0 ||username == null){
            Toast.makeText(SpecialistLoginActivity.this, "Please enter username", Toast.LENGTH_LONG).show();
            txtUsername.requestFocus();
            return;
        }
        if(password.trim().length() == 0 ||password == null){
            Toast.makeText(SpecialistLoginActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
            txtPassword.requestFocus();
            return;
        }

        final ProgressDialog dialog = new ProgressDialog(SpecialistLoginActivity.this);
        dialog.setMessage("Processing...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        try {
            String url = getResources().getString(R.string.specialist_base_url)+getResources().getString(R.string.login_url);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("password", password);

            ServiceApiClient.getResponse(getApplicationContext(), url, Request.Method.POST, jsonObject, new ServiceApiCallback()
            {
                @Override
                public void onSuccessResponse(String response)
                {
                    try
                    {
                        JSONObject respObject = new JSONObject(response);
                        dialog.hide();
                        boolean responseStatus = respObject.getBoolean("responseStatus");
                        if(responseStatus){
                            dialog.hide();
                            Intent intent = new Intent(SpecialistLoginActivity.this, SpecialistScreenActivity.class);
                            intent.putExtra("user",respObject.getString("username"));
                            startActivity(intent);
                        }else{
                            dialog.hide();
                            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
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
    }
}
