package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.devhub.Models.User;
import com.example.devhub.R;
import com.example.devhub.Models.AccessToken;
import com.example.devhub.network.ApiClient;
import com.example.devhub.network.ApiService;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.devhub.Utils.Constants.AUTH_URL;
import static com.example.devhub.Utils.Constants.CLIENT_ID;
import static com.example.devhub.Utils.Constants.CLIENT_SECRET;
import static com.example.devhub.Utils.Constants.GITHUB_URI;
import static com.example.devhub.Utils.Constants.REDIRECT_URI;

public class ValidateActivity extends AppCompatActivity {

   Button btn;
    private static final String TAG = "ValidateActivity";
    ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Get the current user
        user = ParseUser.getCurrentUser();

        //Check if the user is Null -- meaning logged out
        //If thats the case then go to login activity
        if(user == null){
            toLoginActivity();
        }
        else{
            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
        }
        //Check if the Has token meaning its not a new user, Then push them to main activity
        if (user.getBoolean("HasToken")){
            showHomePage();
        }

        //Run this remaining code if and only if its a new user from Register activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate);

        btn = findViewById(R.id.btn_Login);
        btn.setOnClickListener(view -> initiateGithubLogin());
    }

    private void toLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }


    //This is the method that calls and redirects github logins
    //We use the variables saved in constants
    private void initiateGithubLogin() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_URI +
                "?client_id=" + CLIENT_ID + "&scope=repo&redirect_uri=" + REDIRECT_URI)
        );
        startActivity(intent);
    }

    //We use retrofit to deal with the response of the data from the passed intent
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ONRESUMECALLED");
        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(REDIRECT_URI)) {
            String code = uri.getQueryParameter("code");
            retroBuilder(code);
        }
    }


    private void retroBuilder(String code) {
        ApiClient apiClient = ApiService.getApiClient();

        Call<AccessToken> call = apiClient.getAccessToken(CLIENT_ID, CLIENT_SECRET, code);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AccessToken accessToken = response.body();
                    updateUserInfo(accessToken);
                    showHomePage();
                } else {
                    Toast.makeText(ValidateActivity.this, R.string.RequestDenied, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Toast.makeText(ValidateActivity.this, R.string.connectionFailure, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showHomePage() {
        Intent intent = new Intent(ValidateActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void updateUserInfo(AccessToken accessToken) {

        user.put("Token", accessToken.getAccessToken());
        user.put("HasToken", true);

        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Toast.makeText(ValidateActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getUserInfo(String currentUserToken) {
        if (!currentUserToken.isEmpty()) {

            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
            okHttpClient.addInterceptor(chain -> {
                Request request = chain.request();
                Request.Builder newRequest = request.newBuilder().header(
                        "Authorization",
                        "token " + currentUserToken);
                return chain.proceed(newRequest.build());
            }).build();

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(AUTH_URL)
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            ApiClient apiClient = retrofit.create(ApiClient.class);
            apiClient.getUserDetails().enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        User user = response.body();
                        getUserRepositories(user.getUsername());
                        Toast.makeText(getContext(), "Data collected", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(
                                getContext(),
                                "Please try again",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(
                            getContext(),
                            "Check your connection",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        }
    }




}