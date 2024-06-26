package com.example.educationproject2024.activities;

import static com.example.educationproject2024.Utils.APIKEY;
import static com.example.educationproject2024.Utils.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.educationproject2024.data.CourseAdditional;
import com.example.educationproject2024.data.Exercise;
import com.example.educationproject2024.data.SubtitleAndText;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CourseCreationActivity extends AppCompatActivity {

    EditText courseName, courseDescription, courseExercisesCount;
    Spinner courseSubject;
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
        courseSubject = findViewById(R.id.spinnerCourseSubject);
        nextStep = findViewById(R.id.button_next1);
        courseCreate = findViewById(R.id.button_course_create);

        String[] items = new String[]{"Математика", "История", "Информатика", "Химия"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        courseSubject.setAdapter(adapter);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = courseName.getText().toString();
                String description = courseDescription.getText().toString();
                if (name.equals("") || description.equals("") || courseExercisesCount.getText().toString().equals("")) {
                    Toast.makeText(CourseCreationActivity.this, "Заполните все поля корректно!", Toast.LENGTH_SHORT).show();
                }
                else {
                    int exercisesCount = Integer.parseInt(courseExercisesCount.getText().toString());
                    s2.removeAllViews();
                    for (int i = 1; i <= exercisesCount; i++) {
                        LinearLayout keyPar = (LinearLayout) getLayoutInflater().inflate(R.layout.course_exercise, null);
                        LinearLayout answerType = keyPar.findViewById(R.id.exercise_answer_type);
                        LinearLayout rightAnswers = keyPar.findViewById(R.id.right_answers);
                        LinearLayout answerVariantsList = keyPar.findViewById(R.id.answer_variants);
                        TextView exerciseNum = keyPar.findViewById(R.id.exercise_num);
                        exerciseNum.setText(i + "");

                        for (int j = 1; j < 3; j++) {
                            LinearLayout answerVariant = (LinearLayout) getLayoutInflater().inflate(R.layout.course_answer, null);
                            answerVariantsList.addView(answerVariant, answerVariantsList.getChildCount() - 1);
                            TextView answerNum = answerVariant.findViewById(R.id.answer_num);
                            answerNum.setText(j + "");
                        }

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
                                if (answerVariantsList.getChildCount() > 3) answerVariantsList.removeViewAt(answerVariantsList.getChildCount() - 2);
                            }
                        });

                        LinearLayout rightAnswer = (LinearLayout) getLayoutInflater().inflate(R.layout.course_right_answer, null);
                        rightAnswers.addView(rightAnswer, rightAnswers.getChildCount() - 1);

                        MaterialButton addRightAnswerButton = keyPar.findViewById(R.id.button_add_new_right_var);
                        MaterialButton removeRightAnswerButton = keyPar.findViewById(R.id.button_remove_right_var);
                        addRightAnswerButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LinearLayout rightAnswerVariant = (LinearLayout) getLayoutInflater().inflate(R.layout.course_right_answer, null);
                                rightAnswers.addView(rightAnswerVariant, rightAnswers.getChildCount() - 1);
                                TextView rightAnswerNum = rightAnswerVariant.findViewById(R.id.right_answer_num);
                                rightAnswerNum.setText(rightAnswers.getChildCount() - 1 + "");
                            }
                        });
                        removeRightAnswerButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rightAnswers.getChildCount() > 2) rightAnswers.removeViewAt(rightAnswers.getChildCount() - 2);
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
                                    rightAnswers.setVisibility(View.VISIBLE);
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
                                    rightAnswers.setVisibility(View.GONE);
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
                    if (exercisesCount > 0) courseCreate.setVisibility(View.VISIBLE);
                }
            }
        });

        courseCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = courseName.getText().toString();
                String description = courseDescription.getText().toString();
                String subject = courseSubject.getSelectedItem().toString();
                List<Exercise> exercises = new ArrayList<>();
                if (name.equals("") || description.equals("") || courseExercisesCount.getText().toString().equals("")) {
                    Toast.makeText(CourseCreationActivity.this, "Заполните все поля корректно!", Toast.LENGTH_SHORT).show();
                }
                else {
                    int exercisesCount = Integer.parseInt(courseExercisesCount.getText().toString());
                    boolean isAlright = true;
                    for (int i = 1; i <= exercisesCount; i++) {
                        Exercise exercise = new Exercise();
                        LinearLayout exercisesLayout = (LinearLayout) s2.getChildAt(i - 1);
                        Spinner exerciseTypeSpinner = exercisesLayout.findViewById(R.id.spinner_exercise_type);
                        String exerciseType = exerciseTypeSpinner.getSelectedItem().toString();
                        exercise.setExerciseNumber(i + "");
                        exercise.setExerciseType(exerciseType);
                        if (exerciseType.equals("Практика")) {
                            Spinner exerciseAnswerTypeSpinner = exercisesLayout.findViewById(R.id.spinner_exercise_answer_type);
                            String exerciseAnswerType = exerciseAnswerTypeSpinner.getSelectedItem().toString();
                            exercise.setExerciseAnswerType(exerciseAnswerType);
                        }
                        String exerciseTitle = ((EditText) exercisesLayout.findViewById(R.id.editTextTitle)).getText().toString();
                        exercise.setExerciseTitle(exerciseTitle);
                        LinearLayout exerciseSubtitleAndTextLayout = exercisesLayout.findViewById(R.id.subtitle_and_text_list);
                        List<SubtitleAndText> exerciseSubtitlesAndText = new ArrayList<>();
                        for (int j = 0; j < exerciseSubtitleAndTextLayout.getChildCount() - 1; j++) {
                            SubtitleAndText subtitleAndText = new SubtitleAndText();
                            subtitleAndText.setExerciseSubtitle(((EditText) exerciseSubtitleAndTextLayout.getChildAt(j).findViewById(R.id.editTextSubtitle)).getText().toString());
                            String s = ((EditText) exerciseSubtitleAndTextLayout.getChildAt(j).findViewById(R.id.editTextTheoryText)).getText().toString();
                            if (s.equals("")) {
                                Toast.makeText(CourseCreationActivity.this, "Заполните все поля корректно!", Toast.LENGTH_SHORT).show();
                                isAlright = false;
                                break;
                            }
                            else {
                                subtitleAndText.setExerciseText(s);
                                exerciseSubtitlesAndText.add(subtitleAndText);
                            }
                        }
                        if (!isAlright) break;
                        exercise.setExerciseSubtitleAndText(exerciseSubtitlesAndText);
                        if (exerciseType.equals("Практика")) {
                            Spinner exerciseAnswerTypeSpinner = exercisesLayout.findViewById(R.id.spinner_exercise_answer_type);
                            String exerciseAnswerType = exerciseAnswerTypeSpinner.getSelectedItem().toString();
                            if (exerciseAnswerType.equals("Выбор ответа")) {
                                LinearLayout answerVariants = exercisesLayout.findViewById(R.id.answer_variants);
                                List<String> answerVariantsList = new ArrayList<>();
                                for (int k = 0; k < answerVariants.getChildCount() - 1; k++) {
                                    String s = ((EditText) answerVariants.getChildAt(k).findViewById(R.id.editTextAnswer)).getText().toString();
                                    if (s.equals("")) {
                                        Toast.makeText(CourseCreationActivity.this, "Заполните все поля корректно!", Toast.LENGTH_SHORT).show();
                                        isAlright = false;
                                        break;
                                    }
                                    answerVariantsList.add(s);
                                }
                                exercise.setExerciseAnswerVariants(answerVariantsList);
                                if (!isAlright) break;
                            }
                            LinearLayout rightAnswers = exercisesLayout.findViewById(R.id.right_answers);
                            List<String> rightAnswersList = new ArrayList<>();
                            for (int k = 0; k < rightAnswers.getChildCount() - 1; k++) {
                                String s = ((EditText) rightAnswers.getChildAt(k).findViewById(R.id.editTextRightAnswer)).getText().toString();
                                if (s.equals("")) {
                                    Toast.makeText(CourseCreationActivity.this, "Заполните все поля корректно!", Toast.LENGTH_SHORT).show();
                                    isAlright = false;
                                    break;
                                }
                                rightAnswersList.add(s);
                            }
                            exercise.setExerciseRightAnswers(rightAnswersList);
                            if (!isAlright) break;
                        }
                        exercises.add(exercise);
                    }
                    if (isAlright) {
                        for (int i = 0; i < exercisesCount; i++) {
                            CourseAdditional courseAdditional = new CourseAdditional();
                            courseAdditional.setName(name);
                            courseAdditional.setDescription(description);
                            courseAdditional.setSubject(subject);
                            courseAdditional.setExercisesCount(Integer.toString(exercisesCount));
                            courseAdditional.setExerciseNumber(exercises.get(i).getExerciseNumber());
                            courseAdditional.setExerciseType(exercises.get(i).getExerciseType());
                            courseAdditional.setExerciseAnswerType(exercises.get(i).getExerciseAnswerType());
                            courseAdditional.setExerciseTitle(exercises.get(i).getExerciseTitle());
                            List<SubtitleAndText> subtitleAndTextList = exercises.get(i).getExerciseSubtitleAndText();
                            List<String> exerciseAnswerVariants = exercises.get(i).getExerciseAnswerVariants();
                            List<String> exerciseRightAnswers = exercises.get(i).getExerciseRightAnswers();
                            if (exercises.get(i).getExerciseType().equals("Практика")) {
                                if (exercises.get(i).getExerciseAnswerType().equals("Выбор ответа")) {
                                    for (int j = 0; j < Math.max(Math.max(subtitleAndTextList.size(), exerciseAnswerVariants.size()), exerciseRightAnswers.size()); j++) {
                                        CourseAdditional courseAdditional1 = null;
                                        try {
                                            courseAdditional1 = (CourseAdditional) courseAdditional.clone();
                                        } catch (CloneNotSupportedException e) {
                                            throw new RuntimeException(e);
                                        }
                                        if (j < subtitleAndTextList.size()) {
                                            courseAdditional1.setExerciseSubtitle(subtitleAndTextList.get(j).getExerciseSubtitle());
                                            courseAdditional1.setExerciseText(subtitleAndTextList.get(j).getExerciseText());
                                        } else {
                                            courseAdditional1.setExerciseSubtitle("");
                                            courseAdditional1.setExerciseText("");
                                        }
                                        if (j < exerciseAnswerVariants.size()) {
                                            courseAdditional1.setExerciseAnswerVariant(exerciseAnswerVariants.get(j));
                                        } else {
                                            courseAdditional1.setExerciseAnswerVariant("");
                                        }
                                        if (j < exerciseRightAnswers.size()) {
                                            courseAdditional1.setExerciseRightAnswer(exerciseRightAnswers.get(j));
                                        } else {
                                            courseAdditional1.setExerciseRightAnswer("");
                                        }
                                        Call<Void> call = api.createCourse(APIKEY, courseAdditional1);
                                        call.enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {

                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {
                                                Toast.makeText(CourseCreationActivity.this, "Произошла ошибка! Попробуйте ещё раз", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                                else {
                                    for (int j = 0; j < Math.max(subtitleAndTextList.size(), exerciseRightAnswers.size()); j++) {
                                        CourseAdditional courseAdditional1 = null;
                                        try {
                                            courseAdditional1 = (CourseAdditional) courseAdditional.clone();
                                        } catch (CloneNotSupportedException e) {
                                            throw new RuntimeException(e);
                                        }
                                        if (j < subtitleAndTextList.size()) {
                                            courseAdditional1.setExerciseSubtitle(subtitleAndTextList.get(j).getExerciseSubtitle());
                                            courseAdditional1.setExerciseText(subtitleAndTextList.get(j).getExerciseText());
                                        } else {
                                            courseAdditional1.setExerciseSubtitle("");
                                            courseAdditional1.setExerciseText("");
                                        }
                                        if (j < exerciseRightAnswers.size()) {
                                            courseAdditional1.setExerciseRightAnswer(exerciseRightAnswers.get(j));
                                        } else {
                                            courseAdditional1.setExerciseRightAnswer("");
                                        }
                                        Call<Void> call = api.createCourse(APIKEY, courseAdditional1);
                                        call.enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {

                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {
                                                Toast.makeText(CourseCreationActivity.this, "Произошла ошибка! Попробуйте ещё раз", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            }
                            else {
                                for (int j = 0; j < subtitleAndTextList.size(); j++) {
                                    CourseAdditional courseAdditional1 = null;
                                    try {
                                        courseAdditional1 = (CourseAdditional) courseAdditional.clone();
                                    } catch (CloneNotSupportedException e) {
                                        throw new RuntimeException(e);
                                    }
                                    courseAdditional1.setExerciseSubtitle(subtitleAndTextList.get(j).getExerciseSubtitle());
                                    courseAdditional1.setExerciseText(subtitleAndTextList.get(j).getExerciseText());
                                    Call<Void> call = api.createCourse(APIKEY, courseAdditional1);
                                    call.enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {

                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {
                                            Toast.makeText(CourseCreationActivity.this, "Произошла ошибка! Попробуйте ещё раз", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }
                        startActivity(new Intent(CourseCreationActivity.this, ProfileActivity.class));
                    }
                }
            }
        });
    }

    public void ProfileFromCourseCreation(View view) {
        startActivity(new Intent(CourseCreationActivity.this, ProfileActivity.class));
    }
}