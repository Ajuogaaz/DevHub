package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.devhub.R;
import com.example.devhub.databinding.ActivitySettingsBinding;
import com.parse.ParseUser;

public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        binding = ActivitySettingsBinding.inflate(getLayoutInflater());

        binding.btnLogout.setOnClickListener(view -> {
            Toast.makeText(this, "Logging Out", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
            ParseUser.logOut();
            startActivity(intent);
            finish();


        });


    }
}