package com.example.eduforum.activity.ui.auth;

import android.content.Intent;
import android.os.Bundle;
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
            else{
                Snackbar.make(binding.main, "Vui lòng kích hoạt tài khoản qua email", Snackbar.LENGTH_LONG).show();

            }
        });


    }
}