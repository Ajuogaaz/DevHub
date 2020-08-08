package com.example.devhub.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.devhub.databinding.ActivityRegisterBinding;
import com.parse.ParseUser;

public class RegisterActivity extends AppCompatActivity {


    public static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final ActivityRegisterBinding binding = ActivityRegisterBinding.inflate(getLayoutInflater());

        // layout of activity is stored in a special property called root
        View view = binding.getRoot();
        setContentView(view);

        binding.btnLogin.setOnClickListener(view1 -> {
            binding.reposLoader.setVisibility(View.VISIBLE);
            binding.btnLogin.setVisibility(View.GONE);
            binding.enterPassword.setVisibility(View.GONE);
            binding.enterUserName.setVisibility(View.GONE);
            binding.enterEmail.setVisibility(View.GONE);
            Log.i(TAG, "Onclick dignIn");

            ParseUser user =  new ParseUser();

            user.setUsername(binding.etUsername.getText().toString());
            user.setPassword(binding.etPassword.getText().toString());
            user.setEmail(binding.etEmail.getText().toString());

            user.signUpInBackground(e -> {
                binding.reposLoader.setVisibility(View.GONE);
                binding.btnLogin.setVisibility(View.VISIBLE);
                binding.enterPassword.setVisibility(View.VISIBLE);
                binding.enterUserName.setVisibility(View.VISIBLE);
                binding.enterEmail.setVisibility(View.VISIBLE);

                if (e == null) {
                    goToValidateActivity();
                }else{
                    Toast.makeText(RegisterActivity.this, "User Already exists", Toast.LENGTH_SHORT).show();
                }
            });

        });

        binding.signUp.setOnClickListener(view2 -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

        });

        binding.btnTest.setOnClickListener(view3 -> {
            goToValidateActivity();
        });
    }


    private void goToValidateActivity() {

        Intent intent = new Intent(RegisterActivity.this, BoadingActivity.class);
        startActivity(intent);
        //finish();
    }



}