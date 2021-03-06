package com.example.devhub.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.devhub.Activities.FollowersActivity;
import com.example.devhub.Activities.OtherProfileActivity;
import com.example.devhub.Activities.ProfileActivity;
import com.example.devhub.Adapters.FollowersAdapter;
import com.example.devhub.Adapters.FollowingAdapter;
import com.example.devhub.Models.Followers;
import com.example.devhub.R;
import com.parse.ParseUser;

import org.jetbrains.annotations.Nullable;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvFollower = view.findViewById(R.id.rvFollowers);
        followersAdapter = new FollowersAdapter(requireContext(), FollowersActivity.followers, clickListener);
        rvFollower.setAdapter(followersAdapter);

        //set layout manager on recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvFollower.setLayoutManager(linearLayoutManager);


    }

    FollowersAdapter.onClickListener clickListener = position -> {
        //go to profile Fragment
        //get user of that specific post
        ParseUser user = FollowersActivity.followers.get(position).getFollowingUser();

        //Create an intent and pass it either to the current user view or the third party view
        if(user.getObjectId().equals(ParseUser.getCurrentUser().getObjectId())){
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(requireContext(), OtherProfileActivity.class);
            intent.putExtra("post", user);
            startActivity(intent);
        }
    };




}