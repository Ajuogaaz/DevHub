package com.example.devhub.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.devhub.R;
import com.example.devhub.Utils.DoneOnEditorActionListener;
import com.google.android.material.textfield.TextInputEditText;
import com.parse.ParseUser;

import org.jetbrains.annotations.Nullable;

public class OnboardingFragment2 extends Fragment{

    public static final String TAG = OnboardingFragment2.class.getSimpleName();
    private Button save;
    private TextInputEditText etUserName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.onboarding_screen2, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etUserName = view.findViewById(R.id.etUsername);
        save = view.findViewById(R.id.submit);

        etUserName.setOnEditorActionListener(new DoneOnEditorActionListener());

        save.setOnClickListener(view1 -> {

            String profession = etUserName.getText().toString();

            if(!profession.isEmpty()){
                ParseUser.getCurrentUser().put("Experience", profession);
                ParseUser.getCurrentUser().saveInBackground();
            }

        });

    }

}
