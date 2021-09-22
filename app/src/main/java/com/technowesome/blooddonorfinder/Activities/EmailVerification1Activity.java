package com.technowesome.blooddonorfinder.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;
import com.technowesome.blooddonorfinder.NonActivities.APIUtils;
import com.technowesome.blooddonorfinder.R;
import com.technowesome.blooddonorfinder.Services.AuthService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailVerification1Activity extends AppCompatActivity implements View.OnClickListener,
        OnOtpCompletionListener {
    private Button validateButton;
    private OtpView otpView;
    ProgressBar progressBar;
    AuthService authService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification1);
        initializeUi();
        setListeners();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.validate_button) {
            String otpnum=otpView.getText().toString();

            if(otpnum.length()<6){
                Toast.makeText(getApplicationContext(),"OTP must be of 6 character",Toast.LENGTH_LONG).show();

            }
            else {
                int otp=Integer.parseInt(otpnum);
                String username1=getUsername();
                //            Log.d("Credentials", email);

                authService = APIUtils.getAuthService();
                authService.otp(username1,otp).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            if(response.code()==200){
//                            progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"Email verified successfully. Now you can login",Toast.LENGTH_LONG).show();
                                Intent intent =new Intent(EmailVerification1Activity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else{
//                        progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"OTP does not match",Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("OTPActivity",t.getMessage());
                        Toast.makeText(getApplicationContext(),"You may have no internet connection",Toast.LENGTH_LONG).show();
                    }
                });
            }





        }
    }


    private void initializeUi() {
        otpView = findViewById(R.id.otp_view);
        validateButton = findViewById(R.id.validate_button);
    }

    private void setListeners() {
        validateButton.setOnClickListener(this);
        otpView.setOtpCompletionListener(this);
    }

    @Override
    public void onOtpCompleted(String otp) {

        Log.d("Credentials",otp);
        // do Stuff
        Toast.makeText(this, "Now click on Validate", Toast.LENGTH_SHORT).show();
    }
    public String getUsername(){
        Intent intent=getIntent();
        String username=intent.getStringExtra("username");
        return username;
    }
}