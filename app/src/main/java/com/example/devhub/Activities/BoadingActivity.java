package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;

import com.example.devhub.R;
import com.example.devhub.databinding.ActivityBoadingBinding;

public class BoadingActivity extends FragmentActivity {

    ActivityBoadingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoadingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}