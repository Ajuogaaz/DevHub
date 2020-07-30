package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.devhub.Adapters.OtherRepositoryAdapter;
import com.example.devhub.Adapters.RepositoryAdapter;
import com.example.devhub.Models.Repositories;
import com.example.devhub.R;
import com.example.devhub.Utils.EndlessRecyclerViewScrollListener;
import com.example.devhub.databinding.ActivityOtherProfileBinding;
import com.example.devhub.network.ApiClient;
import com.example.devhub.network.ApiService;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherRepositoryActivity extends AppCompatActivity {

    private RecyclerView rvRepos;
    public static final String TAG = "OtherRepositoryActivity";
    protected OtherRepositoryAdapter adapter;
    protected List<Repositories> allRepos;
    private SwipeRefreshLayout swipeContainer;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_repository);


        ParseUser.getCurrentUser().fetchInBackground();

        CurrentUser = getIntent().getParcelableExtra("post");

        posts = new ArrayList<>();

        binding = ActivityOtherProfileBinding.inflate(getLayoutInflater());

        final View view = binding.getRoot();
        setContentView(view);

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