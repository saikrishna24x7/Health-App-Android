package com.example.saikrishna.healthapplication.service;

public interface ServiceApiCallback {
    void onSuccessResponse(String response);
    void onFailureResponse(String message);
}
