package com.example.devhub.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.devhub.Fragments.OnboardingFragment1;
import com.example.devhub.Fragments.OnboardingFragment2;
import com.example.devhub.Fragments.OnboardingFragment3;
import com.example.devhub.databinding.ActivityBoadingBinding;

public class BoadingActivity extends FragmentActivity {

    ActivityBoadingBinding binding;
    FragmentStatePagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoadingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


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




    }
}