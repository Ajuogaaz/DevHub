package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.devhub.Adapters.ChatsAdapter;
import com.example.devhub.Adapters.FollowersAdapter;
import com.example.devhub.Models.MessageTop;
import com.example.devhub.R;
import com.example.devhub.Utils.HideSystemWindow;
import com.example.devhub.databinding.ActivityChatBinding;
import com.example.devhub.databinding.ActivityComposeBinding;
import com.parse.FindCallback;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;

    List<MessageTop> TopMessages;

    ChatsAdapter chatsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatBinding.inflate(getLayoutInflater());

        final View view = binding.getRoot();
        TopMessages = new ArrayList<>();
        setContentView(view);

        loadTopMessages();

        binding.Previous.setOnClickListener(view1 -> {
            startActivity(new Intent(ChatActivity.this, ProfileActivity.class));
        });

        chatsAdapter = new ChatsAdapter(this, TopMessages, onClickListener);

        binding.rvChats.setAdapter(chatsAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        binding.rvChats.setLayoutManager(linearLayoutManager);
    }

    ChatsAdapter.onClickListener onClickListener = new ChatsAdapter.onClickListener() {
        @Override
        public void onMessageClick(int position, ParseUser user) {
            Intent intent = new Intent(ChatActivity.this, ChatDetailsActivity.class);
            intent.putExtra("user", user);
            intent.putExtra("topMessage", (Parcelable) TopMessages.get(position));
            startActivity(intent);

        }

        @Override
        public void onUserClick(int position, ParseUser user) {
            Intent intent = new Intent(ChatActivity.this, OtherProfileActivity.class);
            intent.putExtra("post", user);
            startActivity(intent);
        }
    };




    private void loadTopMessages() {


        MessageTop.queryTopMessages(ParseUser.getCurrentUser(), (FindCallback<MessageTop>)(newfollowers, e) -> {
            if (e != null) {
                return;
            }
            TopMessages.addAll(newfollowers);
            chatsAdapter.notifyDataSetChanged();
        });


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            HideSystemWindow.hideSystemUI(getWindow());
        }
    }


}