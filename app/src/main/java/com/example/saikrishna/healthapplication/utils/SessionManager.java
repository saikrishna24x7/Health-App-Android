package com.example.saikrishna.healthapplication.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.saikrishna.healthapplication.activity.DoctorLoginActivity;
import com.example.saikrishna.healthapplication.models.Doctor;

public class SessionManager {
    SharedPreferences _preferences;
    SharedPreferences.Editor _editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "SchoonerKeyRater";
    private static final String IS_LOGIN = "IsLoggedIn";

    public SessionManager(Context context)
    {
        this._context = context;
        _preferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        _editor = _preferences.edit();
    }

    public void createDoctorSession(Doctor doctor){
        _editor.putBoolean(IS_LOGIN, true);
        _editor.commit();
    }

    public void logoutDoctor()
    {
        _editor.clear();
        _editor.commit();
        Intent i = new Intent(_context, DoctorLoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    public void checkDoctorLogin()
    {
        if(!this.isLoggedIn())
        {
            Intent i = new Intent(_context, DoctorLoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }


//    public void createLoginSession(AppUser appUser)
//    {
//        _editor.putBoolean(IS_LOGIN, true);
////        _editor.putString(KEY_NAME, name);
//        _editor.commit();
//    }
//
//    public void logoutUser()
//    {
//        _editor.clear();
//        _editor.commit();
//        Intent i = new Intent(_context, LoginActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        _context.startActivity(i);
//    }
//
//    public void checkLogin()
//    {
//        if(!this.isLoggedIn())
//        {
//            Intent i = new Intent(_context, LoginActivity.class);
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            _context.startActivity(i);
//        }
//    }

    public boolean isLoggedIn()
    {
        return _preferences.getBoolean(IS_LOGIN, false);
    }
}
