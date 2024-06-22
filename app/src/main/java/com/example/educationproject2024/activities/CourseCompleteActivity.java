package com.example.educationproject2024.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.educationproject2024.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseCompleteActivity extends AppCompatActivity {

    TextView courseNameAsTitle;
    LinearLayout exercisesButtonContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_complete);

        courseNameAsTitle = findViewById(R.id.course_name_as_title);
        exercisesButtonContainer = findViewById(R.id.exercises_button_container);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String courseName = extras.getString("course_name");
            int courseExercisesCount = Integer.parseInt(extras.getString("course_exercises_count"));

            String courseExercises = extras.getString("course_exercises");

            courseNameAsTitle.setText(courseName);

            for (int i = 1; i <= courseExercisesCount; i++) {
                LinearLayout exerciseButton = (LinearLayout) getLayoutInflater().inflate(R.layout.course_exercise_button, null);
                TextView exerciseNum = exerciseButton.findViewById(R.id.course_exercise_num);
                exerciseNum.setText(i + "");
                exercisesButtonContainer.addView(exerciseButton);
            }
        }
    }

    public void HomeFromCourseComplete(View view) {
        startActivity(new Intent(CourseCompleteActivity.this, HomeActivity.class));
    }
}