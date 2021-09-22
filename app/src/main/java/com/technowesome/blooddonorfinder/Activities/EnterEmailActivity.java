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

import com.technowesome.blooddonorfinder.NonActivities.APIUtils;
import com.technowesome.blooddonorfinder.R;
import com.technowesome.blooddonorfinder.Services.AuthService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterEmailActivity extends AppCompatActivity {
    EditText inputEmail;
    Button btnSubmit;
    ProgressBar progressBar;
    AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_email);
        inputEmail=findViewById(R.id.email);
        btnSubmit=findViewById(R.id.btn_email);
        progressBar=findViewById(R.id.progressBar);
        btnSubmit.setOnClickListener(v ->{
            progressBar.setVisibility(View.VISIBLE);

            String email=inputEmail.getText().toString().trim();
            if(email.isEmpty()){
                inputEmail.setError(getString(R.string.input_error_email));
                inputEmail.requestFocus();
                return;
            }
            Log.d("Email", email);
            authService = APIUtils.getAuthService();
            authService.findEmail(email).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(EnterEmailActivity.this, EmailVerificationActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Email not found",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Log.d("OTP1Activity",t.getMessage());
                    Toast.makeText(getApplicationContext(),"You may have no internet connection",Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}