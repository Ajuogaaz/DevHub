package com.example.devhub.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.devhub.Activities.BoadingActivity;
import com.example.devhub.Activities.ValidateActivity;
import com.example.devhub.R;

import org.jetbrains.annotations.Nullable;

public class OnboardingFragment3 extends Fragment{

    public static final String TAG = OnboardingFragment3.class.getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.onboarding_screen3, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void finishOnboarding() {

        // Launch the main Activity, called MainActivity
        Intent main = new Intent(requireContext(), ValidateActivity.class);
        startActivity(main);

        getActivity().finish();
    }


}
