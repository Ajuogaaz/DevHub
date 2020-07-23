package com.example.devhub.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.devhub.Adapters.ProfileAdapter;
import com.example.devhub.Models.Post;
import com.example.devhub.R;

import com.example.devhub.databinding.ActivityProfileBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private static final int DISPLAY_LIMIT = 20;
    ActivityProfileBinding binding;
    private static final String TAG = "PROFILEACTIVITY";
    List<Post> posts;
    ProfileAdapter profileAdapter;
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    File photoFile;
    public String photoFileName = "photo.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ParseUser.getCurrentUser().fetchInBackground();

        posts = new ArrayList<>();

        binding = ActivityProfileBinding.inflate(getLayoutInflater());

        final View view = binding.getRoot();
        setContentView(view);

        binding.settingsPicture.setOnClickListener(view2 -> {
            Toast.makeText(this, "Settings Activity", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        binding.preferredName.setText(ParseUser.getCurrentUser().getString("PreferredName"));
        binding.gitHubUserName.setText(String.format("@%s", ParseUser.getCurrentUser().getString("gitHubUserName")));
        binding.bio.setText(ParseUser.getCurrentUser().getString("Bio"));
        binding.title.setText(ParseUser.getCurrentUser().getString("Title"));
        binding.NumberofActualPosts.setText((ParseUser.getCurrentUser().getNumber("NumberOfPost")).toString());
        binding.NumberofActualRepos.setText((ParseUser.getCurrentUser().getNumber("NumberOfRepos")).toString());
        binding.NumberofActualFollowers.setText((ParseUser.getCurrentUser().getNumber("NumberOfFollowers")).toString());
        binding.NumberofActualfollowing.setText((ParseUser.getCurrentUser().getNumber("NumberOfFollowing")).toString());

        String ImageUrl = "";

        if (ParseUser.getCurrentUser().getBoolean("HasUploadedPic")) {
            ImageUrl = ParseUser.getCurrentUser().getParseFile("ProfilePic").getUrl();
        } else {
            ImageUrl = ParseUser.getCurrentUser().getString("githubProfilePic");
        }

        if (!ImageUrl.isEmpty()) {
            Glide.with(this)
                    .load(ImageUrl)
                    .into(binding.ivProfileImage);
        }


        profileAdapter = new ProfileAdapter(this, posts, position -> {

        });

        binding.ivProfileImage.setOnClickListener(view3 -> {
            launchCamera();
        });

        binding.rvPost.setAdapter(profileAdapter);

        //set the layout manager on the recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rvPost.setLayoutManager(linearLayoutManager);


        queryposts(0);

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
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == -1) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ParseUser currentUser = ParseUser.getCurrentUser();

                currentUser.put("ProfilePic", new ParseFile(photoFile));
                currentUser.put("HasUploadedPic", true);
                currentUser.saveInBackground(e -> {
                    if(e==null){
                        Log.i(TAG, "done: Saved Success");
                    }
                });


            } else { // Result was a failure
                Toast.makeText(ProfileActivity.this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
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



}