package com.example.ayush.lazaranma;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    public  static  DetailsInterface apiInterface;
    private static Gson gson= new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create();
    public static DetailsInterface getApiInterface()
    {
        if(apiInterface!=null)
            return apiInterface;
        else
        {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://9fc47808.ngrok.io/")
                    .addConverterFactory(GsonConverterFactory.create(gson)).client(new OkHttpClient())
                    .build();

            apiInterface=retrofit.create(DetailsInterface.class);
            return apiInterface;
        }
    }
}
