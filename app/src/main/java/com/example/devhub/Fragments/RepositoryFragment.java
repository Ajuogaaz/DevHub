package com.example.devhub.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.devhub.Activities.MainActivity;
import com.example.devhub.Activities.ValidateActivity;
import com.example.devhub.Adapters.RepositoryAdapter;
import com.example.devhub.Adapters.TimelineAdapter;
import com.example.devhub.Models.AccessToken;
import com.example.devhub.Models.Post;
import com.example.devhub.Models.Repositories;
import com.example.devhub.Models.User;
import com.example.devhub.Models.jobs;
import com.example.devhub.R;
import com.example.devhub.Utils.EndlessRecyclerViewScrollListener;
import com.example.devhub.network.ApiClient;
import com.example.devhub.network.ApiService;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.devhub.Utils.Constants.AUTH_URL;

public class RepositoryFragment extends Fragment {

    private RecyclerView rvRepos;
    public static final String TAG = "RepositoryFragment";
    protected  RepositoryAdapter adapter;
    protected List<Repositories> allRepos;
    private SwipeRefreshLayout swipeContainer;
    private EndlessRecyclerViewScrollListener scrollListener;
    protected ParseUser specifiedUser;
    private ProgressBar reposLoader;
    private String currentUserToken;

    public RepositoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvRepos = view.findViewById(R.id.rvRepos);
        allRepos = new ArrayList<>();
        currentUserToken = ParseUser.getCurrentUser().getString("Token");


        reposLoader = view.findViewById(R.id.repos_loader);

        RepositoryAdapter.onClickListener  onClickListener = position -> {
            openWebView(allRepos.get(position));
        };

        adapter = new RepositoryAdapter(getContext(), allRepos, onClickListener);

        rvRepos.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
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
                getUserRepositories(ParseUser.getCurrentUser().getUsername());
                swipeContainer.setRefreshing(false);
            }
        });
        //configure refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        getUserRepositories(ParseUser.getCurrentUser().getUsername());
    }

    public void loadNextDataFromBackend(int offset) {

        getUserRepositories(ParseUser.getCurrentUser().getUsername());
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_repository, container, false);
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
                    if(getContext() != null) {
                        Toast.makeText(
                                getContext(),
                                "Something went wrong while fetching repositories",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Repositories>> call, Throwable t) {
                reposLoader.setVisibility(View.GONE);
                if(getContext() != null) {
                    Toast.makeText(
                            getContext(),
                            "Unable to fetch repositories",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

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