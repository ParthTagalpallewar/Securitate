package com.example.myapplication.api;

import com.example.myapplication.model.PushNotification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.example.myapplication.Constants.CONTENT_TYPE;
import static com.example.myapplication.Constants.SERVER_KEY;

public interface ApiInterface {

    @Headers({"Authorization: key=" + SERVER_KEY, "Content-Type:" + CONTENT_TYPE})
    @POST("fcm/send")
    Call<PushNotification> sendNotification(@Body PushNotification notification);

}
