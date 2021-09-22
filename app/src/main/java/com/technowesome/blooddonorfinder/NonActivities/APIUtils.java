package com.technowesome.blooddonorfinder.NonActivities;

import com.technowesome.blooddonorfinder.Services.AuthService;
import com.technowesome.blooddonorfinder.Services.UserService;

public class APIUtils {

    private APIUtils(){
    };

    public static final String API_URL = "http://192.168.1.138:8080/blood-donor-finder/api/";

    public static AuthService getAuthService(){
        return RetrofitClient.getClient(API_URL).create(AuthService.class);
    }
}
