package com.example.educationproject2024.data;

import com.google.gson.annotations.SerializedName;

public class CourseAdditional {
    @SerializedName("name")
    public String name;
    @SerializedName("description")
    public String description;
    @SerializedName("subject")
    public String subject;
    @SerializedName("exercises_count")
    public String exercises_count;
    @SerializedName("exercise_type")
    public String exercise_type;
    @SerializedName("exercise_answer_type")
    public String exercise_answer_type;
    @SerializedName("exercise_title")
    public String exercise_title;
    @SerializedName("exercise_subtitle")
    public String exercise_subtitle;
    @SerializedName("exercise_text")
    public String exercise_text;
    @SerializedName("exercise_answer_variant")
    public String exercise_answer_variant;
    @SerializedName("exercise_right_answer")
    public String exercise_right_answer;

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

    public String getExercises_count() {
        return exercises_count;
    }

    public void setExercises_count(String exercises_count) {
        this.exercises_count = exercises_count;
    }

    public String getExercise_type() {
        return exercise_type;
    }

    public void setExercise_type(String exercise_type) {
        this.exercise_type = exercise_type;
    }

    public String getExercise_answer_type() {
        return exercise_answer_type;
    }

    public void setExercise_answer_type(String exercise_answer_type) {
        this.exercise_answer_type = exercise_answer_type;
    }

    public String getExercise_title() {
        return exercise_title;
    }

    public void setExercise_title(String exercise_title) {
        this.exercise_title = exercise_title;
    }

    public String getExercise_subtitle() {
        return exercise_subtitle;
    }

    public void setExercise_subtitle(String exercise_subtitle) {
        this.exercise_subtitle = exercise_subtitle;
    }

    public String getExercise_text() {
        return exercise_text;
    }

    public void setExercise_text(String exercise_text) {
        this.exercise_text = exercise_text;
    }

    public String getExercise_answer_variant() {
        return exercise_answer_variant;
    }

    public void setExercise_answer_variant(String exercise_answer_variant) {
        this.exercise_answer_variant = exercise_answer_variant;
    }

    public String getExercise_right_answer() {
        return exercise_right_answer;
    }

    public void setExercise_right_answer(String exercise_right_answer) {
        this.exercise_right_answer = exercise_right_answer;
    }

    @Override
    public String toString() {
        return "CourseAdditional{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", subject='" + subject + '\'' +
                ", exercises_count='" + exercises_count + '\'' +
                ", exercise_type='" + exercise_type + '\'' +
                ", exercise_answer_type='" + exercise_answer_type + '\'' +
                ", exercise_title='" + exercise_title + '\'' +
                ", exercise_subtitle='" + exercise_subtitle + '\'' +
                ", exercise_text='" + exercise_text + '\'' +
                ", exercise_answer_variant='" + exercise_answer_variant + '\'' +
                ", exercise_right_answer='" + exercise_right_answer + '\'' +
                '}';
    }
}
