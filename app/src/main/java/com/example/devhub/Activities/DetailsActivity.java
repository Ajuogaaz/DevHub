package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

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
        binding



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