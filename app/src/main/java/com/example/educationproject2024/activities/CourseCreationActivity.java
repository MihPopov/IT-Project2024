package com.example.educationproject2024.activities;

import static com.example.educationproject2024.Utils.APIKEY;
import static com.example.educationproject2024.Utils.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.educationproject2024.R;
import com.example.educationproject2024.controller.API;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CourseCreationActivity extends AppCompatActivity {

    EditText courseName, courseDescription, courseExercisesCount, courseSubject;
    Button nextStep, courseCreate;
    LinearLayout s2;

    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_creation);

        s2 = findViewById(R.id.course_exercises_s2);
        courseName = findViewById(R.id.editTextCourseName);
        courseDescription = findViewById(R.id.editTextCourseDescription);
        courseExercisesCount = findViewById(R.id.editTextCourseExercisesCount);
        courseSubject = findViewById(R.id.editTextCourseSubject);
        nextStep = findViewById(R.id.button_next1);
        courseCreate = findViewById(R.id.button_course_create);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseCreate.setVisibility(View.VISIBLE);
                String name = courseName.getText().toString();
                String description = courseDescription.getText().toString();
                String subject = courseSubject.getText().toString();
                if (name.equals("") || description.equals("") || subject.equals("") || courseExercisesCount.getText().toString().equals("")) {
                    Toast.makeText(CourseCreationActivity.this, "Заполните все поля корректно!", Toast.LENGTH_SHORT).show();
                }
                else {
                    int exercisesCount = Integer.parseInt(courseExercisesCount.getText().toString());
                    s2.removeAllViews();
                    for (int i = 1; i <= exercisesCount; i++) {
                        LinearLayout keyPar = (LinearLayout) getLayoutInflater().inflate(R.layout.course_exercise, null);
                        LinearLayout answerType = keyPar.findViewById(R.id.exercise_answer_type);
                        LinearLayout rightAnswer = keyPar.findViewById(R.id.right_answer);
                        LinearLayout answerVariantsList = keyPar.findViewById(R.id.answer_variants);
                        TextView exerciseNum = keyPar.findViewById(R.id.exercise_num);
                        exerciseNum.setText(i + "");

                        LinearLayout answerVariant = (LinearLayout) getLayoutInflater().inflate(R.layout.course_answer, null);
                        answerVariantsList.addView(answerVariant, answerVariantsList.getChildCount() - 1);

                        MaterialButton addAnswerButton = keyPar.findViewById(R.id.button_add_new_var);
                        MaterialButton removeAnswerButton = keyPar.findViewById(R.id.button_remove_var);
                        addAnswerButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LinearLayout answerVariant = (LinearLayout) getLayoutInflater().inflate(R.layout.course_answer, null);
                                answerVariantsList.addView(answerVariant, answerVariantsList.getChildCount() - 1);
                                TextView answerNum = answerVariant.findViewById(R.id.answer_num);
                                answerNum.setText(answerVariantsList.getChildCount() - 1 + "");
                            }
                        });
                        removeAnswerButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (answerVariantsList.getChildCount() > 2) answerVariantsList.removeViewAt(answerVariantsList.getChildCount() - 2);
                            }
                        });

                        Spinner dropAnswerType = keyPar.findViewById(R.id.spinner_exercise_answer_type);
                        String[] items = new String[]{"Выбор ответа", "Ввод ответа"};
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(keyPar.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
                        dropAnswerType.setAdapter(adapter);
                        Spinner exerciseType = keyPar.findViewById(R.id.spinner_exercise_type);
                        items = new String[]{"Теория", "Практика"};
                        adapter = new ArrayAdapter<>(keyPar.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
                        exerciseType.setAdapter(adapter);
                        exerciseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (parent.getItemAtPosition(position).equals("Практика")) {
                                    answerType.setVisibility(View.VISIBLE);
                                    rightAnswer.setVisibility(View.VISIBLE);
                                    dropAnswerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            if (parent.getItemAtPosition(position).equals("Выбор ответа")) {
                                                answerVariantsList.setVisibility(View.VISIBLE);
                                            }
                                            else {
                                                answerVariantsList.setVisibility(View.GONE);
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                }
                                else {
                                    answerType.setVisibility(View.GONE);
                                    answerVariantsList.setVisibility(View.GONE);
                                    rightAnswer.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        LinearLayout subtitleAndTextList = keyPar.findViewById(R.id.subtitle_and_text_list);
                        LinearLayout subtitleAndText = (LinearLayout) getLayoutInflater().inflate(R.layout.course_subtitle_and_text, null);
                        subtitleAndTextList.addView(subtitleAndText, subtitleAndTextList.getChildCount() - 1);

                        MaterialButton addButton = keyPar.findViewById(R.id.button_add_new);
                        MaterialButton removeButton = keyPar.findViewById(R.id.button_remove);
                        addButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LinearLayout subtitleAndText = (LinearLayout) getLayoutInflater().inflate(R.layout.course_subtitle_and_text, null);
                                subtitleAndTextList.addView(subtitleAndText, subtitleAndTextList.getChildCount() - 1);
                            }
                        });
                        removeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (subtitleAndTextList.getChildCount() > 2) subtitleAndTextList.removeViewAt(subtitleAndTextList.getChildCount() - 2);
                            }
                        });
                        s2.addView(keyPar);
                    }
                }
            }
        });

        courseCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = courseName.getText().toString();
                String description = courseDescription.getText().toString();
                String subject = courseSubject.getText().toString();
                ArrayList<Object> exercises = new ArrayList<>();
                if (name.equals("") || description.equals("") || subject.equals("") || courseExercisesCount.getText().toString().equals("")) {
                    Toast.makeText(CourseCreationActivity.this, "Заполните все поля корректно!", Toast.LENGTH_SHORT).show();
                }
                else {
                    int exercisesCount = Integer.parseInt(courseExercisesCount.getText().toString());
                    for (int i = 0; i < exercisesCount; i++) {
                        LinearLayout exercise = (LinearLayout) s2.getChildAt(i);
                        Spinner exerciseTypeSpinner = exercise.findViewById(R.id.spinner_exercise_type);
                        String exerciseType = exerciseTypeSpinner.getSelectedItem().toString();
                        ArrayList<Object> exerciseKeyPar = new ArrayList<>();
                        exerciseKeyPar.add(exerciseType);
                        if (exerciseType.equals("Практика")) {
                            Spinner exerciseAnswerTypeSpinner = exercise.findViewById(R.id.spinner_exercise_answer_type);
                            String exerciseAnswerType = exerciseAnswerTypeSpinner.getSelectedItem().toString();
                            exerciseKeyPar.add(exerciseAnswerType);
                        }
                        String exerciseTitle = ((EditText) exercise.findViewById(R.id.editTextTitle)).getText().toString();
                        exerciseKeyPar.add(exerciseTitle);
                        LinearLayout exerciseSubtitleAndTextLayout = exercise.findViewById(R.id.subtitle_and_text_list);
                        ArrayList<Object> exerciseSubtitlesAndText = new ArrayList<>();
                        for (int j = 0; j < exerciseSubtitleAndTextLayout.getChildCount() - 1; j++) {
                            exerciseSubtitlesAndText.add(((EditText) exerciseSubtitleAndTextLayout.getChildAt(j).findViewById(R.id.editTextSubtitle)).getText().toString());
                            String s = ((EditText) exerciseSubtitleAndTextLayout.getChildAt(j).findViewById(R.id.editTextTheoryText)).getText().toString();
                            if (s.equals("")) {
                                Toast.makeText(CourseCreationActivity.this, "Заполните все поля корректно!", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            else {
                                exerciseSubtitlesAndText.add(s);
                            }
                        }
                        exerciseKeyPar.add(exerciseSubtitlesAndText);
                        if (exerciseType.equals("Практика")) {
                            Spinner exerciseAnswerTypeSpinner = exercise.findViewById(R.id.spinner_exercise_answer_type);
                            String exerciseAnswerType = exerciseAnswerTypeSpinner.getSelectedItem().toString();
                            if (exerciseAnswerType.equals("Выбор ответа")) {
                                LinearLayout answerVariants = exercise.findViewById(R.id.answer_variants);
                                ArrayList<Object> answerVariantsList = new ArrayList<>();
                                for (int k = 0; k < answerVariants.getChildCount() - 1; k++) {
                                    String s = ((EditText) answerVariants.getChildAt(k).findViewById(R.id.editTextAnswer)).getText().toString();
                                    if (s.equals("")) {
                                        Toast.makeText(CourseCreationActivity.this, "Заполните все поля корректно!", Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                    answerVariantsList.add(s);
                                }
                                exerciseKeyPar.add(answerVariantsList);
                            }
                            String rightAnswer = ((EditText) exercise.findViewById(R.id.editTextRightAnswer)).getText().toString();
                            if (rightAnswer.equals("")) {
                                Toast.makeText(CourseCreationActivity.this, "Заполните все поля корректно!", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            exerciseKeyPar.add(rightAnswer);
                        }
                        exercises.add(exerciseKeyPar);
                    }
                    HashMap<String, String> parameters = new HashMap<>();
                    parameters.put("name", name);
                    parameters.put("description", description);
                    parameters.put("subject", subject);
                    parameters.put("exercises_count", Integer.toString(exercisesCount));
                    parameters.put("exercises", exercises + "");
                    Call<Void> call = api.createCourse(APIKEY, parameters);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            startActivity(new Intent(CourseCreationActivity.this, ProfileActivity.class));
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(CourseCreationActivity.this, "Произошла ошибка! Попробуйте ещё раз", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void ProfileFromCourseCreation(View view) {
        startActivity(new Intent(CourseCreationActivity.this, ProfileActivity.class));
    }
}