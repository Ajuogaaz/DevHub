package com.example.devhub.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.devhub.Activities.CommentActivity;
import com.example.devhub.Activities.ComposeActivity;
import com.example.devhub.Activities.DetailsActivity;
import com.example.devhub.Activities.ProfileActivity;
import com.example.devhub.Adapters.TimelineAdapter;
import com.example.devhub.Models.Post;
import com.example.devhub.R;
import com.example.devhub.Utils.EndlessRecyclerViewScrollListener;
import com.parse.FindCallback;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class JobsFragment extends Fragment {



    public JobsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPost = view.findViewById(R.id.rvPost);
        allPosts = new ArrayList<>();
        profileButton = view.findViewById(R.id.ivProfile);
        likes = new ArrayList<>();

        compose = view.findViewById(R.id.composebtn);

        Transition transition = TransitionInflater.from(getContext()).inflateTransition(R.transition.slide_right_animation);
        getActivity().getWindow().setExitTransition(transition);

        String ImageUrl = "";

        if(ParseUser.getCurrentUser().getBoolean("HasUploadedPic")){
            ImageUrl = ParseUser.getCurrentUser().getParseFile("ProfilePic").getUrl();
        }else{
            ImageUrl = ParseUser.getCurrentUser().getString("githubProfilePic");
        }

        Glide.with(requireActivity())
                .load(ImageUrl)
                .into(profileButton);


        compose.setOnClickListener(view3 -> {
            Intent intent = new Intent(getContext(), ComposeActivity.class);


            startActivityForResult(intent, ComposeRequestCode);
        });

        profileButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), ProfileActivity.class);

            startActivity(intent);
        });
        //((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);


        TimelineAdapter.onClickListener onClickListener = (position, replyCode) -> {

            if (replyCode == TimelineAdapter.DETAILS_CODE) {

                Toast.makeText(getContext(), "Getting Details", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), DetailsActivity.class);
                // options need to be passed when starting the activity
                intent.putExtra("post", allPosts.get(position));
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity());
                startActivity(intent, options.toBundle());


            }
            if (replyCode == TimelineAdapter.PROFILE_CODE){
                Fragment fragment = new ChatFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", allPosts.get(position).getUser());
                fragment.setArguments(bundle);

                //Go from this fragment to profile fragment
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContainer, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
            if (replyCode == TimelineAdapter.LIKE_CODE){

                //Number k = allPosts.get(position).getLikes().intValue() + 1;

                //allPosts.get(position).setLikes(k);
                Toast.makeText(getContext(), "Liked the post", Toast.LENGTH_SHORT).show();


            }
            if (replyCode == TimelineAdapter.COMMENT_CODE){

                Intent intent = new Intent(getContext(), CommentActivity.class);

                intent.putExtra("post", allPosts.get(position));

                startActivity(intent);

            }
            if(replyCode == TimelineAdapter.LIKE_CODE){

                updatelikes(position);

            }


        };

        adapter = new TimelineAdapter(getContext(), allPosts, onClickListener);

        rvPost.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPost.setLayoutManager(linearLayoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromBackend(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvPost.addOnScrollListener(scrollListener);

        swipeContainer = view.findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                queryPost(0);
                swipeContainer.setRefreshing(false);
            }
        });
        //configure refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);




        queryPost(0);


    }

    private void updatelikes(int position) {

        Post SubjectPost = allPosts.get(position);

        likes.clear();
        if (SubjectPost.getLikes() != null){
            likes.addAll(allPosts.get(position).getLikes());


            if(!currentUserInList(likes)){
                likes.add(ParseUser.getCurrentUser().getObjectId());
            }else{
                likes.remove(ParseUser.getCurrentUser().getObjectId());
            }
        }else{
            likes.add(ParseUser.getCurrentUser().getObjectId());
        }

        SubjectPost.setLike(likes);

        SubjectPost.saveInBackground(e -> {
            if(e == null){
                Toast.makeText(getContext(), "likedPost", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        });


    }
    private boolean currentUserInList(List<String> likes) {
        if (likes.size() != 0){
            for(int i = 0; i < likes.size(); i++)
                if (ParseUser.getCurrentUser().getObjectId().equals(likes.get(i))) {
                    return true;
                }
            return false;
        }
        return false;
    }


    public void loadNextDataFromBackend(int offset) {

        queryPost(1);
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

    protected void queryPost(final int page) {
        Post.query(page, DISPLAY_LIMIT, specifiedUser, (FindCallback<Post>) (posts, e) -> {
            if (e != null){
                Log.e(TAG, "Issue with getting posts", e);
                return;
            }
            for(Post post: posts){
                Log.i(TAG, "Post: " + post.getDescription() + " Username: " + post.getUser().getUsername());
            }
            if(page == 0) {
                adapter.clear();
            }

            allPosts.addAll(posts);
            adapter.notifyDataSetChanged();
        });
    }




}