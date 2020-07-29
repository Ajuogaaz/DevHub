package com.example.devhub.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devhub.Adapters.JobsAdapter;
import com.example.devhub.AlgoModels.UserRep;
import com.example.devhub.Models.Repositories;
import com.example.devhub.Models.jobs;
import com.example.devhub.R;
import com.example.devhub.Utils.DoneOnEditorActionListener;
import com.example.devhub.network.ApiClient;
import com.example.devhub.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@RequiresApi(api = Build.VERSION_CODES.N)
public class JobsFragment extends Fragment implements JobsAdapter.JobInteraction {

    private RecyclerView mJobRecycler;
    private JobsAdapter mJobsAdapter;
    private ProgressBar loader;
    private List<jobs> mJobList = new ArrayList<>();
    private EditText edLocation, edDescription;
    private ImageView btnSearch;
    private ApiClient apiClient;
    private boolean initialLoad = true;
    private UserRep userRep;



    public JobsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userRep = new UserRep();

        apiClient = ApiService.getApiUserJobs();

        initViews(view);

        mJobsAdapter = new JobsAdapter(requireContext(), this);

        setUpRecycler();

        btnSearch.setOnClickListener(view2 -> performSearch());

        edLocation.setOnEditorActionListener(new DoneOnEditorActionListener());
        edDescription.setOnEditorActionListener(new DoneOnEditorActionListener());

    }

    private void initViews(View view) {
        mJobRecycler = view.findViewById(R.id.jobs_recycler);
        loader = view.findViewById(R.id.jobs_loader);
        btnSearch = view.findViewById(R.id.btn_search);
        edLocation = view.findViewById(R.id.search_location);
        edDescription = view.findViewById(R.id.search_desc);
    }

    private void setUpRecycler() {
        mJobRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mJobRecycler.setHasFixedSize(true);

        loader.setVisibility(View.VISIBLE);
        apiClient.getAvailableJobs().enqueue(new Callback<List<jobs>>() {
            @Override
            public void onResponse(Call<List<jobs>> call, Response<List<jobs>> response) {
                initialLoad = true;
                loadResponse(response);
            }

            @Override
            public void onFailure(Call<List<jobs>> call, Throwable t) {
                showConnectionError();
            }
        });
    }

    private void loadResponse(Response<List<jobs>> response) {
        mJobList.clear();
        if (response.isSuccessful() && response.body() != null) {
            loader.setVisibility(View.GONE);
            mJobList.addAll(response.body());
            mJobsAdapter.setJobData(mJobList);
            mJobRecycler.setAdapter(mJobsAdapter);
        } else {
            loader.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Please try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void showConnectionError() {
        loader.setVisibility(View.GONE);
        Toast.makeText(getContext(), "Internet Unavailable", Toast.LENGTH_SHORT).show();
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_jobs, container, false);

    }

    @Override
    public void jobClicked(jobs job, View view) {
        switch (view.getId()) {
            case R.id.btn_job_details:
            case R.id.txt_job_link:
                openWebView(job);
                break;
        }
    }

    private void openWebView(jobs job) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(job.getJobUrl()));
        startActivity(intent);
    }

    private void performSearch() {
        String location = edLocation.getText().toString().toLowerCase().trim();
        String description = edDescription.getText().toString().toLowerCase().trim();

        if (!location.isEmpty() || !description.isEmpty()) {
            if (location.contains(" ")) {
                location = location.replace(" ", "+");
            }
            initialLoad = false;
            makeRequest(location, description);
        } else {
            if(!initialLoad){
                setUpRecycler();
            }

        }
    }
    private void makeRequest(String location, String description) {
        loader.setVisibility(View.VISIBLE);
        apiClient.getQueriedJobs(description, location).enqueue(new Callback<List<jobs>>() {
            @Override
            public void onResponse(Call<List<jobs>> call, Response<List<jobs>> response) {
                loadResponse(response);
            }

            @Override
            public void onFailure(Call<List<jobs>> call, Throwable t) {
                showConnectionError();
            }
        });
    }

    private void getUserRepositories(String username) {
        ApiClient apiClient = ApiService.getApiUserRepos();
        apiClient.getUserRepos(username).enqueue(new Callback<List<Repositories>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Repositories>> call, Response<List<Repositories>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<Repositories> repos = response.body();

                    userRep = new UserRep(repos)

                }
            }
            @Override
            public void onFailure(Call<List<Repositories>> call, Throwable t) {
            }
        });
    }




}