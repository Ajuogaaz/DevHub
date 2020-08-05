package com.example.devhub.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.devhub.R;

public class FollowingFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    public FollowingFragment() {
        // Required empty public constructor
    }

    public static FollowersFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FollowersFragment fragment = new FollowersFragment();
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_following, container, false);
    }
}