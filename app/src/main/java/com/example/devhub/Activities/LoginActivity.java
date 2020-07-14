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

        //if(ParseUser.getCurrentUser() != null) {
          //  goToMainActivity();
        //}

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "OnClick login button");
                String username = binding.etUsername.getText().toString();
                String password = binding.etPassword.getText().toString();
                Toast.makeText(LoginActivity.this, "login in", Toast.LENGTH_SHORT).show();
                loginUser(username, password);
            }
        });
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Onclick dignIn");

                ParseUser user =  new ParseUser();

                user.setUsername(binding.etUsername.getText().toString());
                user.setPassword(binding.etPassword.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            goToValidateActivity();
                        }else{
                            Toast.makeText(LoginActivity.this, "User Already exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    private void loginUser(String username, String password) {

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with login" + e);
                    return;
                }

                //goToMainActivity();
                goToValidateActivity();
                //cd42332af24c8b381a2cf6452d87c807c0899d02

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