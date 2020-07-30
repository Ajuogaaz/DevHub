package com.example.devhub.Activities;

import androidx.appcompat.app.AppCompatActivity;
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
    private String currentUserToken;
    private ActivityOtherRepositoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_repository);

        ParseUser.getCurrentUser().fetchInBackground();

        specifiedUser = getIntent().getParcelableExtra("user");

        binding = ActivityOtherRepositoryBinding.inflate(getLayoutInflater());

        final View view = binding.getRoot();
        setContentView(view);


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


            if (repositories.size() != (ParseUser.getCurrentUser().getNumber("NumberOfRepos")).intValue()){
                ParseUser user =  ParseUser.getCurrentUser();
                user.put("NumberOfRepos", repositories.size());
                user.saveInBackground(e -> {
                    if(e != null){
                        Log.e(TAG, "error " + e.getMessage());
                    }
                });
            }

        }

    }

}