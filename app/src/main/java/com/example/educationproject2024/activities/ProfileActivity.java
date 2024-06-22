package com.example.educationproject2024.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.educationproject2024.R;
import com.example.educationproject2024.Utils;
import com.example.educationproject2024.data.User;

public class ProfileActivity extends AppCompatActivity {

    TextView profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.profile_name);
        profileName.setText(Utils.user.getFullName());
    }

    public void HomeFromProfile(View view) {
        startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
    }

    public void AccountExit(View view) {
        Utils.user = new User("", "", "");
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
    }

    public void CourseCreationFromProfile(View view) {
        startActivity(new Intent(ProfileActivity.this, CourseCreationActivity.class));
    }
}