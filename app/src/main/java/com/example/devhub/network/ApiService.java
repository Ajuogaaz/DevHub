package com.example.devhub.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    public static final String BASE_URL = "https://github.com/";
    public static final String BASE_URL_REPOS = "https://api.github.com/";
    private static Retrofit sRetrofit = null;
    private static Retrofit userRepos = null;
    private static OkHttpClient sOkHttpClient;

    private static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            sOkHttpClient = logging(logging);

            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(sOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return sRetrofit;
    }

    private static OkHttpClient logging(HttpLoggingInterceptor logging) {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

    }

    private static Retrofit getUserRepos() {
        if (userRepos == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            sOkHttpClient = logging(logging);
            userRepos = new Retrofit.Builder()
                    .baseUrl(BASE_URL_REPOS)
                    .client(sOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return userRepos;
    }


}
