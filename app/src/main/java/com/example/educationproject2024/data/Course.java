package com.example.educationproject2024.data;

import com.google.gson.annotations.SerializedName;

public class Course {

    @SerializedName("name")
    public String name;
    @SerializedName("description")
    public String description;
    @SerializedName("subject")
    public String subject;
    @SerializedName("exercises_count")
    public String exercisesCount;
    @SerializedName("exercises")
    public String exercises;

    public Course(String name, String description, String subject, String exercisesCount, String exercises) {
        this.name = name;
        this.description = description;
        this.subject = subject;
        this.exercisesCount = exercisesCount;
        this.exercises = exercises;
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

    public String getExercises() {
        return exercises;
    }

    public void setExercises(String exercises) {
        this.exercises = exercises;
    }
}
