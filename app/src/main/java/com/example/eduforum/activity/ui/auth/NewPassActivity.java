package com.example.eduforum.activity.ui.auth;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.eduforum.R;
import com.example.eduforum.activity.viewmodel.main.settings.NewPassViewModel;
import com.example.eduforum.databinding.ActivityNewPassBinding;
import com.google.android.material.snackbar.Snackbar;

public class NewPassActivity extends AppCompatActivity {
    ActivityNewPassBinding binding;
    NewPassViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(NewPassViewModel.class);
        binding.setLifecycleOwner(this);

        binding.createNewPassButton.setOnClickListener(v -> {
            viewModel.changePassword(binding.currentPassEditText.getEditText().getText().toString(),
                    binding.newPassEditText.getEditText().getText().toString(),
                    binding.verifyNewPassEditText.getEditText().getText().toString());
        });

        viewModel.getIsSuccess().observe(this, isSuccess -> {
            if(isSuccess) {
                new AlertDialog.Builder(this)
                        .setTitle("Đổi mật khẩu")
                        .setMessage("Đổi mật khẩu thành công")
                        .setPositiveButton("OK", (dialog, which) -> {
                            dialog.dismiss();
                            finish();
                        })
                        .show();
            }
        });
        viewModel.getErrorMessage().observe(this, errorMessage -> {
            if(errorMessage != null) {
                Snackbar.make(binding.getRoot(), errorMessage, Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}