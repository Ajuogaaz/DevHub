package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.devhub.Adapters.ChatDetailsAdapter;
import com.example.devhub.Models.MessageTop;
import com.example.devhub.Models.Messages;
import com.example.devhub.R;
import com.example.devhub.Utils.DoneOnEditorActionListener;
import com.example.devhub.Utils.HideSystemWindow;
import com.example.devhub.databinding.ActivityChatDetailsBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class ChatDetailsActivity extends AppCompatActivity {

    ActivityChatDetailsBinding binding;

    ParseUser recepient;

    List<Messages> AllChats;

    ChatDetailsAdapter chatDetailsAdapter;

    MessageTop messageTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailsBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        recepient = getIntent().getParcelableExtra("user");
        messageTop = getIntent().getParcelableExtra("topMessage");


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


        binding.etMessage.setOnEditorActionListener(new DoneOnEditorActionListener());

        binding.send.setOnClickListener(view12 -> {

            String message = binding.etMessage.getText().toString();

            Messages messages = new Messages();
            messages.setSendingUser(ParseUser.getCurrentUser());
            messages.setReceivingUser(recepient);
            messages.setChatBody(message);

            messages.saveInBackground(e -> {
                if(e != null){
                    Toast.makeText(this, "not sent", Toast.LENGTH_SHORT).show();
                }
                chatDetailsAdapter.clear();
                LoadAllMessages();
            });
            
            messageTop.updateTopMessage(message);
            messageTop.saveInBackground(e -> {
                Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
            });




        });

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
            HideSystemWindow.hideSystemUI(getWindow());
        }
    }

}