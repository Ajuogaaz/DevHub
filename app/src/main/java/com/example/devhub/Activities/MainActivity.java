package com.example.devhub.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.devhub.Fragments.RepositoryFragment;
import com.example.devhub.Fragments.SearchFragment;
import com.example.devhub.Fragments.TimelineFragment;
import com.example.devhub.R;
import com.example.devhub.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        final FragmentManager fragmentManager = getSupportFragmentManager();

        //define your fragments here
        final Fragment ChatFragment = new ChatFragment();
        final Fragment NotificationFragment = new NotificationFragment();
        final Fragment RepositoryFragment = new RepositoryFragment();
        final Fragment Searchfragment = new SearchFragment();
        final Fragment TimelineFragment = new TimelineFragment();


        // layout of activity is stored in a special property called root
        final View view = binding.getRoot();
        setContentView(view);


        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
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
                        binding.toolbar.setVisibility(View.GONE);
                        fragment = NotificationFragment;
                        break;

                    case R.id.action_compose:
                        Toast.makeText(MainActivity.this, "Compose",
                                Toast.LENGTH_SHORT).show();
                        binding.toolbar.setVisibility(View.GONE);
                        fragment = composeFragment;
                        break;
                    case R.id.action_profile:
                    default:
                        Toast.makeText(MainActivity.this, "Profile",
                                Toast.LENGTH_SHORT).show();
                        binding.toolbar.setVisibility(View.GONE);
                        fragment = profileFragment;
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        binding.bottomNavigation.setSelectedItemId(R.id.action_home);











    }
}