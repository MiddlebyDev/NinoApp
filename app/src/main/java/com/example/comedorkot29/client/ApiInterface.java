package com.example.comedorkot29.client;

import retrofit2.Call;
import retrofit2.http.Body;
import  retrofit2.http.GET;
import retrofit2.http.POST;

import com.example.comedorkot29.Answers;
import com.example.comedorkot29.QuestionsApiResponse;
import com.example.comedorkot29.ShiftApiResponse;

public interface ApiInterface {

    @GET("api/questions")
    Call<QuestionsApiResponse> getQuestion();

    @GET("api/shifts")
    Call<ShiftApiResponse> getShift();

    @POST("api/answers")
    Call<Answers> createAnswer(@Body Answers answers);
}
