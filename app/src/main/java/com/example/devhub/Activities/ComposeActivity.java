package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.devhub.R;
import com.example.devhub.databinding.ActivityComposeBinding;
import com.example.devhub.databinding.ActivityProfileBinding;

import org.jetbrains.annotations.NotNull;

public class ComposeActivity extends AppCompatActivity {

    ActivityComposeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityComposeBinding.inflate(getLayoutInflater());

        final View view = binding.getRoot();
        setContentView(view);



        binding.Cancel.setOnClickListener(view1 -> {

            Toast.makeText(this, "Cancel Clicked", Toast.LENGTH_SHORT).show();

        });
        binding.toolbarPost.setOnClickListener(view2 ->{

            Toast.makeText(this, "Posting", Toast.LENGTH_SHORT).show();
        });


    }
}