package com.example.eduforum.activity.ui.auth;

import android.os.Bundle;

import com.example.eduforum.databinding.ActivityVerificationWaitingBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.eduforum.databinding.ActivityVerificationWaitingAcitivityBinding;

import com.example.eduforum.R;

public class VerificationWaitingAcitivity extends AppCompatActivity {

    private ActivityVerificationWaitingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerificationWaitingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }


}