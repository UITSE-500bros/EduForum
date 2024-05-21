package com.example.eduforum.activity.ui.community;

import android.os.Bundle;
import android.view.MenuInflater;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eduforum.R;
import com.example.eduforum.databinding.ActivityAdminMemberListBinding;
import com.google.android.material.appbar.MaterialToolbar;

public class AdminMemberListActivity extends AppCompatActivity {
    private ActivityAdminMemberListBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAdminMemberListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // This should be the only call to setContentView

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialToolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getMenuInflater().inflate(R.menu.search_menu, toolbar.getMenu());



    }
}