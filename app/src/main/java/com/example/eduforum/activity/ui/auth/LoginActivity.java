package com.example.eduforum.activity.ui.auth;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.eduforum.R;
import com.example.eduforum.activity.viewmodel.auth.LoginViewModel;
import com.example.eduforum.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

    }
}