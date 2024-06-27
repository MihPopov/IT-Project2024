package com.example.educationproject2024.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.educationproject2024.R;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
    }

    public void RegistrationFromPrivacyPolicy(View view) {
        startActivity(new Intent(PrivacyPolicyActivity.this, RegistrationActivity.class));
    }
}