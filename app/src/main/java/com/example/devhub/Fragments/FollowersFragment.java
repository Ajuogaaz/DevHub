package com.example.devhub.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.devhub.Adapters.FollowersAdapter;
import com.example.devhub.Adapters.FollowingAdapter;
import com.example.devhub.Models.Followers;
import com.example.devhub.R;
public class FollowersFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    public RecyclerView rvFollower;
    public FollowersAdapter followersAdapter;

    public FollowersFragment() {
        // Required empty public constructor
    }

    //Static innitializers that supports the constructor
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false);
    }
}