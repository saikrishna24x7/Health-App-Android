package com.example.saikrishna.healthapplication.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.saikrishna.healthapplication.R;
import com.example.saikrishna.healthapplication.service.ServiceApiCallback;
import com.example.saikrishna.healthapplication.service.ServiceApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Base64;

public class SendToDoctor extends AppCompatActivity {

    Button btnUploadImage, btnSendToDoctor;
    String patientId, doctorName, record;
    Bitmap sourceBitmap1, sourceBitmap2, sourceBitmap3;
    TextView txtImageNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_to_doctor);
        init();
    }

    private void init(){

        Toolbar toolbar = findViewById(R.id.toolbarSendToDoctor);
        setSupportActionBar(toolbar);
        setTitle(R.string.title);

        patientId = getIntent().getExtras().getString("patientId");
        doctorName = getIntent().getExtras().getString("doctorName");
        record = getIntent().getExtras().getString("record");

        txtImageNames = findViewById(R.id.imageNames);

        btnUploadImage = findViewById(R.id.uploadImage);
        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//                photoPickerIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });

        btnSendToDoctor = findViewById(R.id.sendToDoctor);
        btnSendToDoctor.setText("Send To "+doctorName);
        btnSendToDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToDoctor();
            }
        });
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                if(sourceBitmap1 == null) {
                    sourceBitmap1 = BitmapFactory.decodeStream(imageStream);
                }else if(sourceBitmap2 == null){
                    sourceBitmap2 = BitmapFactory.decodeStream(imageStream);
                }else if(sourceBitmap3 == null){
                    sourceBitmap3 = BitmapFactory.decodeStream(imageStream);
                }
                txtImageNames.setText(txtImageNames.getText().toString() + "\n" + getPath(imageUri));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        return filePath.split("/")[filePath.split("/").length-1];
    }

    private void sendToDoctor(){

        int height = sourceBitmap1.getHeight();
        int width = sourceBitmap1.getWidth();

        byte[] image1 = new byte[0], image2 = new byte[0], image3 = new byte[0];

        if(sourceBitmap1 !=null){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            sourceBitmap1.compress(Bitmap.CompressFormat.PNG, 100, stream);
            image1 = stream.toByteArray();
            sourceBitmap1.recycle();
        }

        if(sourceBitmap2 !=null){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            sourceBitmap2.compress(Bitmap.CompressFormat.PNG, 100, stream);
            image2 = stream.toByteArray();
            sourceBitmap2.recycle();
        }

        if(sourceBitmap3 !=null){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            sourceBitmap3.compress(Bitmap.CompressFormat.PNG, 100, stream);
            image3 = stream.toByteArray();
            sourceBitmap3.recycle();
        }


        final ProgressDialog dialog = new ProgressDialog(SendToDoctor.this);
        dialog.setMessage("Processing...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("patientID", patientId);
            jsonObject.put("doctorName", doctorName);
            jsonObject.put("patientData", record);
            jsonObject.put("height", height);
            jsonObject.put("width", width);
            if(sourceBitmap1 !=null){
                String encodedImage = new String(org.apache.commons.codec.binary.Base64.encodeBase64(image1));
                jsonObject.put("image1", encodedImage);
            }
            if(sourceBitmap2 !=null){
                String encodedImage = new String(org.apache.commons.codec.binary.Base64.encodeBase64(image2));
                jsonObject.put("image2", encodedImage);
            }
            if(sourceBitmap3 !=null){
                String encodedImage = new String(org.apache.commons.codec.binary.Base64.encodeBase64(image3));
                jsonObject.put("image3", encodedImage);
            }

            String url = getResources().getString(R.string.base_url) + getResources().getString(R.string.send_to_doctor);

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
                            Toast.makeText(SendToDoctor.this, "Record sent to doctor", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SendToDoctor.this, PatientScreenActivity.class);
                            intent.putExtra("patientId",patientId);
                            intent.putExtra("doctorName", doctorName);
                            startActivity(intent);
                        }else{
                            Toast.makeText(SendToDoctor.this, "Sending failed", Toast.LENGTH_LONG).show();
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
