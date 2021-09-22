package com.technowesome.blooddonorfinder.Services;

import com.squareup.picasso.RequestHandler;
import com.technowesome.blooddonorfinder.Models.PasswordChangeRequest;
import com.technowesome.blooddonorfinder.Models.User;
import com.technowesome.blooddonorfinder.Payload.LoginRequest;
import com.technowesome.blooddonorfinder.Response.JwtResponse;

import javax.xml.transform.Result;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AuthService {

    @POST("auth/signup")
    Call<User> addUser(@Body User user);

    @POST("auth/signin")
    Call<ResponseBody> login(@Body LoginRequest loginRequest);

    @POST("auth/emailverification/{username}")
    Call<ResponseBody> otp(@Path("username") String username,@Query("otpnum") int otpnum);

    @POST("auth/generate/otp")
    Call<ResponseBody> findEmail(@Query("email") String email);

    @POST("auth/otp/emailverification")
    Call<ResponseBody> otp1( @Query("email") String email, @Query("otpnum") int otpnum);

    @POST("auth/changePassword/{email}")
    Call<ResponseBody> changePassword(@Path("email") String email, @Query("otpnum") int otp,@Body PasswordChangeRequest passwordChangeRequest);

}
