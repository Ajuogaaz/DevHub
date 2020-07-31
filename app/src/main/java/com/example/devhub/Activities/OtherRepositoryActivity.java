package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.devhub.Adapters.OtherRepositoryAdapter;
import com.example.devhub.Adapters.RepositoryAdapter;
import com.example.devhub.Models.Repositories;
import com.example.devhub.R;
import com.example.devhub.Utils.EndlessRecyclerViewScrollListener;
import com.example.devhub.databinding.ActivityOtherProfileBinding;
import com.example.devhub.databinding.ActivityOtherRepositoryBinding;
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
    protected ParseUser specifiedUser;
    private ProgressBar reposLoader;
    private ActivityOtherRepositoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_repository);


        specifiedUser = getIntent().getParcelableExtra("user");

        binding = ActivityOtherRepositoryBinding.inflate(getLayoutInflater());

        final View view = binding.getRoot();
        setContentView(view);


        rvRepos = view.findViewById(R.id.rvRepos);
        allRepos = new ArrayList<>();

        reposLoader = view.findViewById(R.id.repos_loader);

        OtherRepositoryAdapter.onClickListener  onClickListener = position -> {
            openWebView(allRepos.get(position));
        };

        adapter = new OtherRepositoryAdapter(this, allRepos, onClickListener);

        rvRepos.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvRepos.setLayoutManager(linearLayoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromBackend(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvRepos.addOnScrollListener(scrollListener);

        swipeContainer = view.findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                getUserRepositories(specifiedUser.getString("gitHubUserName"));
                swipeContainer.setRefreshing(false);
            }
        });
        //configure refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        getUserRepositories(specifiedUser.getString("gitHubUserName"));
    }

    public void loadNextDataFromBackend(int offset) {

        getUserRepositories(specifiedUser.getString("gitHubUserName"));
    }

    private void openWebView(Repositories repo) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(repo.getUrl()));
        startActivity(intent);

    }
    private void getUserRepositories(String username) {
        reposLoader.setVisibility(View.VISIBLE);
        ApiClient apiClient = ApiService.getApiUserRepos();
        apiClient.getUserRepos(username).enqueue(new Callback<List<Repositories>>() {
            @Override
            public void onResponse(Call<List<Repositories>> call, Response<List<Repositories>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    reposLoader.setVisibility(View.GONE);
                    List<Repositories> repositories = response.body();
                    loadRepositories(repositories);
                } else {
                    reposLoader.setVisibility(View.GONE);
                    }
                }


            @Override
            public void onFailure(Call<List<Repositories>> call, Throwable t) {
                reposLoader.setVisibility(View.GONE);
            }
        });
    }



    private void loadRepositories(List<Repositories> repositories) {
        if (repositories.size() > 0){
            allRepos.clear();
            allRepos.addAll(repositories);
            adapter.notifyDataSetChanged();

        }

    }

}