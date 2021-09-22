package com.technowesome.blooddonorfinder.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;
import com.technowesome.blooddonorfinder.Models.PasswordChangeRequest;
import com.technowesome.blooddonorfinder.NonActivities.APIUtils;
import com.technowesome.blooddonorfinder.R;
import com.technowesome.blooddonorfinder.Services.AuthService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener, OnOtpCompletionListener {

    private Button validateButton;
    private OtpView otpView;
    EditText newPasswordInput;
    EditText confirmPasswordInput;
    ProgressBar progressBar;
    AuthService authService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initializeUi();
        setListeners();
    }
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.password_change_button) {
            String otpnum=otpView.getText().toString();
            String newPassword=newPasswordInput.getText().toString().trim();
            String confirmPassword=confirmPasswordInput.getText().toString().trim();

            if(otpnum.length()<6){
                Toast.makeText(getApplicationContext(),"OTP must be of 6 character",Toast.LENGTH_LONG).show();

            }
            else if(newPassword.isEmpty()){
                newPasswordInput.setError(getString(R.string.input_error_password));
                newPasswordInput.requestFocus();
                return;
            }
            else if (newPassword.length()<8){
                newPasswordInput.setError(getString(R.string.input_error_password_length));
                newPasswordInput.requestFocus();
                return;
            }
            else if(confirmPassword.isEmpty()){
                confirmPasswordInput.setError(getString(R.string.input_error_password));
                confirmPasswordInput.requestFocus();
                return;
            }
            else if (confirmPassword.length()<8){
                confirmPasswordInput.setError(getString(R.string.input_error_password_length));
                confirmPasswordInput.requestFocus();
                return;
            }

            else {
                int otp=Integer.parseInt(otpnum);

                PasswordChangeRequest passwordChangeRequest=new PasswordChangeRequest();
                passwordChangeRequest.setNewPassword(newPassword);
                passwordChangeRequest.setConfirmPassword(confirmPassword);
                String email1 = getEmail();


                authService = APIUtils.getAuthService();
                authService.changePassword(email1,otp,passwordChangeRequest).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            if(response.code()==200){

                                Toast.makeText(getApplicationContext(),"Password Change Successfully",Toast.LENGTH_LONG).show();
                                Intent intent =new Intent(ChangePasswordActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else
                            {if(response.code()==400){
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
//
                                Toast.makeText(getApplicationContext(),jObjError.toString() ,Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("OTPActivity",t.getMessage());
                        Toast.makeText(getApplicationContext(),"You may have no internet connection",Toast.LENGTH_LONG).show();
                    }
                });
            }





        }
    }
    private void initializeUi() {
        otpView = findViewById(R.id.otp_view);
        validateButton = findViewById(R.id.password_change_button);
        newPasswordInput= findViewById(R.id.newPassword);
        confirmPasswordInput= findViewById(R.id.confirmPassword);
    }
    private void setListeners() {
        validateButton.setOnClickListener(this);
        otpView.setOtpCompletionListener(this);
    }
    @Override
    public void onOtpCompleted(String otp) {

//        Log.d("Credentials",otp);
//        // do Stuff
//        Toast.makeText(this, "Now click on Validate", Toast.LENGTH_SHORT).show();
    }
    public String getEmail(){
        Intent intent=getIntent();
        String email=intent.getStringExtra("email");
        return email;
    }
}