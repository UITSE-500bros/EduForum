package com.example.eduforum.activity.ui.auth;

import android.os.Bundle;

import com.example.eduforum.databinding.ActivityVerificationWaitingBinding;

import androidx.appcompat.app.AppCompatActivity;

public class VerificationWaitingActivity extends AppCompatActivity {

    private ActivityVerificationWaitingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerificationWaitingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }


}