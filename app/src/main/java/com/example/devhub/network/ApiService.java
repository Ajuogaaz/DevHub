package com.example.devhub.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class ApiService {

    public static final String BASE_URL = "https://github.com/";
    public static final String BASE_URL_REPOS = "https://api.github.com/";
    private static Retrofit sRetrofit = null;
    private static Retrofit userRepos = null;
    private static OkHttpClient sOkHttpClient;

}
