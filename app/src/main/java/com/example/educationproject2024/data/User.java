package com.example.educationproject2024.data;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("full_name")
    public String fullName;
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;
    @SerializedName("courses_created")
    public String coursesCreated;
    @SerializedName("courses_completed")
    public String coursesCompleted;

    public User(String fullName, String email, String password, String coursesCreated, String coursesCompleted) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.coursesCreated = coursesCreated;
        this.coursesCompleted = coursesCompleted;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCoursesCreated() {
        return coursesCreated;
    }

    public void setCoursesCreated(String coursesCreated) {
        this.coursesCreated = coursesCreated;
    }

    public String getCoursesCompleted() {
        return coursesCompleted;
    }

    public void setCoursesCompleted(String coursesCompleted) {
        this.coursesCompleted = coursesCompleted;
    }
}
