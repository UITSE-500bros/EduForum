package com.example.eduforum.activity.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.graphics.Color;
import android.os.Bundle;
import com.example.eduforum.R;
import com.example.eduforum.databinding.ActivityMainBinding;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.color.utilities.DynamicColor;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.mainFragmentContainer);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.mainBottomNavigation, navController);


        //Set badge number for notification fragment
        BadgeDrawable badgeDrawable = binding.mainBottomNavigation.getOrCreateBadge(R.id.notiFragment);
        badgeDrawable.setBackgroundColor(Color.BLUE);
        badgeDrawable.setNumber(10);
        badgeDrawable.setBadgeTextColor(Color.WHITE);
        badgeDrawable.setVisible(true);
    }
}