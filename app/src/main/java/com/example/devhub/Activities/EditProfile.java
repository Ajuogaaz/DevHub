package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.devhub.R;
import com.example.devhub.databinding.ActivityEditProfileBinding;
import com.example.devhub.databinding.ActivityProfileBinding;

public class EditProfile extends AppCompatActivity {

    ActivityEditProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);





    }
}