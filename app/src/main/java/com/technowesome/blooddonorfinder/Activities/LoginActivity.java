package com.technowesome.blooddonorfinder.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.technowesome.blooddonorfinder.NonActivities.APIUtils;
import com.technowesome.blooddonorfinder.NonActivities.PrefManager;
import com.technowesome.blooddonorfinder.NonActivities.TokenStorageManager;
import com.technowesome.blooddonorfinder.Payload.LoginRequest;
import com.technowesome.blooddonorfinder.R;
import com.technowesome.blooddonorfinder.Services.AuthService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText username, password;
    private Button btn_login;
    private TextView signUp, changePassword;
    private ProgressBar progressBar;
    private CheckBox checkBox;
    private AuthService authService;
    private TokenStorageManager tokenStorageManager;
    private static final int MODE_PRIVATE=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username= findViewById(R.id.username);
        password= findViewById((R.id.password));
        btn_login=findViewById(R.id.btn_login);
        progressBar=findViewById(R.id.progressBar);
        checkBox=findViewById(R.id.remember_me);
        if (!new PrefManager(this).isUserLogedOut()) {
            //user's email and password both are saved in preferences
            startHomeActivity();
        }
        signUp=findViewById(R.id.signup_text);
        signUp.setOnClickListener(v -> {
            Intent intent=new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });

        changePassword=findViewById(R.id.forget_password);
        changePassword.setOnClickListener(v -> {
            Intent intent=new Intent(LoginActivity.this, EnterEmail1Activity.class);
            startActivity(intent);
        });

        btn_login.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String pass,user;
            user=username.getText().toString().trim();
            pass=password.getText().toString().trim();
            Log.d("LoginActivity",username.getText().toString());
            Log.d("LoginActivity",password.getText().toString());
            if(user.isEmpty()){
                username.setError(getString(R.string.input_error_username));
                username.requestFocus();
                return;
            }

            if(pass.isEmpty()){
                password.setError(getString(R.string.input_error_password));
                password.requestFocus();
                return;
            }
            if (pass.length()<8){
                password.setError(getString(R.string.input_error_password_length));
                password.requestFocus();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            LoginRequest loginRequest= new LoginRequest();
            loginRequest.setUsername(user);
            loginRequest.setPassword(pass);
            Log.d("LoginActivity",loginRequest.getUsername());
            Log.d("LoginActivity",loginRequest.getPassword());
            authService = APIUtils.getAuthService();

            authService.login(loginRequest).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.d("Login", String.valueOf(+ response.code()));
                    if(response.isSuccessful()){
                        try {
                            if(response!=null){
                                if(response.code()==200){

                                    if(checkBox.isChecked()){
                                        saveLoginDetails(user,pass);
                                    }
                                    Gson gson= new Gson();
                                    String str_json = gson.toJson(response.body().string());
                                    Log.d("LoginActivity",str_json);
                                    progressBar.setVisibility(View.GONE);
                                    saveUserCredentials(str_json);

                                    Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
                                    startHomeActivity();

                                }

                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                    else{
                        if(response.code()==400){
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
//                                Log.d("Login",response.errorBody().string());
                                Log.d("Rohit",jObjError.toString());
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),jObjError.toString() ,Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this,EnterEmailActivity.class);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"Unauthorized",Toast.LENGTH_LONG).show();
                        }
                    }
                }


                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Log.d("LoginActivity",t.getMessage());
                    Toast.makeText(getApplicationContext(),"Login Failed. You may have no internet connection",Toast.LENGTH_LONG).show();

                }
            });
        });

    }
    private void startHomeActivity(){
        Intent intent= new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);

        finish();

    }

    private void saveLoginDetails(String username, String password) {
        new PrefManager(this).saveLoginDetails(username, password);
    }

    private void saveUserCredentials(String str_json){
        new PrefManager(this).saveUserCredentials(str_json);
    }

}