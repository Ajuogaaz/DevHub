package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.devhub.Adapters.CommentsAdapter;
import com.example.devhub.Adapters.ProfileAdapter;
import com.example.devhub.Models.Post;
import com.example.devhub.R;
import com.example.devhub.databinding.ActivityDetailsBinding;
import com.example.devhub.databinding.ActivityProfileBinding;
import com.parse.FindCallback;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;
    private static final int DISPLAY_LIMIT = 20;
    List<Post> posts;
    CommentsAdapter commentsAdapter;
    Post SubjectPost;
    private static final String TAG = "DETAILSACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SubjectPost= getIntent().getParcelableExtra("post");

        ParseUser.getCurrentUser().fetchInBackground();

        posts = new ArrayList<>();

        binding = ActivityDetailsBinding.inflate(getLayoutInflater());

        binding.Previous.setOnClickListener(view -> {
            startActivity(new Intent(DetailsActivity.this, MainActivity.class));
        });

        final View view = binding.getRoot();
        setContentView(view);

        commentsAdapter = new CommentsAdapter(this, posts, position -> {

        });
        binding.rvComments.setAdapter(commentsAdapter);

        //set the layout manager on the recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rvComments.setLayoutManager(linearLayoutManager);


        queryposts(0);

        innitViews();

        binding.ivComment.setOnClickListener(view1 -> {

        });
        binding.ivCommentText.setOnClickListener(view2 -> {

        });



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


    private void innitViews() {

        binding.titleToolBar.setTitle(SubjectPost.getTopic());

        String ImageUrl = "";

        if(SubjectPost.getUser().getBoolean("HasUploadedPic")){
            ImageUrl = SubjectPost.getUser().getParseFile("ProfilePic").getUrl();
        }else{
            ImageUrl = SubjectPost.getUser().getString("githubProfilePic");
        }

        if (!ImageUrl.isEmpty()) {
            Glide.with(DetailsActivity.this)
                    .load(ImageUrl)
                    .into(binding.ivProfileImage);
        }

        binding.tvName.setText(SubjectPost.getUser().getString("PreferredName"));
        binding.gitHubUserName.setText(String.format("@%s", SubjectPost.getUser().getString("gitHubUserName")));
        binding.tvCreatedAt.setText(SubjectPost.getTime());
        binding.title.setText(SubjectPost.getTopic());
        binding.tvDescription.setText(SubjectPost.getDescription());

        ParseFile image = SubjectPost.getImage();

        if (image != null){
            binding.PostImage.setVisibility(View.VISIBLE);
            Glide.with(DetailsActivity.this)
                    .load(image.getUrl())
                    .into(binding.PostImage);
        }else{
            binding.PostImage.setVisibility(View.GONE);
        }




    }

    private void queryposts(final int page) {

        Post.query(page, DISPLAY_LIMIT, ParseUser.getCurrentUser(), (FindCallback<Post>) (newposts, e) -> {
            if (e != null){
                Log.e(TAG, "Issue with getting posts", e);
                return;

            }
            for(Post post: newposts){
                Log.i(TAG, "Post: " + post.getDescription() + " Username: " + post.getUser().getUsername());
            }
            if(page == 0) {
                commentsAdapter.clear();
            }
            posts.addAll(newposts);
            commentsAdapter.notifyDataSetChanged();
        });
    }



}