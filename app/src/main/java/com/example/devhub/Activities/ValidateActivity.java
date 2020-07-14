package com.example.devhub.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.devhub.R;
import com.example.devhub.Utils.AccessToken;
import com.example.devhub.databinding.ActivityValidateBinding;
import com.example.devhub.network.ApiClient;
import com.example.devhub.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.devhub.Utils.Constants.CLIENT_ID;
import static com.example.devhub.Utils.Constants.CLIENT_SECRET;
import static com.example.devhub.Utils.Constants.GITHUB_URI;
import static com.example.devhub.Utils.Constants.REDIRECT_URI;

public class ValidateActivity extends AppCompatActivity {

   Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate);

        btn = findViewById(R.id.btn_Login);

        //binding = ActivityValidateBinding.inflate(getLayoutInflater());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateGithubLogin();
            }
        });
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
                    Toast.makeText(ValidateActivity.this, R.string.RequestDenied, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Toast.makeText(ValidateActivity.this, R.string.connectionFailure, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showHomePage(AccessToken accessToken) {
        Intent intent = new Intent(ValidateActivity.this, MainActivity.class);
        intent.putExtra("accessToken", accessToken);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(REDIRECT_URI)) {
            String code = uri.getQueryParameter("code");
            retroBuilder(code);
        }
    }
}