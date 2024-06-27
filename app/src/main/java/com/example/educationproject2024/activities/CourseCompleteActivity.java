package com.example.educationproject2024.activities;

import static com.example.educationproject2024.Utils.APIKEY;
import static com.example.educationproject2024.Utils.BASE_URL;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.educationproject2024.R;
import com.example.educationproject2024.Utils;
import com.example.educationproject2024.controller.API;
import com.example.educationproject2024.data.Course;
import com.example.educationproject2024.data.ExerciseComplete;
import com.example.educationproject2024.data.Exercise;
import com.example.educationproject2024.data.SubtitleAndText;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CourseCompleteActivity extends AppCompatActivity {

    TextView courseNameAsTitle;
    LinearLayout exercisesIndicatorContainer, exercisesContainer;
    MaterialButton completeButton;

    List<Integer> rightCompleteList = new ArrayList<>();
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_complete);

        courseNameAsTitle = findViewById(R.id.course_name_as_title);
        exercisesIndicatorContainer = findViewById(R.id.exercises_button_container);
        exercisesContainer = findViewById(R.id.exercises_container);
        completeButton = findViewById(R.id.button_complete);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String courseName = extras.getString("course_name");
            int courseExercisesCount = Integer.parseInt(extras.getString("course_exercises_count"));
            List<Exercise> courseExercises = ((Course) extras.getSerializable("course_exercises")).getExercises();

            courseNameAsTitle.setText(courseName);

            Call<List<ExerciseComplete>> call = api.getCompletedCourses(APIKEY, "*");
            call.enqueue(new Callback<List<ExerciseComplete>>() {
                @Override
                public void onResponse(Call<List<ExerciseComplete>> call, Response<List<ExerciseComplete>> response) {
                    for (int i = 0; i < response.body().size(); i++) {
                        if (response.body().get(i).getUserName().equals(Utils.user.getFullName()) && response.body().get(i).getCourseName().equals(courseName)) {
                            rightCompleteList.add(Integer.parseInt(response.body().get(i).getExerciseCompleteStatus()));
                            if (rightCompleteList.size() == Integer.parseInt(response.body().get(i).getExercisesCount())) break;
                        }
                    }

                    if (rightCompleteList.isEmpty()) {
                        for (int i = 1; i <= courseExercisesCount; i++) {
                            LinearLayout exerciseIndicator = (LinearLayout) getLayoutInflater().inflate(R.layout.course_exercise_indicator, null);
                            TextView exerciseNum = exerciseIndicator.findViewById(R.id.course_exercise_num);
                            exerciseNum.setText(i + "");

                            exercisesIndicatorContainer.addView(exerciseIndicator);
                        }

                        for (int i = 0; i < courseExercisesCount; i++) {
                            LinearLayout exerciseLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.course_exercise_to_complete, null);
                            Exercise exercise = courseExercises.get(i);

                            TextView exerciseTitle = exerciseLayout.findViewById(R.id.exercise_title);
                            exerciseTitle.setText(exercise.getExerciseTitle());

                            LinearLayout exerciseSubtitleAndText = exerciseLayout.findViewById(R.id.subtitle_and_text_container);
                            for (int j = 0; j < exercise.getExerciseSubtitleAndText().size(); j++) {
                                LinearLayout subtitleAndTextLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.exercise_subtitle_and_text, null);
                                SubtitleAndText subtitleAndText = exercise.getExerciseSubtitleAndText().get(j);
                                if (subtitleAndText.getExerciseSubtitle() != null) {
                                    TextView subtitle = subtitleAndTextLayout.findViewById(R.id.exercise_subtitle);
                                    subtitle.setText(subtitleAndText.getExerciseSubtitle());
                                    subtitle.setVisibility(View.VISIBLE);
                                }
                                TextView text = subtitleAndTextLayout.findViewById(R.id.exercise_text);
                                text.setText(subtitleAndText.getExerciseText());
                                exerciseSubtitleAndText.addView(subtitleAndTextLayout);
                            }

                            if (exercise.getExerciseType().equals("Практика")) {
                                if (exercise.getExerciseAnswerType().equals("Выбор ответа")) {
                                    List<String> answerVariantsList = exercise.getExerciseAnswerVariants();
                                    if (exercise.getExerciseRightAnswers().size() == 1) {
                                        RadioGroup answerVariants = exerciseLayout.findViewById(R.id.answer_variants_container);
                                        for (int j = 0; j < answerVariantsList.size(); j++) {
                                            RadioButton answerVariant = new RadioButton(getApplicationContext());
                                            answerVariant.setText(answerVariantsList.get(j));
                                            answerVariants.addView(answerVariant);
                                        }
                                    }
                                    else {
                                        LinearLayout answerVariantsLayout = exerciseLayout.findViewById(R.id.answer_variants_layout);
                                        for (int j = 0; j < answerVariantsList.size(); j++) {
                                            CheckBox answerVariant = new CheckBox(getApplicationContext());
                                            answerVariant.setText(answerVariantsList.get(j));
                                            answerVariantsLayout.addView(answerVariant);
                                        }
                                    }
                                }
                                else {
                                    LinearLayout answersInputContainer = exerciseLayout.findViewById(R.id.right_answers_input_container);
                                    answersInputContainer.setVisibility(View.VISIBLE);

                                    MaterialButton addAnswerInput = exerciseLayout.findViewById(R.id.button_add_answer_input);
                                    addAnswerInput.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            EditText answerInput = (EditText) getLayoutInflater().inflate(R.layout.exercise_answer_input, null);
                                            answersInputContainer.addView(answerInput, answersInputContainer.getChildCount() - 1);
                                        }
                                    });

                                    MaterialButton removeAnswerInput = exerciseLayout.findViewById(R.id.button_remove_answer_input);
                                    removeAnswerInput.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (answersInputContainer.getChildCount() > 2) answersInputContainer.removeViewAt(answersInputContainer.getChildCount() - 2);
                                        }
                                    });

                                }
                            }

                            exercisesContainer.addView(exerciseLayout);
                        }

                        completeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                rightCompleteList.clear();
                                for (int i = 0; i < exercisesContainer.getChildCount(); i++) {
                                    if (courseExercises.get(i).getExerciseType().equals("Практика")) {
                                        Exercise exercise = courseExercises.get(i);
                                        List<String> rightAnswers = exercise.getExerciseRightAnswers();
                                        if (exercise.getExerciseAnswerType().equals("Выбор ответа")) {
                                            List<String> selectedAnswers = new ArrayList<>();
                                            if (rightAnswers.size() == 1) {
                                                RadioGroup answerVariants = exercisesContainer.getChildAt(i).findViewById(R.id.answer_variants_container);
                                                for (int j = 0; j < answerVariants.getChildCount(); j++) {
                                                    RadioButton answerVariant = (RadioButton) answerVariants.getChildAt(j);
                                                    if (answerVariant.isChecked())
                                                        selectedAnswers.add(answerVariant.getText().toString());
                                                }
                                                if (rightAnswers.equals(selectedAnswers)) {
                                                    rightCompleteList.add(1);
                                                }
                                                else {
                                                    rightCompleteList.add(0);
                                                }
                                            }
                                            else {
                                                LinearLayout answerVariants = exercisesContainer.getChildAt(i).findViewById(R.id.answer_variants_layout);
                                                for (int j = 0; j < answerVariants.getChildCount(); j++) {
                                                    CheckBox answerVariant = (CheckBox) answerVariants.getChildAt(j);
                                                    if (answerVariant.isChecked())
                                                        selectedAnswers.add(answerVariant.getText().toString());
                                                }
                                                if (rightAnswers.equals(selectedAnswers)) {
                                                    rightCompleteList.add(1);
                                                }
                                                else {
                                                    rightCompleteList.add(0);
                                                }
                                            }
                                        }
                                        else {
                                            LinearLayout answersInputLayout = exercisesContainer.getChildAt(i).findViewById(R.id.right_answers_input_container);
                                            List<String> answers = new ArrayList<>();
                                            for (int j = 0; j < answersInputLayout.getChildCount() - 1; j++) {
                                                String answer = ((EditText) answersInputLayout.getChildAt(j)).getText().toString();
                                                answers.add(answer);
                                            }
                                            if (rightAnswers.equals(answers)) {
                                                rightCompleteList.add(1);
                                            }
                                            else {
                                                rightCompleteList.add(0);
                                            }
                                        }
                                    }
                                    else {
                                        rightCompleteList.add(-1);
                                    }
                                }
                                for (int i = 0; i < exercisesIndicatorContainer.getChildCount(); i++) {
                                    LinearLayout exerciseIndicator = (LinearLayout) exercisesIndicatorContainer.getChildAt(i);
                                    if (courseExercises.get(i).getExerciseType().equals("Практика")) {
                                        if (rightCompleteList.get(i) == 1) exerciseIndicator.setBackgroundColor(Color.parseColor("#2EFF00"));
                                        if (rightCompleteList.get(i) == 0) exerciseIndicator.setBackgroundColor(Color.parseColor("#FF2525"));
                                    }
                                }
                                completeButton.setVisibility(View.GONE);
                                for (int i = 0; i < courseExercisesCount; i++) {
                                    if (courseExercises.get(i).getExerciseType().equals("Практика") && courseExercises.get(i).getExerciseAnswerType().equals("Ввод ответа")) {
                                        exercisesContainer.getChildAt(i).findViewById(R.id.button_add_answer_input).setVisibility(View.GONE);
                                        exercisesContainer.getChildAt(i).findViewById(R.id.button_remove_answer_input).setVisibility(View.GONE);
                                    }
                                }

                                List<ExerciseComplete> courseComplete = new ArrayList<>();
                                for (int i = 0; i < rightCompleteList.size(); i++) {
                                    ExerciseComplete exerciseComplete = new ExerciseComplete();
                                    exerciseComplete.setUserName(Utils.user.getFullName());
                                    exerciseComplete.setCourseName(courseName);
                                    exerciseComplete.setExercisesCount(rightCompleteList.size() + "");
                                    exerciseComplete.setExerciseCompleteStatus(rightCompleteList.get(i) + "");
                                    courseComplete.add(exerciseComplete);
                                }

                                Call<Void> call = api.courseComplete(APIKEY, courseComplete);
                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });

                                Utils.user.setCoursesCompleted((Integer.parseInt(Utils.user.getCoursesCompleted()) + 1) + "");
                                HashMap<String, String> parameters = new HashMap<>();
                                parameters.put("courses_completed", Utils.user.getCoursesCompleted()) ;
                                Call<Void> call1 = api.updateAccount(APIKEY, parameters, "eq." + Utils.user.getFullName());
                                call1.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });
                            }
                        });
                    }
                    else {
                        for (int i = 1; i <= courseExercisesCount; i++) {
                            LinearLayout exerciseIndicator = (LinearLayout) getLayoutInflater().inflate(R.layout.course_exercise_indicator, null);
                            TextView exerciseNum = exerciseIndicator.findViewById(R.id.course_exercise_num);
                            exerciseNum.setText(i + "");

                            if (rightCompleteList.get(i - 1) == 1) exerciseIndicator.setBackgroundColor(Color.parseColor("#2EFF00"));
                            if (rightCompleteList.get(i - 1) == 0) exerciseIndicator.setBackgroundColor(Color.parseColor("#FF2525"));

                            exercisesIndicatorContainer.addView(exerciseIndicator);
                        }
                        for (int i = 0; i < courseExercisesCount; i++) {
                            LinearLayout exerciseLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.course_exercise_to_complete, null);
                            Exercise exercise = courseExercises.get(i);

                            TextView exerciseTitle = exerciseLayout.findViewById(R.id.exercise_title);
                            exerciseTitle.setText(exercise.getExerciseTitle());

                            LinearLayout exerciseSubtitleAndText = exerciseLayout.findViewById(R.id.subtitle_and_text_container);
                            for (int j = 0; j < exercise.getExerciseSubtitleAndText().size(); j++) {
                                LinearLayout subtitleAndTextLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.exercise_subtitle_and_text, null);
                                SubtitleAndText subtitleAndText = exercise.getExerciseSubtitleAndText().get(j);
                                if (subtitleAndText.getExerciseSubtitle() != null) {
                                    TextView subtitle = subtitleAndTextLayout.findViewById(R.id.exercise_subtitle);
                                    subtitle.setText(subtitleAndText.getExerciseSubtitle());
                                    subtitle.setVisibility(View.VISIBLE);
                                }
                                TextView text = subtitleAndTextLayout.findViewById(R.id.exercise_text);
                                text.setText(subtitleAndText.getExerciseText());
                                exerciseSubtitleAndText.addView(subtitleAndTextLayout);
                            }

                            if (exercise.getExerciseType().equals("Практика")) {
                                if (exercise.getExerciseAnswerType().equals("Выбор ответа")) {
                                    List<String> answerVariantsList = exercise.getExerciseAnswerVariants();
                                    if (exercise.getExerciseRightAnswers().size() == 1) {
                                        RadioGroup answerVariants = exerciseLayout.findViewById(R.id.answer_variants_container);
                                        for (int j = 0; j < answerVariantsList.size(); j++) {
                                            RadioButton answerVariant = new RadioButton(getApplicationContext());
                                            answerVariant.setText(answerVariantsList.get(j));
                                            answerVariants.addView(answerVariant);
                                        }
                                    }
                                    else {
                                        LinearLayout answerVariantsLayout = exerciseLayout.findViewById(R.id.answer_variants_layout);
                                        for (int j = 0; j < answerVariantsList.size(); j++) {
                                            CheckBox answerVariant = new CheckBox(getApplicationContext());
                                            answerVariant.setText(answerVariantsList.get(j));
                                            answerVariantsLayout.addView(answerVariant);
                                        }
                                    }
                                }
                                else {
                                    LinearLayout answersInputContainer = exerciseLayout.findViewById(R.id.right_answers_input_container);
                                    answersInputContainer.setVisibility(View.VISIBLE);

                                    exerciseLayout.findViewById(R.id.button_add_answer_input).setVisibility(View.GONE);
                                    exerciseLayout.findViewById(R.id.button_remove_answer_input).setVisibility(View.GONE);
                                }
                            }

                            exercisesContainer.addView(exerciseLayout);
                        }
                        completeButton.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<List<ExerciseComplete>> call, Throwable t) {

                }
            });
        }
    }

    public void HomeFromCourseComplete(View view) {
        startActivity(new Intent(CourseCompleteActivity.this, HomeActivity.class));
    }
}