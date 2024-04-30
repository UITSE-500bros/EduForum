package com.example.eduforum.activity.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.eduforum.R;
import com.example.eduforum.activity.viewmodel.auth.LoginViewModel;
import com.example.eduforum.databinding.ActivityForgotPassBinding;
import com.example.eduforum.databinding.ActivityVerificationWaitingBinding;

public class ForgotPassActivity extends AppCompatActivity {

    private ActivityForgotPassBinding binding;
    private LoginViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_pass);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setLifecycleOwner(this);

        binding.sendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(binding.forgotPassEmailEditText.getText());
                if (email.isEmpty() || !email.matches("^[0-9]{8}@gm\\.uit\\.edu\\.vn$")) {
                    Toast.makeText(ForgotPassActivity.this, "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.sendResetPasswordEmail(email);
                }
            }
        });

        viewModel.getIsResetPasswordEmailSent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean) return;
                Intent intent = new Intent(ForgotPassActivity.this, VerificationWaitingActivity.class);
                intent.putExtra("callingActivity", "ForgotPassActivity");
                startActivity(intent);
            }
        });
    }
}