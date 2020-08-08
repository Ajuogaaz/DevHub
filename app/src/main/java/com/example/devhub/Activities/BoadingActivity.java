package com.example.devhub.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.devhub.Fragments.OnboardingFragment1;
import com.example.devhub.Fragments.OnboardingFragment2;
import com.example.devhub.Fragments.OnboardingFragment3;
import com.example.devhub.R;
import com.example.devhub.databinding.ActivityBoadingBinding;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

public class BoadingActivity extends FragmentActivity {

    FragmentStatePagerAdapter adapter;

    private ViewPager pager;
    private SmartTabLayout indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boading);

        pager = findViewById(R.id.pager);
        indicator = findViewById(R.id.indicator);

        adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0 : return new OnboardingFragment1();
                    case 1 : return new OnboardingFragment2();
                    case 2 : return new OnboardingFragment3();
                    default: return null;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };

        pager.setAdapter(adapter);
        indicator.setViewPager(pager);


    }

}