package com.example.saikrishna.healthapplication.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    static String baseUrl;

    public static boolean checkMailFormat(String email){
        boolean flag=false;
        //regular expression to check format and allowable characters in mail id
        String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
        Pattern p = Pattern.compile(regEx);//compile regular expression for validity
        Matcher m = p.matcher(email);//check mail with regular expression
        if(m.find()){
            flag = true;
        }else {
            flag = false;
        }
        return flag;
    }

    public static String formatTimestamp(String inputString){
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
//        Date date = new Date(Long.valueOf(inputString.replace("T", " ")));
        Date date = null;
        try {
            date = dateFormat.parse(inputString.replace("T", " "));
        } catch (ParseException e) {
            Log.d("Exception:", "Format Exception");
        }
        String convertedDate = dateFormat.format(date);
        return convertedDate;
    }
}
