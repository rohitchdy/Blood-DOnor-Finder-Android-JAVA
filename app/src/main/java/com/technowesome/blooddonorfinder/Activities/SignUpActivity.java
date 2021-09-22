package com.technowesome.blooddonorfinder.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.technowesome.blooddonorfinder.Models.User;
import com.technowesome.blooddonorfinder.NonActivities.APIUtils;
import com.technowesome.blooddonorfinder.NonActivities.PrefManager;
import com.technowesome.blooddonorfinder.R;
import com.technowesome.blooddonorfinder.Response.MessageResponse;
import com.technowesome.blooddonorfinder.Services.AuthService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private EditText inputFullName, inputMobile,inputDob,
            inputDistrict, inputMunicipality, inputLocalAddress, inputUsername, inputEmail,
            inputPassword;
    private Spinner inputGender, inputBloodGroup;
    private ProgressBar progressBar;
    private Button btnSignUp;
     AuthService authService;
    private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        inputFullName=(EditText)findViewById(R.id.full_name);
        inputMobile=(EditText)findViewById(R.id.mobile);
        inputDob=(EditText)findViewById(R.id.dob);
        inputGender=(Spinner)findViewById(R.id.genderSpinner);
        inputBloodGroup=(Spinner)findViewById(R.id.bg);
        inputDistrict=(EditText)findViewById(R.id.district);
        inputMunicipality=(EditText)findViewById(R.id.municipality);
        inputLocalAddress=(EditText)findViewById(R.id.local_address);
        inputUsername=(EditText)findViewById(R.id.username);
        inputEmail=(EditText)findViewById(R.id.email);
        inputPassword=(EditText)findViewById(R.id.password);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        btnSignUp=(Button)findViewById(R.id.btn_sign_up);


        btnSignUp.setOnClickListener(v -> {
            String fullName,mobile,dob,gender,bloodGroup,district,municipality,localAddress,username,
                    email,password;

            fullName=inputFullName.getText().toString().trim();
            mobile=inputMobile.getText().toString().trim();
            dob=inputDob.getText().toString().trim();
            gender=inputGender.getSelectedItem().toString();
            bloodGroup=inputBloodGroup.getSelectedItem().toString();
            district=inputDistrict.getText().toString();
            municipality=inputMunicipality.getText().toString();
            localAddress=inputLocalAddress.getText().toString();
            username=inputUsername.getText().toString();
            email=inputEmail.getText().toString();
            password=inputPassword.getText().toString();
            authService = APIUtils.getAuthService();
            if(fullName.isEmpty()){
                inputFullName.setError(getString(R.string.input_error_full_name));
                inputFullName.requestFocus();
                return;
            }
            if(mobile.isEmpty()){
                inputMobile.setError(getString(R.string.input_error_number_invalid));
                inputMobile.requestFocus();
                return;
            }
            if (mobile.length()!=10){
                inputMobile.setError(getString(R.string.input_error_mobile_length));
                inputMobile.requestFocus();
                return;
            }
            if(dob.isEmpty()){
                inputDob.setError(getString(R.string.input_error_dob));
                inputDob.requestFocus();
                return;
            }
            if(district.isEmpty()){
                inputDistrict.setError(getString(R.string.input_error_district));
                inputDistrict.requestFocus();
            }
            if(municipality.isEmpty()){
                inputMunicipality.setError(getString(R.string.input_error_municipality));
                inputDistrict.requestFocus();
            }
            if(localAddress.isEmpty()){
                inputLocalAddress.setError(getString(R.string.input_error_localAddress));
                inputLocalAddress.requestFocus();
            }
            if(username.isEmpty()){
                inputUsername.setError(getString(R.string.input_error_username));
                inputUsername.requestFocus();
            }
            if(email.isEmpty()){
                inputEmail.setError(getString(R.string.input_error_email));
                inputEmail.requestFocus();
            }
            if(password.isEmpty()){
                inputPassword.setError(getString(R.string.input_error_password));
                inputPassword.requestFocus();
                return;
            }
            if (password.length()<8){
                inputPassword.setError(getString(R.string.input_error_password_length));
                inputPassword.requestFocus();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);

            User u = new User();
            u.setFullName(fullName);
            u.setMobile(mobile);
            u.setDob(dob);
            u.setGender(gender);
            u.setBloodGroup(bloodGroup);
            u.setDistrict(district);
            u.setMunicipality(municipality);
            u.setLocalAddress(localAddress);
            u.setUsername(username);
            u.setEmail(email);
            u.setPassword(password);


            authService.addUser(u).enqueue(new Callback<User>() {

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
//                    Log.w(TAG, "onResponse: " + response );
                    if (response.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(getApplicationContext(),"Registered Successfully", Toast.LENGTH_LONG).show();

                        Intent intent =new Intent(SignUpActivity.this, EmailVerification1Activity.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                        finish();
                        // todo: do something with the response string
                    }
                    else{
                        if(response.code()==400){
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
//                                Log.d("Login",response.errorBody().string());
                                Log.d("Rohit",jObjError.toString());
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),jObjError.toString() ,Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"Register Unsuccess!",Toast.LENGTH_LONG).show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
//                    Log.w(TAG, "onFailure: " + t.getMessage() );
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Registered Unsuccessful. You may have no internet connection", Toast.LENGTH_SHORT).show();

                }
            });

        });

    }
//    private void saveUsername(String username) {
//        new PrefManager(this).saveUsername(username);
//    }

}