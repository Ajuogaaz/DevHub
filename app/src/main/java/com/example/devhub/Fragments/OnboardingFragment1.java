package com.example.devhub.Fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.devhub.R;
import com.example.devhub.Utils.DoneOnEditorActionListener;
import com.google.android.material.textfield.TextInputEditText;
import com.parse.ParseUser;

import org.jetbrains.annotations.Nullable;

public class OnboardingFragment1 extends Fragment{

        public static final String TAG = OnboardingFragment1.class.getSimpleName();

        private TextInputEditText etUserName;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.onboarding_screen1, container, false);
            return view;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            etUserName = view.findViewById(R.id.etUsername);

            etUserName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    new DoneOnEditorActionListener();
                    String Profession = etUserName.getText().toString();

                    ParseUser.getCurrentUser().put("Title", Profession);
                    ParseUser.getCurrentUser().saveInBackground();
                    return false;
                }
            });




        }

}
