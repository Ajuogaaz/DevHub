package com.example.devhub.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.devhub.Adapters.OtherProfileAdapter;
import com.example.devhub.Adapters.ProfileAdapter;
import com.example.devhub.Models.Post;
import com.example.devhub.R;
import com.example.devhub.Utils.OnSwipeTouchListener;
import com.example.devhub.databinding.ActivityOtherProfileBinding;
import com.example.devhub.databinding.ActivityProfileBinding;
import com.parse.FindCallback;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OtherProfileActivity extends AppCompatActivity {

    private static final int DISPLAY_LIMIT = 20;
    ActivityOtherProfileBinding binding;
    private static final String TAG = "OTHERPROFILEACTIVITY";
    List<Post> posts;
    OtherProfileAdapter otherProfileAdapter;
    ParseUser CurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);

        ParseUser.getCurrentUser().fetchInBackground();

        CurrentUser = getIntent().getParcelableExtra("post");

        posts = new ArrayList<>();

        binding = ActivityOtherProfileBinding.inflate(getLayoutInflater());

        final View view = binding.getRoot();
        setContentView(view);

        binding.followStatus.setOnClickListener(view2 -> {
            Toast.makeText(this, "Settings Activity", Toast.LENGTH_SHORT).show();

        });

        binding.preferredName.setText(CurrentUser.getString("PreferredName"));
        binding.gitHubUserName.setText(String.format("@%s", CurrentUser.getString("gitHubUserName")));
        binding.bio.setText(CurrentUser.getString("Bio"));
        binding.title.setText(CurrentUser.getString("Title"));

        binding.NumberofActualRepos.setText((CurrentUser.getNumber("NumberOfRepos")).toString());
        binding.NumberofActualFollowers.setText((CurrentUser.getNumber("NumberOfFollowers")).toString());
        binding.NumberofActualfollowing.setText((Objects.requireNonNull(CurrentUser.getNumber("NumberOfFollowing"))).toString());

        String ImageUrl;

        if (CurrentUser.getBoolean("HasUploadedPic")) {
            ImageUrl = Objects.requireNonNull(CurrentUser.getParseFile("ProfilePic")).getUrl();
        } else {
            ImageUrl = CurrentUser.getString("githubProfilePic");
        }


        if (ImageUrl != null && !ImageUrl.isEmpty()) {
            Glide.with(this)
                    .load(ImageUrl)
                    .into(binding.ivProfileImage);
        }

        OtherProfileAdapter.onClickListener clickListener = position -> {
            Intent intent = new Intent(OtherProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        };


        otherProfileAdapter = new OtherProfileAdapter(this, posts, clickListener);


        binding.Previous.setOnClickListener(view5 -> {
            Intent intent = new Intent(OtherProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        binding.Follow.setOnClickListener(view6 -> {

            Toast.makeText(this, "Follow clicked", Toast.LENGTH_SHORT).show();

        });



        binding.rvPost.setAdapter(otherProfileAdapter);

        //set the layout manager on the recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rvPost.setLayoutManager(linearLayoutManager);


        queryposts(0);



        binding.anchor.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeDown() {

            }

            @Override
            public void onSwipeLeft() {


            }

            @Override
            public void onSwipeUp() {

            }

            @Override
            public void onSwipeRight() {
                Intent intent = new Intent(OtherProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }

    private void queryposts(final int page) {

        Post.query(page, DISPLAY_LIMIT, CurrentUser, (FindCallback<Post>) (newposts, e) -> {
            if (e != null){
                Log.e(TAG, "Issue with getting posts", e);
                return;

            }
            for(Post post: newposts){
                Log.i(TAG, "Post: " + post.getDescription() + " Username: " + post.getUser().getUsername());
            }
            if(page == 0) {
                otherProfileAdapter.clear();
            }
            posts.addAll(newposts);
            otherProfileAdapter.notifyDataSetChanged();
            binding.NumberofActualPosts.setText(posts.size());
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


}


