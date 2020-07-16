package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.devhub.Adapters.ProfileAdapter;
import com.example.devhub.Models.Post;
import com.example.devhub.R;
import com.example.devhub.databinding.ActivityProfileBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

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

        binding = ActivityProfileBinding.inflate(getLayoutInflater());

        final View view = binding.getRoot();
        setContentView(view);

        binding.btnEditProfile.setOnClickListener(view1 -> {
            Toast.makeText(this, "Edit Profile", Toast.LENGTH_SHORT).show();

        });

        binding.name.setText(ParseUser.getCurrentUser().getUsername());
        binding.repoNo.setText(R.string.temp_repno);
        binding.userEmail.setText(R.string.temp_email);
        binding.username.setText(R.string.temp_username);

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