package com.example.devhub.network;

import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiClient {
    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")

}
