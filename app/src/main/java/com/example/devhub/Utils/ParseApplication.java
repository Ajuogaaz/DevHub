package com.example.devhub.Utils;

import android.app.Application;

import com.example.devhub.Models.Comments;
import com.example.devhub.Models.Followers;
import com.parse.Parse;
import com.parse.ParseObject;
import com.example.devhub.Models.Post;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ///Register your parse models
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Comments.class);
        ParseObject.registerSubclass(Followers.class);

        // Use for monitoring Parse network traffic
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // any network interceptors must be added with the Configuration Builder given this syntax
        builder.networkInterceptors().add(httpLoggingInterceptor);





        // set applicationId, and server server based on the values in the Heroku settings.
        // clientKey is not needed unless explicitly configured
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("devh-ub") // should correspond to APP_ID env variable
                .clientKey("key")  // set explicitly unless clientKey is explicitly configured on Parse server
                .server("https://devh-ub.herokuapp.com/parse/").build());

    }
}
