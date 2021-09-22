package com.technowesome.blooddonorfinder.NonActivities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;

public class PrefManager {
    Context context;
    public PrefManager(Context context){
        this.context=context;
    }

    public void saveLoginDetails(String username, String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Username", username);
        editor.putString("Password", password);
        editor.commit();
    }

    public boolean isUserLogedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean isUsernameEmpty = sharedPreferences.getString("Username", "").isEmpty();
        boolean isPasswordEmpty = sharedPreferences.getString("Password", "").isEmpty();
        return isUsernameEmpty || isPasswordEmpty;
    }

    public void saveUserCredentials(String str_json){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userCredentials", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userCredentials", str_json);
        editor.commit();
    }
    public void Logout(){
        SharedPreferences sharedPreferences=context.getSharedPreferences("LoginDetail",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();

    }
    public void clearSharedPreferences(){
        File dir=new File(context.getFilesDir().getParent()+"/shared_prefs/");
        String[] children=dir.list();
        for(int i=0; i<children.length;i++){
            context.getSharedPreferences(children[i].replace(".xml",""),Context.MODE_PRIVATE).edit().clear().commit();
            new File(dir,children[i]).delete();
        }
    }

//    public String getUsername(){
//        SharedPreferences sharedPreferences=context.getSharedPreferences("userCredentials", Context.MODE_PRIVATE);
//        Gson gson= new Gson();
//        File dir=new File(context.getFilesDir().getParent()+"/shared_prefs/");
//       return gson.fromJson(sharedPreferences.getString("userCredentials","dir"), JSONObject.class).toString();
//    }

    public void saveUsername(String username){
        SharedPreferences sharedPreferences=context.getSharedPreferences("Username", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
    }
    public  JSONObject getUsername(){

        SharedPreferences sharedPreferences = context.getSharedPreferences("userCredentials", Context.MODE_PRIVATE);
        Gson gson = new Gson();
           String json = sharedPreferences.getString("userCredentials", "");
           JSONObject obj=gson.fromJson(json,JSONObject.class);
           return obj;


    }


}
