package com.example.eduforum.activity.ui.auth;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.eduforum.R;
import com.example.eduforum.databinding.ActivityForumBinding;

public class ForumActivity extends AppCompatActivity {

    ActivityForumBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.forumFragmentContainer);
        NavController navController = navHostFragment.getNavController();
        //NavigationUI.setupWithNavController(binding., navController);
    }
}