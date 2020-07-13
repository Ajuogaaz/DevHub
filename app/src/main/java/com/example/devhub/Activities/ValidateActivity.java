package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.devhub.R;
import com.example.devhub.databinding.ActivityValidateBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidateActivity extends AppCompatActivity {

    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate);

        ActivityValidateBinding binding = ActivityValidateBinding.inflate(getLayoutInflater());

        initViews();

        btnLogin.setOnClickListener(v -> initiateGithubLogin());
    }

    private void initiateGithubLogin() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_URI +
                "?client_id=" + CLIENT_ID + "&scope=repo&redirect_uri=" + REDIRECT_URI)
        );
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                    showHomePage(accessToken);
                } else {
                    Toast.makeText(MainActivity.this, "Request denied", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showHomePage(AccessToken accessToken) {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.putExtra("accessToken", accessToken);
        startActivity(intent);
    }

}