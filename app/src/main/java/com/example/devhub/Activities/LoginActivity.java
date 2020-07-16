package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.devhub.databinding.ActivityLoginBinding;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {


    public static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());

        // layout of activity is stored in a special property called root
        View view = binding.getRoot();
        setContentView(view);


        binding.btnLogin.setOnClickListener(view1 -> {
            Log.i(TAG, "OnClick login button");
            String username = binding.etUsername.getText().toString();
            String password = binding.etPassword.getText().toString();
            Toast.makeText(LoginActivity.this, "login in", Toast.LENGTH_SHORT).show();
            loginUser(username, password);
        });

        //Send them back to validate activity to confirm if the user if they dont have login credentials
        if(ParseUser.getCurrentUser().getBoolean("HasToken")){
            goToMainActivity();
        }else {
        goToValidateActivity();
        }

    }

    private void loginUser(String username, String password) {

        ParseUser.logInInBackground(username, password, (user, e) -> {
            if(e != null){
                Log.e(TAG, "Issue with login" + e);
                return;
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

}