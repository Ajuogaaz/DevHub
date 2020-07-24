package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.devhub.R;
import com.example.devhub.databinding.ActivityEditProfileBinding;
import com.example.devhub.databinding.ActivityProfileBinding;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class EditProfile extends AppCompatActivity {

    private static final String TAG = "EditProfile";
    ActivityEditProfileBinding binding;
    ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        user = ParseUser.getCurrentUser();

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

        binding.tvName.setText(ParseUser.getCurrentUser().getString("PreferredName"));
        binding.gitHubUserName.setText(ParseUser.getCurrentUser().getString("gitHubUserName"));


         //binding.bio.setText(user.getString("Bio"));
         //binding.Profession.setText(user.getString("Title"));
         //binding.preferredName.setText(user.getString("PreferredName"));


         binding.btnSubmit.setOnClickListener(view1 -> {
             saveProfile();
         });

         binding.Cancel.setOnClickListener(view2 -> {
             backToProfile();
         });



    }

    private void backToProfile() {
        startActivity(new Intent(EditProfile.this, ProfileActivity.class));
    }

    private void saveProfile() {

        if(binding.bio.getText().toString().isEmpty() ||
        binding.Profession.getText().toString().isEmpty() ||
        binding.preferredName.getText().toString().isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }


        user.fetchInBackground((object, e) -> {
            if (e == null){
                user.put("bio", binding.bio.getText().toString());
                user.put("Title", binding.Profession.getText().toString());
                user.put("PreferredName", binding.preferredName.getText().toString());

                user.saveInBackground(k -> {
                    if(k == null){
                        Log.i(TAG, "done: Saved Success");
                        backToProfile();
                    }else{
                        Log.d(TAG, k.getMessage());
                    }
                });
            }else {
                Log.d(TAG, "Not fetching " + e.getMessage());
            }


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