package com.example.educationproject2024.controller;

import com.example.educationproject2024.data.CourseAdditional;
import com.example.educationproject2024.data.ExerciseComplete;
import com.example.educationproject2024.data.User;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @POST("accounts")
    Call<Void> createAccount(@Header("apikey") String apikey, @Body HashMap<String, String> parameters);

    @PATCH("accounts")
    Call<Void> updateAccount(@Header("apikey") String apikey, @Body HashMap<String, String> parameters, @Query("full_name") String name);

    @GET("accounts")
    Call<List<User>> getAccount(@Header("apikey") String apikey, @Query("select") String select);

    @POST("courses")
    Call<Void> createCourse(@Header("apikey") String apikey, @Body CourseAdditional course);

    @GET("courses")
    Call<List<CourseAdditional>> getCourses(@Header("apikey") String apikey, @Query("select") String select);

    @GET("courses")
    Call<List<CourseAdditional>> getCourse(@Header("apikey") String apikey, @Query("select") String select, @Query("name") String name);

    @POST("completed_courses")
    Call<Void> courseComplete(@Header("apikey") String apikey, @Body List<ExerciseComplete> course);

    @GET("completed_courses")
    Call<List<ExerciseComplete>> getCompletedCourses(@Header("apikey") String apikey, @Query("select") String select);
}
