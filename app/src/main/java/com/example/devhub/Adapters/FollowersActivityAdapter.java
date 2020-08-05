package com.example.devhub.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.devhub.Fragments.FollowersFragment;
import com.example.devhub.Fragments.FollowingFragment;

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
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new FollowersFragment();
                break;
            case 1:
                fragment = new FollowingFragment();
                break;
        }
        return fragment;
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
