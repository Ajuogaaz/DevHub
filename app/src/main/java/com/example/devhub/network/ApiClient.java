package com.example.devhub.network;

import com.example.devhub.Models.Repositories;
import com.example.devhub.Models.User;
import com.example.devhub.Models.AccessToken;
import com.example.devhub.Models.jobs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiClient {
    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    Call<AccessToken> getAccessToken(
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("code") String accessToken
    );

    @GET("user")
    Call<User> getUserDetails();

    @GET("users/{user}/repos")
    Call<List<Repositories>> getUserRepos(@Path("user") String user);



    @GET("positions.json")
    Call<List<jobs>> getAvailableJobs();

    @GET("positions.json")
    Call<List<jobs>> getQueriedJobs(
            @Query("description") String description,
            @Query("location") String location
    );
}
