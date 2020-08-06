package com.example.devhub.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.devhub.Fragments.ChatFragment;
import com.example.devhub.Fragments.JobsFragment;
import com.example.devhub.Fragments.RepositoryFragment;
import com.example.devhub.Fragments.SearchFragment;
import com.example.devhub.Fragments.TimelineFragment;
import com.example.devhub.Models.Followers;
import com.example.devhub.Models.Repositories;
import com.example.devhub.R;

import com.example.devhub.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static ActivityMainBinding binding;
    public static List<Repositories> Repos;

    public static final int TimelineFragmentRequest = 1234;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        final FragmentManager fragmentManager = getSupportFragmentManager();

        //define your fragments here
        final Fragment ChatFragment = new ChatFragment();
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
                        Toast.makeText(MainActivity.this, "Chat",
                                Toast.LENGTH_SHORT).show();
                        fragment = ChatFragment;
                        break;

                    case R.id.action_notifications:
                        Toast.makeText(MainActivity.this, "notifications",
                                Toast.LENGTH_SHORT).show();
                        //binding.toolbar.setVisibility(View.GONE);
                        fragment = NotificationFragment;
                        break;

                    case R.id.action_repository:
                        Toast.makeText(MainActivity.this, "Repository",
                                Toast.LENGTH_SHORT).show();
                        //binding.toolbar.setVisibility(View.GONE);
                        fragment = RepositoryFragment;
                        break;
                    case R.id.action_timeline:
                    default:
                        Toast.makeText(MainActivity.this, "Timeline",
                                Toast.LENGTH_SHORT).show();
                        //binding.toolbar.setVisibility(View.GONE);
                        fragment = TimelineFragment;
                        break;
                    case R.id.action_search:
                        Toast.makeText(MainActivity.this, "Search",
                                Toast.LENGTH_SHORT).show();
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
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

    }

}