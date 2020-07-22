package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.devhub.Models.Post;
import com.example.devhub.R;

public class CommentActivity extends AppCompatActivity {

    Post SubjectPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
    }
}