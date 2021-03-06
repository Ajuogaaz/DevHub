package com.example.devhub.Activities;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.devhub.Fragments.JobsFragment;
import com.example.devhub.Fragments.MapsFragment;
import com.example.devhub.Fragments.RepositoryFragment;
import com.example.devhub.Fragments.SearchFragment;
import com.example.devhub.Fragments.TimelineFragment;
import com.example.devhub.R;
import com.example.devhub.Utils.HideSystemWindow;

import com.example.devhub.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static ActivityMainBinding binding;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        final FragmentManager fragmentManager = getSupportFragmentManager();

        //define your fragments here
        final Fragment MapFragment = new MapsFragment();
        final Fragment NotificationFragment = new JobsFragment();
        final Fragment RepositoryFragment = new RepositoryFragment();
        final Fragment SearchFragment = new SearchFragment();
        final Fragment TimelineFragment = new TimelineFragment();


        // layout of activity is stored in a special property called root
        final View view = binding.getRoot();
        setContentView(view);



        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.action_chat:
                        //binding.toolbar.setVisibility(View.VISIBLE);
                        fragment = MapFragment;
                        break;

                    case R.id.action_notifications:

                        //binding.toolbar.setVisibility(View.GONE);
                        fragment = NotificationFragment;
                        break;

                    case R.id.action_repository:

                        //binding.toolbar.setVisibility(View.GONE);
                        fragment = RepositoryFragment;
                        break;
                    case R.id.action_timeline:
                    default:

                        //binding.toolbar.setVisibility(View.GONE);
                        fragment = TimelineFragment;
                        break;
                    case R.id.action_search:

                        //binding.toolbar.setVisibility(View.GONE);
                        fragment = SearchFragment;
                        break;
                }
                if(fragment != null){
                    fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                    return true;
                }else{
                    return true;
                }
            }
        });
        binding.bottomNavigation.setSelectedItemId(R.id.action_timeline);


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            HideSystemWindow.hideSystemUI(getWindow());
        }
    }

}