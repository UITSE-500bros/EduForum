package com.example.eduforum.activity.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.eduforum.R;
import com.example.eduforum.activity.ui.main.MainActivity;
import com.example.eduforum.activity.viewmodel.auth.LoginViewModel;
import com.example.eduforum.databinding.ActivityLoginBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

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

        // navigate to sign up
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), SignUpActivity.class);
                startActivity(i);
            }
        });

        TextInputLayout emailInput = binding.TILEmail;
        TextInputLayout passwordInput = binding.TILPassword;

        viewModel.getEmailError().observe(this, emailError -> {
            if(emailError != null){
                emailInput.setError(emailError);
            }
        });

        viewModel.getPasswordError().observe(this, passwordError -> {
            if(passwordError != null){
                passwordInput.setError(passwordError);
            }
        });

        viewModel.getIsEmailVerified().observe(this, isEmailVerified -> {
            if(isEmailVerified){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });

        viewModel.getLoginErrorMsg().observe(this, msg -> {
            Snackbar.make(binding.getRoot(), msg, Snackbar.LENGTH_SHORT).show();
        });


    }
}