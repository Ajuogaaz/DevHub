package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.devhub.R;
import com.example.devhub.databinding.ActivityLoginBinding;
import com.example.devhub.databinding.ActivityRegisterBinding;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

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
            Log.i(TAG, "Onclick dignIn");

            ParseUser user =  new ParseUser();

            user.setUsername(binding.etUsername.getText().toString());
            user.setPassword(binding.etPassword.getText().toString());

            user.signUpInBackground(e -> {
                binding.reposLoader.setVisibility(View.GONE);
                binding.btnLogin.setVisibility(View.VISIBLE);
                binding.enterPassword.setVisibility(View.VISIBLE);
                binding.enterUserName.setVisibility(View.VISIBLE);

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
    }


    private void goToValidateActivity() {

        Intent intent = new Intent(RegisterActivity.this, ValidateActivity.class);
        startActivity(intent);
        finish();
    }



}