package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.devhub.Models.MessageTop;
import com.example.devhub.R;
import com.example.devhub.databinding.ActivityChatBinding;
import com.example.devhub.databinding.ActivityComposeBinding;

import java.util.List;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;

    List<MessageTop>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatBinding.inflate(getLayoutInflater());

        final View view = binding.getRoot();
        setContentView(view);



    }
}