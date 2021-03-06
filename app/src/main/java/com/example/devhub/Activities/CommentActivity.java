package com.example.devhub.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.devhub.Models.Comments;
import com.example.devhub.Models.Post;
import com.example.devhub.R;
import com.example.devhub.databinding.ActivityCommentBinding;
import com.parse.ParseUser;


public class CommentActivity extends AppCompatActivity {

    Post SubjectPost;
    ActivityCommentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SubjectPost= getIntent().getParcelableExtra("post");

        ParseUser.getCurrentUser().fetchInBackground();

        binding = ActivityCommentBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        Transition transitionIn = TransitionInflater.from(this).inflateTransition(R.transition.slide_right_animation);
        getWindow().setEnterTransition(transitionIn);

        Transition transitionOut = TransitionInflater.from(this).inflateTransition(R.transition.slide_left_animation);
        getWindow().setExitTransition(transitionOut);


        binding.Cancel.setOnClickListener(view2 -> {
            fireIntent();
        });

        binding.toolbarComment.setOnClickListener(view3 -> {

            if(binding.PostBody.getText().toString().isEmpty()){
                Toast.makeText(this, "Comment body cant be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            saveComment();
        });

        initValues();

    }

    private void initValues() {

        String ImageUrl = "";

        if(ParseUser.getCurrentUser().getBoolean("HasUploadedPic")){
            ImageUrl = ParseUser.getCurrentUser().getParseFile("ProfilePic").getUrl();
        }else{
            ImageUrl =  ParseUser.getCurrentUser().getString("githubProfilePic");
        }

        if (!ImageUrl.isEmpty()) {
            Glide.with(CommentActivity.this)
                    .load(ImageUrl)
                    .into(binding.ivProfileImage);
        }

        binding.tvName.setText( ParseUser.getCurrentUser().getString("PreferredName"));
        binding.gitHubUserName.setText(String.format("@%s",  ParseUser.getCurrentUser().getString("gitHubUserName")));
    }

    private void fireIntent() {

        Intent intent = new Intent(CommentActivity.this, DetailsActivity.class);
        intent.putExtra("post", SubjectPost);
        startActivity(intent);
        finish();
    }

    private void saveComment() {

        Comments comment = new Comments();
        comment.setPost(SubjectPost);
        comment.setUser(ParseUser.getCurrentUser());
        comment.setDescription(binding.PostBody.getText().toString());
        fireIntent();
        comment.saveInBackground(e -> {
            binding.PostBody.setText("");
            updateComments();
        });


    }

    private void updateComments() {
        int comment = SubjectPost.getNumberofComments().intValue();
        comment += 1;

        SubjectPost.setNumberOfComments(comment);

        SubjectPost.saveInBackground(e -> {

        });

    }
}