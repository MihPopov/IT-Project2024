package com.example.educationproject2024.data;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class CourseAdditional implements Cloneable {
    @SerializedName("name")
    public String name;
    @SerializedName("description")
    public String description;
    @SerializedName("subject")
    public String subject;
    @SerializedName("exercises_count")
    public String exercisesCount;
    @SerializedName("exercise_number")
    public String exerciseNumber;
    @SerializedName("exercise_type")
    public String exerciseType;
    @SerializedName("exercise_answer_type")
    public String exerciseAnswerType;
    @SerializedName("exercise_title")
    public String exerciseTitle;
    @SerializedName("exercise_subtitle")
    public String exerciseSubtitle;
    @SerializedName("exercise_text")
    public String exerciseText;
    @SerializedName("exercise_answer_variant")
    public String exerciseAnswerVariant;
    @SerializedName("exercise_right_answer")
    public String exerciseRightAnswer;

    public CourseAdditional() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getExercisesCount() {
        return exercisesCount;
    }

    public void setExercisesCount(String exercisesCount) {
        this.exercisesCount = exercisesCount;
    }

    public String getExerciseNumber() {
        return exerciseNumber;
    }

    public void setExerciseNumber(String exerciseNumber) {
        this.exerciseNumber = exerciseNumber;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    public String getExerciseAnswerType() {
        return exerciseAnswerType;
    }

    public void setExerciseAnswerType(String exerciseAnswerType) {
        this.exerciseAnswerType = exerciseAnswerType;
    }

    public String getExerciseTitle() {
        return exerciseTitle;
    }

    public void setExerciseTitle(String exerciseTitle) {
        this.exerciseTitle = exerciseTitle;
    }

    public String getExerciseSubtitle() {
        return exerciseSubtitle;
    }

    public void setExerciseSubtitle(String exerciseSubtitle) {
        this.exerciseSubtitle = exerciseSubtitle;
    }

    public String getExerciseText() {
        return exerciseText;
    }

    public void setExerciseText(String exerciseText) {
        this.exerciseText = exerciseText;
    }

    public String getExerciseAnswerVariant() {
        return exerciseAnswerVariant;
    }

    public void setExerciseAnswerVariant(String exerciseAnswerVariant) {
        this.exerciseAnswerVariant = exerciseAnswerVariant;
    }

    public String getExerciseRightAnswer() {
        return exerciseRightAnswer;
    }

    public void setExerciseRightAnswer(String exerciseRightAnswer) {
        this.exerciseRightAnswer = exerciseRightAnswer;
    }

    @Override
    public String toString() {
        return "CourseAdditional{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", subject='" + subject + '\'' +
                ", exercisesCount='" + exercisesCount + '\'' +
                ", exerciseNumber='" + exerciseNumber + '\'' +
                ", exerciseType='" + exerciseType + '\'' +
                ", exerciseAnswerType='" + exerciseAnswerType + '\'' +
                ", exerciseTitle='" + exerciseTitle + '\'' +
                ", exerciseSubtitle='" + exerciseSubtitle + '\'' +
                ", exerciseText='" + exerciseText + '\'' +
                ", exerciseAnswerVariant='" + exerciseAnswerVariant + '\'' +
                ", exerciseRightAnswer='" + exerciseRightAnswer + '\'' +
                '}';
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
