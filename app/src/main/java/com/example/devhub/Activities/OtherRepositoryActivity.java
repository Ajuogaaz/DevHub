package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.devhub.Models.Repositories;
import com.example.devhub.R;
import com.example.devhub.network.ApiClient;
import com.example.devhub.network.ApiService;
import com.parse.ParseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherRepositoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_repository);
    }

    private void openWebView(Repositories repo) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(repo.getUrl()));
        startActivity(intent);


    private void loadRepositories(List<Repositories> repositories) {
        if (repositories.size() > 0){
            allRepos.clear();
            allRepos.addAll(repositories);
            adapter.notifyDataSetChanged();


            if (repositories.size() != (ParseUser.getCurrentUser().getNumber("NumberOfRepos")).intValue()){
                ParseUser user =  ParseUser.getCurrentUser();
                user.put("NumberOfRepos", repositories.size());
                user.saveInBackground(e -> {
                    if(e == null){
                        Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } else {
            if(getContext() != null){
                Toast.makeText(getContext(), "No repositories found", Toast.LENGTH_SHORT).show();
            }
        }
    }

}