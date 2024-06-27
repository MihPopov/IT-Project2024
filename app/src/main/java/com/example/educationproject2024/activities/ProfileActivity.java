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

    TextView profileName, coursesCompleted, coursesCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.profile_name);
        coursesCompleted = findViewById(R.id.courses_completed_num);
        coursesCreated = findViewById(R.id.courses_created_num);

        profileName.setText(Utils.user.getFullName());
        coursesCompleted.setText(Utils.user.getCoursesCompleted());
        coursesCreated.setText(Utils.user.getCoursesCreated());
    }

    public void HomeFromProfile(View view) {
        startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
    }

    public void AccountExit(View view) {
        Utils.user = new User("", "", "", "", "");
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
    }

    public void CourseCreationFromProfile(View view) {
        startActivity(new Intent(ProfileActivity.this, CourseCreationActivity.class));
    }

    public void ChangeUserFromProfile(View view) {
        startActivity(new Intent(ProfileActivity.this, ChangeUserActivity.class));
    }
}