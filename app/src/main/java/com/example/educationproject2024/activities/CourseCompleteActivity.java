package com.example.educationproject2024.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.educationproject2024.R;
import com.example.educationproject2024.data.Course;
import com.example.educationproject2024.data.Exercise;
import com.example.educationproject2024.data.SubtitleAndText;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class CourseCompleteActivity extends AppCompatActivity {

    TextView courseNameAsTitle;
    LinearLayout exercisesIndicatorContainer, exercisesContainer;
    MaterialButton completeButton;

    int practicalCount = 0;
    int rightCompleteCount = 0;
    List<Integer> rightCompleteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_complete);

        courseNameAsTitle = findViewById(R.id.course_name_as_title);
        exercisesIndicatorContainer = findViewById(R.id.exercises_button_container);
        exercisesContainer = findViewById(R.id.exercises_container);
        completeButton = findViewById(R.id.button_complete);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String courseName = extras.getString("course_name");
            int courseExercisesCount = Integer.parseInt(extras.getString("course_exercises_count"));

            List<Exercise> courseExercises = ((Course) extras.getSerializable("course_exercises")).getExercises();

            courseNameAsTitle.setText(courseName);

            for (int i = 1; i <= courseExercisesCount; i++) {
                LinearLayout exerciseButton = (LinearLayout) getLayoutInflater().inflate(R.layout.course_exercise_indicator, null);
                TextView exerciseNum = exerciseButton.findViewById(R.id.course_exercise_num);
                exerciseNum.setText(i + "");
                exercisesIndicatorContainer.addView(exerciseButton);
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
                                RadioButton answerVariant = new RadioButton(this);
                                answerVariant.setText(answerVariantsList.get(j));
                                answerVariants.addView(answerVariant);
                            }
                        }
                        else {
                            LinearLayout answerVariantsLayout = exerciseLayout.findViewById(R.id.answer_variants_layout);
                            for (int j = 0; j < answerVariantsList.size(); j++) {
                                CheckBox answerVariant = new CheckBox(this);
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
                    for (int i = 0; i < exercisesContainer.getChildCount(); i++) {
                        if (courseExercises.get(i).getExerciseType().equals("Практика")) {
                            practicalCount++;
                            Exercise exercise = courseExercises.get(i);
                            List<String> rightAnswers = exercise.getExerciseRightAnswers();
                            if (exercise.getExerciseAnswerType().equals("Выбор ответа")) {
                                List<String> selectedAnswers = new ArrayList<>();
                                if (rightAnswers.size() == 1) {
                                    RadioGroup answerVariants = exercisesContainer.getChildAt(i).findViewById(R.id.answer_variants_container);
                                    for (int j = 0; j < answerVariants.getChildCount(); j++) {
                                        RadioButton answerVariant = (RadioButton) answerVariants.getChildAt(j);
                                        if (answerVariant.isChecked()) selectedAnswers.add(answerVariant.getText().toString());
                                    }
                                    if (rightAnswers.equals(selectedAnswers)) {
                                        rightCompleteCount++;
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
                                        if (answerVariant.isChecked()) selectedAnswers.add(answerVariant.getText().toString());
                                    }
                                    if (rightAnswers.equals(selectedAnswers)) {
                                        rightCompleteCount++;
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
                                    rightCompleteCount++;
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
                            if (rightCompleteList.get(i) == 1) {
                                exerciseIndicator.setBackgroundColor(Color.parseColor("#2EFF00"));
                            }
                            else {
                                exerciseIndicator.setBackgroundColor(Color.parseColor("#FF2525"));
                            }
                        }
                    }
                    Toast.makeText(CourseCompleteActivity.this, rightCompleteCount + " / " + practicalCount, Toast.LENGTH_SHORT).show();
                    practicalCount = 0;
                    rightCompleteCount = 0;
                    rightCompleteList = new ArrayList<>();
                }
            });
        }
    }

    public void HomeFromCourseComplete(View view) {
        startActivity(new Intent(CourseCompleteActivity.this, HomeActivity.class));
    }
}