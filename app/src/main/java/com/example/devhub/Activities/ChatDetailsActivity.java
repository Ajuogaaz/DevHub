package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.devhub.Adapters.ChatDetailsAdapter;
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

    ChatDetailsAdapter chatDetailsAdapter;

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

        chatDetailsAdapter = new ChatDetailsAdapter(this, AllChats);

        binding.rvChats.setAdapter(chatDetailsAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        binding.rvChats.setLayoutManager(linearLayoutManager);


    }

    private void LoadAllMessages() {

        Messages.queryMessages(recepient, ParseUser.getCurrentUser(), (FindCallback<Messages>)(newfollowers, e) -> {
            if (e != null) {
                return;
            }
            AllChats.addAll(newfollowers);
            chatDetailsAdapter.notifyDataSetChanged();

        });

    }

    private void backtoChats() {
        startActivity(new Intent(ChatDetailsActivity.this, ChatActivity.class));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

    }


}