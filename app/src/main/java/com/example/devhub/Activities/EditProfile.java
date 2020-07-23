package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.devhub.R;
import com.example.devhub.databinding.ActivityEditProfileBinding;
import com.example.devhub.databinding.ActivityProfileBinding;
import com.parse.ParseException;
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

         binding.bio.setText(user.getString("bio"));
         binding.Profession.setText(user.getString("Title"));
         binding.preferredName.setText(user.getString("PreferredName"));


         binding.btnSubmit.setOnClickListener(view1 -> {
             saveProfile();
             backToProfile()

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

        user.put(binding.bio.getText().toString(), "bio");
        user.put(binding.Profession.getText().toString(), "Title");
        user.put(binding.preferredName.getText().toString(), "PreferredName");

        user.saveInBackground(e -> {
            if(e == null){
                Log.i(TAG, "done: Saved Success");
            }
        });

    }
}