package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.devhub.Models.MessageTop;
import com.example.devhub.R;
import com.example.devhub.databinding.ActivityChatBinding;
import com.example.devhub.databinding.ActivityComposeBinding;
import com.parse.FindCallback;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;

    List<MessageTop> TopMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatBinding.inflate(getLayoutInflater());

        final View view = binding.getRoot();
        TopMessages = new ArrayList<>();
        setContentView(view);

        loadTopMessages();




    }

    private void loadTopMessages() {


        MessageTop.queryTopMessages(ParseUser.getCurrentUser(), (FindCallback<MessageTop>)(newfollowers, e) -> {
            if (e != null) {
                return;
            }
            TopMessages.addAll(newfollowers);
        });


    }
}