package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
    private static final String TAG = "DETAILSACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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