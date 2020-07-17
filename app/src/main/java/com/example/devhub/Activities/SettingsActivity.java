package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.devhub.R;
import com.example.devhub.databinding.ActivitySettingsBinding;
import com.parse.ParseUser;

public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySettingsBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        binding.btnLogout.setOnClickListener(view -> {
            Toast.makeText(this, "Logging Out", Toast.LENGTH_SHORT).show();
            ParseUser.logOut();

            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();


        });


    }
}