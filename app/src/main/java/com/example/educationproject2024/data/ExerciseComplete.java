package com.example.educationproject2024.data;

import com.google.gson.annotations.SerializedName;

public class ExerciseComplete {

    @SerializedName("user_name")
    public String userName;
    @SerializedName("course_name")
    public String courseName;
    @SerializedName("exercises_count")
    public String exercisesCount;
    @SerializedName("exercise_complete_status")
    public String exerciseCompleteStatus;

    public ExerciseComplete() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getExercisesCount() {
        return exercisesCount;
    }

    public void setExercisesCount(String exercisesCount) {
        this.exercisesCount = exercisesCount;
    }

    public String getExerciseCompleteStatus() {
        return exerciseCompleteStatus;
    }

    public void setExerciseCompleteStatus(String exerciseCompleteStatus) {
        this.exerciseCompleteStatus = exerciseCompleteStatus;
    }
}
