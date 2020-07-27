package com.example.devhub.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devhub.Adapters.JobsAdapter;
import com.example.devhub.Models.jobs;
import com.example.devhub.R;
import com.example.devhub.network.ApiClient;
import com.example.devhub.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JobsFragment extends Fragment {

    private RecyclerView mJobRecycler;
    private JobsAdapter mJobsAdapter;
    private ProgressBar loader;
    private List<jobs> mJobList = new ArrayList<>();
    private EditText edLocation, edDescription;
    private Button btnSearch;
    private ApiClient apiClient;
    private boolean initialLoad = true;



    public JobsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiClient = ApiService.getApiClient();

        initViews(view);

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


}