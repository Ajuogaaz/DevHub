package com.example.devhub.Utils;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.devhub.Activities.LoginActivity;
import com.example.devhub.Activities.MainActivity;
import com.parse.ParseUser;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, LoginActivity.class));

        finish();
    }
}
