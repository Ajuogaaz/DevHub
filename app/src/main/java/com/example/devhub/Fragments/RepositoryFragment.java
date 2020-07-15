package com.example.devhub.Fragments;

import android.content.Intent;
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
import com.example.devhub.Adapters.RepositoryAdapter;
import com.example.devhub.Adapters.TimelineAdapter;
import com.example.devhub.Models.AccessToken;
import com.example.devhub.Models.Post;
import com.example.devhub.Models.Repositories;
import com.example.devhub.Models.User;
import com.example.devhub.R;
import com.example.devhub.Utils.EndlessRecyclerViewScrollListener;
import com.example.devhub.network.ApiClient;
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

    public RepositoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        allRepos = new ArrayList<>();

        rvRepos =view.findViewById(R.id.rvRepos);

        reposLoader = view.findViewById(R.id.repos_loader);

        RepositoryAdapter.onClickListener  onClickListener = position -> {

            Toast.makeText(getContext(), "Showing click", Toast.LENGTH_SHORT).show();

        };

        getUserInfo(MainActivity.accessToken);

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
                queryRepos(0);
                swipeContainer.setRefreshing(false);
            }
        });
        //configure refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);




        queryRepos(0);


    }

    public void loadNextDataFromBackend(int offset) {

        queryRepos(1);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }

    protected void queryRepos(final int page) {

    }




    //This method to be moved to the profile activity
    private void getUserInfo(AccessToken accessToken) {
        if (accessToken != null) {
            String token = accessToken.getAccessToken();

            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
            okHttpClient.addInterceptor(chain -> {
                Request request = chain.request();
                Request.Builder newRequest = request.newBuilder().header(
                        "Authorization",
                        "token " + token);
                return chain.proceed(newRequest.build());
            }).build();

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(AUTH_URL)
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            ApiClient apiClient = retrofit.create(ApiClient.class);
            apiClient.getUserDetails().enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        User user = response.body();
                    } else {
                        Toast.makeText(
                                getContext(),
                                "Please try again",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(
                            getContext(),
                            "Check your connection",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        }
    }


    private void getUserRepositories(String username) {
        reposLoader.setVisibility(View.VISIBLE);
        ApiClient apiClient = ApiService.getApiUserRepos();
        apiClient.getUserRepos(username).enqueue(new Callback<List<UserRepo>>() {
            @Override
            public void onResponse(Call<List<UserRepo>> call, Response<List<UserRepo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    reposLoader.setVisibility(View.GONE);
                    List<UserRepo> userRepos = response.body();
                    loadRepositories(userRepos);
                } else {
                    reposLoader.setVisibility(View.GONE);
                    Toast.makeText(
                            HomeActivity.this,
                            "Something went wrong while fetching repositories",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserRepo>> call, Throwable t) {
                reposLoader.setVisibility(View.GONE);
                Toast.makeText(
                        HomeActivity.this,
                        "Unable to fetch repositories",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void loadRepositories(List<UserRepo> userRepos) {
        if (userRepos.size() > 0){
            List<UserRepo> userRepoList = new ArrayList<>();
            userRepoList.clear();
            userRepoList.addAll(userRepos);

            //Set up recycler view
            reposRecycler.setLayoutManager(new LinearLayoutManager(this));
            reposRecycler.setHasFixedSize(true);

            repoAdapter.setData(userRepoList);
            reposRecycler.setAdapter(repoAdapter);
        } else {
            Toast.makeText(this, "No repositories found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void repoItemClick(UserRepo userRepo) {
        Toast.makeText(this, "clicked " + userRepo.getName(), Toast.LENGTH_SHORT).show();
    }




}