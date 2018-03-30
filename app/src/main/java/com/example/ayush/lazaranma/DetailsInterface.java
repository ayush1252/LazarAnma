package com.example.ayush.lazaranma;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DetailsInterface {
    @Headers("Content-Type: application/json")
    @POST("/offlineData")
    Call<DetailClass> details(@Body HashMap<String, String> user);

}
