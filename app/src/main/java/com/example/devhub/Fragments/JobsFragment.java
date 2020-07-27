package com.example.devhub.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devhub.Adapters.JobsAdapter;
import com.example.devhub.Models.jobs;
import com.example.devhub.R;
import com.example.devhub.network.ApiClient;

import java.util.ArrayList;
import java.util.List;


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


}