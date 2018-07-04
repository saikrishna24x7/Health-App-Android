package com.example.saikrishna.healthapplication.utils;

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

    public static void setBaseUrl(String u){
        baseUrl = "http://"+u+"/api/";
    }

    public static String getBaseUrl(){
        return baseUrl;
    }
}
