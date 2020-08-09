package com.example.devhub.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.devhub.Activities.ValidateActivity;
import com.example.devhub.R;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

import static com.parse.Parse.getApplicationContext;

public class OnboardingFragment3 extends Fragment{

    public static final String TAG = OnboardingFragment3.class.getSimpleName();
    private Button save;
    Number Latitude;
    Number Longitude;

    public OnboardingFragment3(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.onboarding_screen3, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String apiKey = getString(R.string.google_maps_key);
        save = view.findViewById(R.id.submit);

        if(requireActivity() == null){
            Log.i(TAG, "Its null");
        }

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }


        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(requireContext());


        AutocompleteSupportFragment autocompleteSupportFragment =
                (AutocompleteSupportFragment) getChildFragmentManager()
                        .findFragmentById(R.id.autocomplete_fragment);

        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.i(TAG, "Place : " + place.getName() + ", " + place.getId()+ "," + place.getLatLng());
                Latitude = place.getLatLng().latitude;
                Longitude = place.getLatLng().longitude;


            }

            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(getContext(), ""+status.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        save.setOnClickListener(view1 -> {
            finishOnboarding();
        });


    }

    private void finishOnboarding() {

        // Launch the main Activity, called MainActivity
        ParseUser.getCurrentUser().put("Latitude", Latitude);
        ParseUser.getCurrentUser().put("Longitude", Longitude);
        ParseUser.getCurrentUser().saveInBackground(e -> {
            Intent intent = new Intent(requireActivity(), ValidateActivity.class);
            startActivity(intent);
        });
    }


}
