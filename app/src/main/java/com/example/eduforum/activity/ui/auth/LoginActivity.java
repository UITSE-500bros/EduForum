package com.example.eduforum.activity.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.example.eduforum.activity.EduForum;
import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.activity.repository.user.IUserCallback;
import com.example.eduforum.activity.ui.main.MainActivity;
import com.example.eduforum.activity.ui.welcome.WelcomeActivity;
import com.example.eduforum.activity.util.FlagsList;
import com.example.eduforum.activity.viewmodel.auth.LoginViewModel;
import com.example.eduforum.activity.viewmodel.shared.UserViewModel;
import com.example.eduforum.databinding.ActivityLoginBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;
    private UserViewModel userViewModel;
    private ActivityLoginBinding binding;
    private SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        //viewModel.getSignedInUser().observe(this, this::updateUI);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        EduForum app = (EduForum) getApplication();
        userViewModel = app.getSharedViewModel(UserViewModel.class);

        prefs = getSharedPreferences("com.example.eduforum", MODE_PRIVATE);
        if (prefs.getBoolean("firstRun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            Intent i = new Intent(this, WelcomeActivity.class);
            startActivity(i);
            prefs.edit().putBoolean("firstRun", false).apply();
            finish();

        }


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

        // navigate to forgot password
        binding.btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ForgotPassActivity.class);
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
                userViewModel.getCurrentUser(new IUserCallback() {
                    @Override
                    public void onGetUserSuccess(User user) {
                        startActivity(intent);
                        finish();
                    }
                    @Override
                    public void onGetUserFailure(String errorMsg) {
                        Snackbar.make(binding.getRoot(), "Đã có lỗi xảy ra, vui lòng chờ giây lát!", Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });

        viewModel.getLoginErrorMsg().observe(this, msg -> {
            Snackbar.make(binding.getRoot(), msg, Snackbar.LENGTH_SHORT).show();
        });


    }

    private void updateUI(FirebaseUser user) {
        if (user != null && user.isEmailVerified()) {
//            Log.d("TEST", user.getEmail());
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}