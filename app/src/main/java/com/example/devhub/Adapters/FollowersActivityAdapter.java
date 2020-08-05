package com.example.devhub.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.devhub.Activities.FollowersActivity;
import com.example.devhub.Fragments.FollowersFragment;

public class FollowersActivityAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Followers", "Following"};
    private final Context context;

    public FollowersActivityAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        return FollowersFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
