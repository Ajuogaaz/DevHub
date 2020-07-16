package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.devhub.Models.Post;
import com.example.devhub.R;
import com.example.devhub.databinding.ActivityProfileBinding;
import com.parse.ParseUser;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    private static final String TAG = "PROFILEACTIVITY";
    List<Post> posts;

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

        posts = queryposts();


        binding.txtName.setText(ParseUser.getCurrentUser().getUsername());
        binding.






    }

    private List<Post> queryposts() {

    }



}