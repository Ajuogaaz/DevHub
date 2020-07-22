package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.devhub.Models.Post;
import com.example.devhub.R;
import com.example.devhub.databinding.ActivityCommentBinding;
import com.example.devhub.databinding.ActivityDetailsBinding;
import com.parse.ParseUser;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    Post SubjectPost;
    ActivityCommentBinding binding;

    private static final String TAG = "COMMENTACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SubjectPost= getIntent().getParcelableExtra("post");

        ParseUser.getCurrentUser().fetchInBackground();

        binding = ActivityCommentBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        binding.Cancel.setOnClickListener(view2 -> {
            fireIntent();
        });

        binding.toolbarComment.setOnClickListener(view3 -> {

            saveComment();
            fireIntent();
        });

        initValues();



    }

    private void initValues() {

        String ImageUrl = "";

        if(SubjectPost.getUser().getBoolean("HasUploadedPic")){
            ImageUrl = SubjectPost.getUser().getParseFile("ProfilePic").getUrl();
        }else{
            ImageUrl = SubjectPost.getUser().getString("githubProfilePic");
        }

        if (!ImageUrl.isEmpty()) {
            Glide.with(CommentActivity.this)
                    .load(ImageUrl)
                    .into(binding.ivProfileImage);
        }

        binding.tvName.setText(SubjectPost.getUser().getString("PreferredName"));
        binding.gitHubUserName.setText(String.format("@%s", SubjectPost.getUser().getString("gitHubUserName")));
    }

    private void fireIntent() {

        Intent intent = new Intent(CommentActivity.this, DetailsActivity.class);
        intent.putExtra("post", SubjectPost);
        startActivity(intent);
        finish();
    }

    private void saveComment() {

    }
}