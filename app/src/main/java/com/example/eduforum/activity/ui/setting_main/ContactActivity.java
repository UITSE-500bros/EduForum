package com.example.eduforum.activity.ui.setting_main;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.example.eduforum.R;
import com.example.eduforum.databinding.ActivityContactBinding;
import com.google.android.material.appbar.MaterialToolbar;


public class ContactActivity extends AppCompatActivity {

    ActivityContactBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact);

        MaterialToolbar toolbar = binding.toolbarContact;

        binding.toolbarContact.setNavigationOnClickListener(v -> {
            finish();
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });


    }





}