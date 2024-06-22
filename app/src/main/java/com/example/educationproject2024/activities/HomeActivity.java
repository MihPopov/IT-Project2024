package com.example.educationproject2024.activities;

import static com.example.educationproject2024.Utils.APIKEY;
import static com.example.educationproject2024.Utils.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.educationproject2024.R;
import com.example.educationproject2024.Utils;
import com.example.educationproject2024.controller.API;
import com.example.educationproject2024.data.Course;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    TextView accountName;
    LinearLayout coursesList;

    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        coursesList = findViewById(R.id.coursesList);

        accountName = findViewById(R.id.account_name);
        accountName.setText(Utils.user.getFullName());

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);

        Call<List<Course>> call = api.getCourses(APIKEY, "*");
        call.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                List<Course> allCourses = response.body();
                for (int i = 0; i < allCourses.size(); i++) {
                    Course course = allCourses.get(i);
                    CardView coursePanel = (CardView) getLayoutInflater().inflate(R.layout.course_panel, null);

                    TextView tv = coursePanel.findViewById(R.id.course_name);
                    tv.setText(course.getName());

                    tv = coursePanel.findViewById(R.id.course_description);
                    tv.setText(course.getDescription());

                    tv = coursePanel.findViewById(R.id.course_exercises);
                    tv.setText(course.getExercisesCount());

                    coursePanel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(HomeActivity.this, CourseCompleteActivity.class);
                            intent.putExtra("course_name", course.getName());
                            intent.putExtra("course_exercises_count", course.getExercisesCount());
                            intent.putExtra("course_exercises", course.getExercises());
                            startActivity(intent);
                        }
                    });

                    coursesList.addView(coursePanel);
                }
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Произошла ошибка! Попробуйте ещё раз", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ProfileFromHome(View view) {
        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
    }
}