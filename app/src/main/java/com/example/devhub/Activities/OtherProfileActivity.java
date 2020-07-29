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
    ProfileAdapter profileAdapter;
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    File photoFile;
    public String photoFileName = "photo.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);

        ParseUser.getCurrentUser().fetchInBackground();

        posts = new ArrayList<>();

        binding = ActivityOtherProfileBinding.inflate(getLayoutInflater());

        final View view = binding.getRoot();
        setContentView(view);

        binding.followStatus.setOnClickListener(view2 -> {
            Toast.makeText(this, "Settings Activity", Toast.LENGTH_SHORT).show();

        });

        binding.preferredName.setText(ParseUser.getCurrentUser().getString("PreferredName"));
        binding.gitHubUserName.setText(String.format("@%s", ParseUser.getCurrentUser().getString("gitHubUserName")));
        binding.bio.setText(ParseUser.getCurrentUser().getString("Bio"));
        binding.title.setText(ParseUser.getCurrentUser().getString("Title"));
        binding.NumberofActualPosts.setText((Objects.requireNonNull(ParseUser.getCurrentUser().getNumber("NumberOfPost"))).toString());
        binding.NumberofActualRepos.setText((ParseUser.getCurrentUser().getNumber("NumberOfRepos")).toString());
        binding.NumberofActualFollowers.setText((ParseUser.getCurrentUser().getNumber("NumberOfFollowers")).toString());
        binding.NumberofActualfollowing.setText((Objects.requireNonNull(ParseUser.getCurrentUser().getNumber("NumberOfFollowing"))).toString());

        String ImageUrl;

        if (ParseUser.getCurrentUser().getBoolean("HasUploadedPic")) {
            ImageUrl = Objects.requireNonNull(ParseUser.getCurrentUser().getParseFile("ProfilePic")).getUrl();
        } else {
            ImageUrl = ParseUser.getCurrentUser().getString("githubProfilePic");
        }


        if (ImageUrl != null && !ImageUrl.isEmpty()) {
            Glide.with(this)
                    .load(ImageUrl)
                    .into(binding.ivProfileImage);
        }

        ProfileAdapter.onClickListener clickListener = position -> {
            Intent intent = new Intent(OtherProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        };


        profileAdapter = new ProfileAdapter(this, posts, clickListener);

        binding.ivProfileImage.setOnClickListener(view3 -> launchCamera());

        binding.Previous.setOnClickListener(view5 -> {
            Intent intent = new Intent(OtherProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        binding.Follow.setOnClickListener(view6 -> {

            Toast.makeText(this, "Follow clicked", Toast.LENGTH_SHORT).show();

        });



        binding.rvPost.setAdapter(profileAdapter);

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
    private void launchCamera() {
        //Create an intent to take pictures and return control to the app
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Create a file reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        //wrap file object into a content provider
        Uri fileProvider = FileProvider.getUriForFile(this, "com.codepath.fileprovider.DevHub", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }

    }

    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.i(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename

        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == -1) {
                // by this point we have the camera photo on disk
                //Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ParseUser currentUser = ParseUser.getCurrentUser();
                Toast.makeText(this, "Your profile picture will be updated shortly", Toast.LENGTH_SHORT).show();

                currentUser.put("ProfilePic", new ParseFile(photoFile));
                currentUser.put("HasUploadedPic", true);
                currentUser.saveInBackground(e -> {
                    if(e==null){
                        Log.i(TAG, "done: Saved Success");
                    }
                });


            } else { // Result was a failure
                Toast.makeText(OtherProfileActivity.this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
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
                profileAdapter.clear();
            }
            posts.addAll(newposts);
            profileAdapter.notifyDataSetChanged();
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


