package com.technowesome.blooddonorfinder.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.technowesome.blooddonorfinder.NonActivities.PrefManager;
import com.technowesome.blooddonorfinder.R;

import org.json.JSONObject;

public class DemoActivity extends AppCompatActivity {
    Button demoBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        demoBtn=findViewById(R.id.btn_demo);

        demoBtn.setOnClickListener(v -> {
            String username=getUsername().toString();
            Toast.makeText(getApplicationContext(),username,Toast.LENGTH_LONG).show();

        });
    }

    private JSONObject getUsername() {
       return new PrefManager(this).getUsername();
    }
}