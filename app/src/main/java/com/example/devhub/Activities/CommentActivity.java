package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

        binding.Cancel.setOnClickListener(view -> {

            Intent intent = new Intent(CommentActivity.this, DetailsActivity.class);
            intent.putExtra("post", SubjectPost);
            startActivity(intent);
            finish();
        });

        binding.


        final View view = binding.getRoot();
        setContentView(view);

    }
}