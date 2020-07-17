package com.example.devhub.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.devhub.Fragments.RepositoryFragment;
import com.example.devhub.Fragments.SearchFragment;
import com.example.devhub.Fragments.TimelineFragment;
import com.example.devhub.Fragments.ChatFragment;
import com.example.devhub.Fragments.NotificationFragment;
import com.example.devhub.Models.AccessToken;
import com.example.devhub.Models.Repositories;
import com.example.devhub.Models.User;
import com.example.devhub.R;

import com.example.devhub.databinding.ActivityMainBinding;
import com.example.devhub.network.ApiClient;
import com.example.devhub.network.ApiService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.devhub.Utils.Constants.AUTH_URL;

public class MainActivity extends AppCompatActivity {

    public static ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        final FragmentManager fragmentManager = getSupportFragmentManager();

        //define your fragments here
        final Fragment ChatFragment = new ChatFragment();
        final Fragment NotificationFragment = new NotificationFragment();
        final Fragment RepositoryFragment = new RepositoryFragment();
        final Fragment SearchFragment = new SearchFragment();
        final Fragment TimelineFragment = new TimelineFragment();


        // layout of activity is stored in a special property called root
        final View view = binding.getRoot();
        setContentView(view);



        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_chat:
                        //binding.toolbar.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "Chat",
                                Toast.LENGTH_SHORT).show();
                        fragment = ChatFragment;
                        break;

                    case R.id.action_notifications:
                        Toast.makeText(MainActivity.this, "notifications",
                                Toast.LENGTH_SHORT).show();
                        //binding.toolbar.setVisibility(View.GONE);
                        fragment = NotificationFragment;
                        break;

                    case R.id.action_repository:
                        Toast.makeText(MainActivity.this, "Repository",
                                Toast.LENGTH_SHORT).show();
                        //binding.toolbar.setVisibility(View.GONE);
                        fragment = RepositoryFragment;
                        break;
                    case R.id.action_timeline:
                    default:
                        Toast.makeText(MainActivity.this, "Timeline",
                                Toast.LENGTH_SHORT).show();
                        //binding.toolbar.setVisibility(View.GONE);
                        fragment = TimelineFragment;
                        break;
                    case R.id.action_search:
                        Toast.makeText(MainActivity.this, "Search",
                                Toast.LENGTH_SHORT).show();
                        //binding.toolbar.setVisibility(View.GONE);
                        fragment = SearchFragment;
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        binding.bottomNavigation.setSelectedItemId(R.id.action_timeline);


    }
    private void getUserInfo(String currentUserToken) {
        if (!currentUserToken.isEmpty()) {

            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
            okHttpClient.addInterceptor(chain -> {
                Request request = chain.request();
                Request.Builder newRequest = request.newBuilder().header(
                        "Authorization",
                        "token " + currentUserToken);
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
                        getUserRepositories(user.getUsername());
                        Toast.makeText(MainActivity.this, "Data collected", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(
                                MainActivity.this,
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
        apiClient.getUserRepos(username).enqueue(new Callback<List<Repositories>>() {
            @Override
            public void onResponse(Call<List<Repositories>> call, Response<List<Repositories>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    reposLoader.setVisibility(View.GONE);
                    List<Repositories> repositories = response.body();
                    Toast.makeText(getContext(), "Data processed", Toast.LENGTH_SHORT).show();
                    loadRepositories(repositories);
                } else {
                    reposLoader.setVisibility(View.GONE);
                    Toast.makeText(
                            getContext(),
                            "Something went wrong while fetching repositories",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<List<Repositories>> call, Throwable t) {
                reposLoader.setVisibility(View.GONE);
                Toast.makeText(
                        getContext(),
                        "Unable to fetch repositories",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void loadRepositories(List<Repositories> repositories) {
        if (repositories.size() > 0){
            allRepos.clear();
            allRepos.addAll(repositories);
            adapter.notifyDataSetChanged();

        } else {
            Toast.makeText(getContext(), "No repositories found", Toast.LENGTH_SHORT).show();
        }
    }

}