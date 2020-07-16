package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.devhub.Models.Post;
import com.example.devhub.R;
import com.example.devhub.databinding.ActivityProfileBinding;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;

    List<Post> posts

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        binding = new ActivityProfileBinding();

        binding.btnEditProfile.setOnClickListener(view -> {

            Toast.makeText(this, "EditProfile", Toast.LENGTH_SHORT).show();

        });

        bin





    }
}