package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.devhub.R;
import com.example.devhub.databinding.ActivityBoadingBinding;

public class BoadingActivity extends AppCompatActivity {

    ActivityBoadingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoadingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}