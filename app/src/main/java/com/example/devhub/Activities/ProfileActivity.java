package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.devhub.Adapters.ProfileAdapter;
import com.example.devhub.Models.Post;
import com.example.devhub.R;

import com.example.devhub.databinding.ActivityProfileBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private static final int DISPLAY_LIMIT = 20;
    ActivityProfileBinding binding;
    private static final String TAG = "PROFILEACTIVITY";
    List<Post> posts;
    ProfileAdapter profileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ParseUser.getCurrentUser().fetchInBackground();

        posts = new ArrayList<>();

        binding = ActivityProfileBinding.inflate(getLayoutInflater());

        final View view = binding.getRoot();
        setContentView(view);

        binding.btnEditProfile.setOnClickListener(view1 -> {
            Toast.makeText(this, "Edit Profile", Toast.LENGTH_SHORT).show();

        });

        binding.btnEditSettings.setOnClickListener(view2 -> {
            Toast.makeText(this, "Settings Activity", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        binding.preferredName.setText(ParseUser.getCurrentUser().getString("PreferredName"));
        binding.bio.setText(ParseUser.getCurrentUser().getString("Bio"));
        binding.title.setText(ParseUser.getCurrentUser().getString("Title"));
        binding.NumberofActualPosts.setText((ParseUser.getCurrentUser().getNumber("NumberOfPost")).toString());
        binding.NumberofActualRepos.setText((ParseUser.getCurrentUser().getNumber("NumberOfRepos")).toString());
        binding.NumberofActualFollowers.setText((ParseUser.getCurrentUser().getNumber("NumberOfFollowers")).toString());
        binding.NumberofActualfollowing.setText((ParseUser.getCurrentUser().getNumber("NumberOfFollowing")).toString());

        String ImageUrl = "";

        if(ParseUser.getCurrentUser().getBoolean("HasUploadedPic")){
            ImageUrl = ParseUser.getCurrentUser().getParseFile("ProfilePic").getUrl();
        }else{
            ImageUrl = ParseUser.getCurrentUser().getString("githubProfilePic");
        }

        if (!ImageUrl.isEmpty()) {
            Glide.with(this)
                    .load(ImageUrl)
                    .into(binding.ivProfileImage);
        }


        profileAdapter = new ProfileAdapter(this, posts, position -> {

        });

        binding.rvPost.setAdapter(profileAdapter);

        //set the layout manager on the recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rvPost.setLayoutManager(linearLayoutManager);


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
                profileAdapter.clear();
            }
            posts.addAll(newposts);
            profileAdapter.notifyDataSetChanged();
        });
    }



}