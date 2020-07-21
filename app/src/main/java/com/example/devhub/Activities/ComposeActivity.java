package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.devhub.R;
import com.example.devhub.databinding.ActivityComposeBinding;
import com.example.devhub.databinding.ActivityProfileBinding;

import org.jetbrains.annotations.NotNull;

public class ComposeActivity extends AppCompatActivity {

    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());

        final View view = binding.getRoot();
        setContentView(view);


    }
}