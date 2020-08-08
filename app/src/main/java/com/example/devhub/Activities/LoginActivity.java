package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.devhub.Models.AccessToken;
import com.example.devhub.Models.User;
import com.example.devhub.R;
import com.example.devhub.databinding.ActivityLoginBinding;
import com.example.devhub.network.ApiClient;
import com.example.devhub.network.ApiService;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

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
import static com.example.devhub.Utils.Constants.REDIRECT_URI;

public class LoginActivity extends AppCompatActivity {


    public static final String TAG = "LoginActivity";
    ParseUser user;

    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        // layout of activity is stored in a special property called root
        View view = binding.getRoot();
        setContentView(view);

        user = ParseUser.getCurrentUser();

        if(user != null){
            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();

            if (user.getBoolean("HasToken")){
                showHomePage();
                finish();
            }
        }


        binding.btnLogin.setOnClickListener(view1 -> {
            Log.i(TAG, "OnClick login button");
            String username = binding.etUsername.getText().toString();
            String password = binding.etPassword.getText().toString();
            Toast.makeText(LoginActivity.this, "login in", Toast.LENGTH_SHORT).show();
            binding.reposLoader.setVisibility(View.VISIBLE);
            binding.btnLogin.setVisibility(View.GONE);
            binding.enterPassword.setVisibility(View.GONE);
            binding.enterUserName.setVisibility(View.GONE);
            loginUser(username, password);

        });

        binding.signUp.setOnClickListener(view2 -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

        });


    }

    private void loginUser(String username, String password) {

        ParseUser.logInInBackground(username, password, (user, e) -> {
            binding.reposLoader.setVisibility(View.GONE);
            binding.btnLogin.setVisibility(View.VISIBLE);
            binding.enterPassword.setVisibility(View.VISIBLE);
            binding.enterUserName.setVisibility(View.VISIBLE);
            if(e != null){
                Log.e(TAG, "Issue with login" + e);
                Toast.makeText(this, "Wrong credentials", Toast.LENGTH_SHORT).show();
                return;
            }else{
                //Send them back to validate activity to confirm if the user if they dont have login credentials
                if(ParseUser.getCurrentUser().getBoolean("HasToken")){
                    goToMainActivity();
                }else {
                    goToValidateActivity();
                }

            }
        });
    }

    private void goToValidateActivity() {

        Intent intent = new Intent(LoginActivity.this, ValidateActivity.class);
        startActivity(intent);
        finish();
    }
    private void goToMainActivity() {

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //We use retrofit to deal with the response of the data from the passed intent
    @Override
    protected void onResume() {
        super.onResume();
        if(ParseUser.getCurrentUser() == null){
            return;
        }
        binding.reposLoader.setVisibility(View.VISIBLE);
        binding.btnLogin.setVisibility(View.GONE);
        binding.enterPassword.setVisibility(View.GONE);
        binding.enterUserName.setVisibility(View.GONE);
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
                } else {
                    Toast.makeText(LoginActivity.this, R.string.RequestDenied, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Toast.makeText(LoginActivity.this, R.string.connectionFailure, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showHomePage() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void updateUserInfo(AccessToken accessToken) {
        getUserInfo(accessToken.getAccessToken());
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
                        User replyUser = response.body();
                        saveUserInfo(replyUser, currentUserToken);
                    } else {
                        Toast.makeText(
                                LoginActivity.this,
                                "Please try again",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(
                            LoginActivity.this,
                            "Check your connection",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        }else{
            Toast.makeText(this, "Your authentication was unsuccesfull", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserInfo(User replyUser, String currentUserToken) {

        user.put("Token", currentUserToken);

        user.put("HasToken", true);

        if(replyUser.getBio() != null) {
            user.put("Bio", replyUser.getBio());
        }else{
            user.put("Bio", "");
        }
        user.put("HasUploadedPic", false);

        if(replyUser.getAvatar() != null) {
            user.put("githubProfilePic", replyUser.getAvatar());
        }else{
            user.put("githubProfilePic", "");
        }

        if(replyUser.getName() != null){
            user.put("PreferredName", replyUser.getName());
        }else{
            user.put("PreferredName", user.getUsername());
        }
        if(replyUser.getUsername() != null){
            user.put("gitHubUserName", replyUser.getUsername());
        }else{
            user.put("gitHubUserName", user.getUsername());
        }

        user.put("NumberOfRepos", replyUser.getRepos());

        user.put("NumberOfPost", 0);


        user.put("NumberOfFollowing", 0);

        user.put("NumberOfFollowers", 0);


        user.saveInBackground(e -> {
            binding.reposLoader.setVisibility(View.GONE);
            binding.btnLogin.setVisibility(View.VISIBLE);
            binding.enterPassword.setVisibility(View.VISIBLE);
            binding.enterUserName.setVisibility(View.VISIBLE);
            if(e == null){
                Toast.makeText(LoginActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                goToMainActivity();
            }
        });
    }




}