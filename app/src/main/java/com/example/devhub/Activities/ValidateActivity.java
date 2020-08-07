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
import com.example.devhub.databinding.ActivityValidateBinding;
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

    ParseUser user;

    ActivityValidateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = ParseUser.getCurrentUser();

        //Check if the user is Null -- meaning logged out
        //If thats the case then go to login activity
        if (user == null) {
            toLoginActivity();
        }
        binding = ActivityValidateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.gitHub.setOnClickListener(view1 -> initiateGithubLogin());
        binding.gitLab.setOnClickListener(view2 -> initiateGitlabLogin());
        binding.bitBucket.setOnClickListener(view3 -> initiateBitBucketLogin());
    }

    private void initiateBitBucketLogin() {
        Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show();
    }

    private void initiateGitlabLogin() {
        Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show();
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
        finish();
    }

}