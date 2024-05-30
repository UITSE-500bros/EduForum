package com.example.eduforum.activity.ui.setting_main;

import android.app.AlertDialog;
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
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.eduforum.R;
import com.example.eduforum.activity.EduForum;
import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.activity.viewmodel.main.settings.ProfileUserSettingViewModel;
import com.example.eduforum.activity.viewmodel.shared.UserViewModel;
import com.example.eduforum.databinding.ActivityProfileUserSettingBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileUserSettingActivity extends AppCompatActivity {

    ActivityProfileUserSettingBinding binding;
    ProfileUserSettingViewModel viewModel;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user_setting);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_user_setting);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(ProfileUserSettingViewModel.class);
        EduForum app  = (EduForum) getApplication();
        userViewModel = app.getSharedViewModel(UserViewModel.class);
        userViewModel.getCurrentUserLiveData().observe(this, user -> {
            if(user != null) {
                viewModel.setUserLiveData(user);

            }
        });
        viewModel.getUserLiveData().observe(this, user -> {
            if(user != null) {
                binding.nameUserEditText.getEditText().setText(user.getName());
                binding.phoneNumberEditText.getEditText().setText(user.getPhoneNumber());
                binding.ACTVCategory.setText(user.getDepartment());
                if(user.getProfilePicture()!=null && !user.getProfilePicture().isEmpty()){
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(user.getProfilePicture());
                    Glide.with(binding.getRoot().getContext())
                            .load(storageReference)
                            .into(binding.avaUserImageView);
                }
            }
        });
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
                        User user = viewModel.getUserLiveData().getValue();
                        if(user != null) {
                            user.setUploadPicture(uri);
                            viewModel.setUserLiveData(user);
                        }
                    }
                    else {

                    }
                });

        binding.avaUserImageView.setOnClickListener(v -> {
            pickImage.launch(new PickVisualMediaRequest());
        });

        binding.updateBtn.setOnClickListener(v -> {
            User user = viewModel.getUserLiveData().getValue();
            if(user != null) {
                user.setName(binding.nameUserEditText.getEditText().getText().toString());
                user.setPhoneNumber(binding.phoneNumberEditText.getEditText().getText().toString());
                user.setDepartment(binding.ACTVCategory.getText().toString());
                viewModel.setUserLiveData(user);
                viewModel.updateUserProfile();
            }
        });
        viewModel.getIsUpdateSuccess().observe(this, isUpdateSuccess -> {
            if(isUpdateSuccess) {
                new AlertDialog.Builder(this)
                        .setTitle("Cập nhật thông tin của bạn")
                        .setMessage("Cập nhật thông tin thành công")
                        .setPositiveButton("OK", (dialog, which) -> {
                            dialog.dismiss();
                            finish();
                        })
                        .show();
            }
        });
    }
}