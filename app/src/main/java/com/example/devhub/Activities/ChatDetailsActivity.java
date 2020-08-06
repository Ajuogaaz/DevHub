package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.devhub.Models.Messages;
import com.example.devhub.R;
import com.example.devhub.databinding.ActivityChatDetailsBinding;
import com.parse.FindCallback;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ChatDetailsActivity extends AppCompatActivity {

    ActivityChatDetailsBinding binding;

    ParseUser recepient;

    List<Messages> AllChats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailsBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        recepient = getIntent().getParcelableExtra("user");
        AllChats = new ArrayList<>();
        LoadAllMessages();


        String ImageUrl = "";

        if(recepient.getBoolean("HasUploadedPic")){
            ImageUrl = recepient.getParseFile("ProfilePic").getUrl();
        }else{
            ImageUrl = recepient.getString("githubProfilePic");
        }


        if(!ImageUrl.isEmpty()) {
            Glide.with(this)
                    .load(ImageUrl)
                    .into(binding.ivProfileImage);
        }

        binding.titleToolBar.setTitle(recepient.getString("PreferredName"));

        binding.Previous.setOnClickListener(view1 -> {
           backtoChats();
        });


    }

    private void LoadAllMessages() {

        Messages.queryMessages(recepient, ParseUser.getCurrentUser(), (FindCallback<Messages>)(newfollowers, e) -> {
            if (e != null) {
                return;
            }
            AllChats.addAll(newfollowers);

        });

    }

    private void backtoChats() {
        startActivity(new Intent(ChatDetailsActivity.this, ChatActivity.class));
    }
}