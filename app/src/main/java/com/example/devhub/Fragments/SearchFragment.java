package com.example.devhub.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devhub.Activities.OtherProfileActivity;
import com.example.devhub.Activities.ProfileActivity;
import com.example.devhub.Adapters.SearchAdapter;
import com.example.devhub.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchFragment extends Fragment {
    public static final String TAG = SearchFragment.class.getSimpleName();
    private SearchAdapter searchAdapter;
    private List<ParseUser> users;
    private RecyclerView rvSearch;
    private SearchView searchView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        users = new ArrayList<>();
        rvSearch = view.findViewById(R.id.rvSearch);
        searchAdapter = new SearchAdapter(getContext(), users, clickListener);
        rvSearch.setAdapter(searchAdapter);

        //set layout manager on recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvSearch.setLayoutManager(linearLayoutManager);

        searchView = view.findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

    }

    public void filter(String characterText) {
        //This is the list of preview users
        final List<ParseUser> allParseUsers = new ArrayList<>();

        characterText = characterText.toLowerCase(Locale.getDefault());
        searchAdapter.clear();
        if (characterText.length() != 0) {
            //get all users
            ParseQuery<ParseUser> queryUser = ParseUser.getQuery();

            final String finalCharacterText = characterText;

            queryUser.findInBackground((allUsers, e) -> {
                if (e!= null){
                    Log.e(TAG, "Issue with getting all users from Parse");
                }
                Log.i(TAG, "Got all users from parse Successfully");
                allParseUsers.addAll(allUsers);

                for (ParseUser parseUser: allParseUsers) {
                    if (parseUser.getUsername().toLowerCase(Locale.getDefault()).contains(finalCharacterText)) {
                        users.add(parseUser);
                    }
                }
                searchAdapter.notifyDataSetChanged();
            });

        }

    }

    SearchAdapter.onClickListener clickListener = position -> {
        //go to profile Fragment
        //get user of that specific post
        ParseUser user =  users.get(position);

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