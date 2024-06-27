package com.example.educationproject2024.activities;

import static com.example.educationproject2024.Utils.APIKEY;
import static com.example.educationproject2024.Utils.BASE_URL;

import android.content.ContentQueryMap;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import com.example.educationproject2024.data.CourseAdditional;
import com.example.educationproject2024.data.Exercise;
import com.example.educationproject2024.data.ExerciseComplete;
import com.example.educationproject2024.data.SubtitleAndText;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
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

        Call<List<CourseAdditional>> call = api.getCourses(APIKEY, "name");
        call.enqueue(new Callback<List<CourseAdditional>>() {
            @Override
            public void onResponse(Call<List<CourseAdditional>> call, Response<List<CourseAdditional>> response) {
                List<String> coursesName = new ArrayList<>();
                for (int i = 0; i < response.body().size(); i++) {
                    String name = response.body().get(i).getName();
                    if (!coursesName.contains(name)) coursesName.add(name);
                }

                for (int i = 0; i < coursesName.size(); i++) {
                    Call<List<CourseAdditional>> call2 = api.getCourse(APIKEY, "*", "eq." + coursesName.get(i));
                    call2.enqueue(new Callback<List<CourseAdditional>>() {
                        @Override
                        public void onResponse(Call<List<CourseAdditional>> call, Response<List<CourseAdditional>> response) {
                            List<CourseAdditional> courseData = response.body();
                            List<Exercise> exercises = new ArrayList<>();
                            for (int j = 1; j <= Integer.parseInt(courseData.get(0).getExercisesCount()); j++) {
                                List<CourseAdditional> exerciseData = new ArrayList<>();
                                for (int k = 0; k < courseData.size(); k++) {
                                    if (j == Integer.parseInt(courseData.get(k).getExerciseNumber())) {
                                        exerciseData.add(courseData.get(k));
                                    }
                                }
                                Exercise exercise = new Exercise();
                                exercise.setExerciseType(exerciseData.get(0).getExerciseType());
                                exercise.setExerciseAnswerType(exerciseData.get(0).getExerciseAnswerType());
                                exercise.setExerciseTitle(exerciseData.get(0).getExerciseTitle());
                                List<String> exerciseSubtitles = getColumn(exerciseData, "exerciseSubtitle");
                                List<String> exerciseTexts = getColumn(exerciseData, "exerciseText");
                                List<SubtitleAndText> subtitleAndTextList = new ArrayList<>();
                                if (!exerciseTexts.isEmpty()) {
                                    for (int k = 0; k < exerciseTexts.size(); k++) {
                                        SubtitleAndText subtitleAndText = new SubtitleAndText();
                                        if (!exerciseSubtitles.isEmpty()) subtitleAndText.setExerciseSubtitle(exerciseSubtitles.get(k));
                                        subtitleAndText.setExerciseText(exerciseTexts.get(k));
                                        subtitleAndTextList.add(subtitleAndText);
                                    }
                                }
                                exercise.setExerciseSubtitleAndText(subtitleAndTextList);
                                if (exercise.getExerciseType().equals("Практика")) {
                                    exercise.setExerciseRightAnswers(getColumn(exerciseData, "exerciseRightAnswer"));
                                    if (exercise.getExerciseAnswerType().equals("Выбор ответа")) {
                                        exercise.setExerciseAnswerVariants(getColumn(exerciseData, "exerciseAnswerVariant"));
                                    }
                                }
                                exercises.add(exercise);
                            }
                            MaterialCardView coursePanel = (MaterialCardView) getLayoutInflater().inflate(R.layout.course_panel, null);

                            TextView tv = coursePanel.findViewById(R.id.course_name);
                            tv.setText(courseData.get(0).getName());

                            tv = coursePanel.findViewById(R.id.course_description);
                            tv.setText(courseData.get(0).getDescription());

                            tv = coursePanel.findViewById(R.id.course_exercises);
                            tv.setText(courseData.get(0).getExercisesCount());

                            String subject = courseData.get(0).getSubject();
                            if (subject.equals("Математика")) {
                                coursePanel.setCardBackgroundColor(Color.parseColor("#00F7FF"));
                                coursePanel.setStrokeColor(Color.parseColor("#00BCD4"));
                            }
                            if (subject.equals("История")) {
                                coursePanel.setCardBackgroundColor(Color.parseColor("#FB6060"));
                                coursePanel.setStrokeColor(Color.parseColor("#B8514A"));
                            }
                            if (subject.equals("Информатика")) {
                                coursePanel.setCardBackgroundColor(Color.parseColor("#FFF200"));
                                coursePanel.setStrokeColor(Color.parseColor("#EBBC2E"));
                            }
                            if (subject.equals("Химия")) {
                                coursePanel.setCardBackgroundColor(Color.parseColor("#2EFF00"));
                                coursePanel.setStrokeColor(Color.parseColor("#8BC34A"));
                            }

                            Call<List<ExerciseComplete>> call3 = api.getCompletedCourses(APIKEY, "user_name,course_name");
                            call3.enqueue(new Callback<List<ExerciseComplete>>() {
                                @Override
                                public void onResponse(Call<List<ExerciseComplete>> call, Response<List<ExerciseComplete>> response) {
                                    for (int i = 0; i < response.body().size(); i++) {
                                        if (response.body().get(i).getUserName().equals(Utils.user.getFullName()) && response.body().get(i).getCourseName().equals(courseData.get(0).getName())) {
                                            coursePanel.findViewById(R.id.complete_status).setVisibility(View.VISIBLE);
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<ExerciseComplete>> call, Throwable t) {

                                }
                            });

                            Course course = new Course();
                            course.setExercises(exercises);

                            coursePanel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(HomeActivity.this, CourseCompleteActivity.class);
                                    intent.putExtra("course_name", courseData.get(0).getName());
                                    intent.putExtra("course_exercises_count", courseData.get(0).getExercisesCount());
                                    intent.putExtra("course_exercises", course);
                                    startActivity(intent);
                                }
                            });

                            coursesList.addView(coursePanel);
                        }

                        @Override
                        public void onFailure(Call<List<CourseAdditional>> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<CourseAdditional>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Произошла ошибка! Попробуйте ещё раз", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ProfileFromHome(View view) {
        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
    }

    public List<String> getColumn(List<CourseAdditional> course, String columnName) {
        List<String> column = new ArrayList<>();
        for (int i = 0; i < course.size(); i++) {
            if (columnName.equals("exerciseSubtitle")) {
                String subtitle = course.get(i).getExerciseSubtitle();
                if (!subtitle.equals("")) column.add(subtitle);
            }
            if (columnName.equals("exerciseText")) {
                String text = course.get(i).getExerciseText();
                if (!text.equals("")) column.add(text);
            }
            if (columnName.equals("exerciseAnswerVariant")) {
                String answerVariant = course.get(i).getExerciseAnswerVariant();
                if (!answerVariant.equals("")) column.add(answerVariant);
            }
            if (columnName.equals("exerciseRightAnswer")) {
                String rightAnswer = course.get(i).getExerciseRightAnswer();
                if (!rightAnswer.equals("")) column.add(rightAnswer);
            }
        }
        return column;
    }
}