package com.example.eduforum.activity.ui.setting_main;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.example.eduforum.R;
import com.example.eduforum.databinding.ActivityProfileUserSettingBinding;

public class ProfileUserSettingActivity extends AppCompatActivity {

    ActivityProfileUserSettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_user_setting);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_user_setting);

        binding.toolBarProfileUser.setNavigationOnClickListener(v -> {
            finish();
        });

        binding.ACTVCategory.setAdapter(new ArrayAdapter<>(
                binding.getRoot().getContext(),
                android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.ds_khoa)));

        ActivityResultLauncher<PickVisualMediaRequest> pickImage =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    if (uri != null) {
                        binding.avaUserImageView.setImageURI(uri);
                    }
                    else {

                    }
                });

        binding.avaUserImageView.setOnClickListener(v -> {
            pickImage.launch(new PickVisualMediaRequest());
        });

    }
}