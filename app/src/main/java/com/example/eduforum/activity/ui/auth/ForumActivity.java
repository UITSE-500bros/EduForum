package com.example.eduforum.activity.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import com.example.eduforum.R;
import com.example.eduforum.databinding.ActivityForumBinding;

public class ForumActivity extends AppCompatActivity {


    ActivityForumBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForumBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.forumFragmentContainer);
        NavController navController = navHostFragment.getNavController();
        //NavigationUI.setupWithNavController(binding., navController);

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchView.setVisibility(View.VISIBLE);
                binding.searchView.setIconified(false); // Open the search view when the button is clicked
            }
        });
    }
}