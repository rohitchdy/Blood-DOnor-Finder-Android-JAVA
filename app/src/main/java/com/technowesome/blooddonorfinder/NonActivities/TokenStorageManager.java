package com.technowesome.blooddonorfinder.NonActivities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class TokenStorageManager {
    private static final int MODE_PRIVATE = 0;

    public  void saveToken(Context con,String credentials, String data){

        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(con);
        preferences.edit().putString(credentials,data);
    }

    public  String getToken(Context con, String credentials, String defaultValue){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(con);
        String data=sharedPreferences.getString(credentials,defaultValue);
        return data;
    }
}
