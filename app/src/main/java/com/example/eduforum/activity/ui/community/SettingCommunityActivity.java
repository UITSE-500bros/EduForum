package com.example.eduforum.activity.ui.community;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.eduforum.R;
import com.example.eduforum.activity.EduForum;
import com.example.eduforum.activity.ui.main.MainActivity;
import com.example.eduforum.activity.ui.main.fragment.CreateCommunityViewState;
import com.example.eduforum.activity.viewmodel.community.SettingsCommunityViewModel;
import com.example.eduforum.activity.viewmodel.shared.UserViewModel;
import com.example.eduforum.databinding.ActivitySettingCommunityBinding;
import com.google.android.material.snackbar.Snackbar;

public class SettingCommunityActivity extends AppCompatActivity {

    ActivitySettingCommunityBinding binding;
    CreateCommunityViewState currentCommunity;
    SettingsCommunityViewModel viewModel;
    UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingCommunityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(SettingsCommunityViewModel.class);
        EduForum app  = (EduForum) getApplication();
        userViewModel = app.getSharedViewModel(UserViewModel.class);
        userViewModel.getCurrentUserLiveData().observe(this, user -> {
            if(user != null) {
                viewModel.setUserId(user.getUserId());
            }
        });
        //set up turn back button in ActionBar
        binding.toolBarSettingCommunity.setNavigationOnClickListener(v -> {
            finish();
        });

        Boolean isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        Boolean isExploring = getIntent().getBooleanExtra("isExploring", false);
        if(!isAdmin){
            binding.removeCommunity.setVisibility(View.GONE);
            binding.settingCommunity.setVisibility(View.GONE);
            binding.categorySetting.setVisibility(View.GONE);
            binding.memberRequest.setVisibility(View.GONE);

        }
        if(isExploring){
            binding.leaveCommunity.setVisibility(View.GONE);
            binding.notificationSetting.setVisibility(View.GONE);
        }
        currentCommunity = (CreateCommunityViewState) getIntent().getSerializableExtra("currentCommunity");
        if(currentCommunity == null) {
            Log.e("Intent to SettingCommunityActivity", "currentCommunity is null");
            finish();
        }
        viewModel.setCommunityId(currentCommunity.getCommunityID());
        binding.settingCommunity.setOnClickListener(v -> {
            if(isAdmin){
                Intent intent = new Intent(this, ProfileCommunityActivity.class);
                intent.putExtra("currentCommunity", currentCommunity);
                startActivity(intent);
            }
            else {
                makeSnackBar("Bạn không phải quản trị viên");
            }
        });

        binding.categorySetting.setOnClickListener(v -> {
            //if user is admin- need a check from database
            if(isAdmin){
                Intent intent = new Intent(this, CustomTagsActivity.class);
                intent.putExtra("communityId",currentCommunity.getCommunityID());
                startActivity(intent);
            }
            else {
                makeSnackBar("Bạn không phải quản trị viên");
            }
        });

        binding.memberRequest.setOnClickListener(v -> {
            //if user is admin- need a check from database
            if(isAdmin){
                Intent intent = new Intent(this, MemberRequestActivity.class);
                intent.putExtra("communityId", currentCommunity.getCommunityID());
                startActivity(intent);
            }
            else {
                makeSnackBar("Bạn không phải quản trị viên");
            }
        });
        binding.memberSetting.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminMemberListActivity.class);
            intent.putExtra("communityId", currentCommunity.getCommunityID());
            intent.putExtra("isAdmin", isAdmin);
            startActivity(intent);
        });

        binding.leaveCommunity.setOnClickListener(v -> {
            //if user is admin- need a check from database
                new AlertDialog.Builder(this)
                        .setTitle("Rời khỏi cộng đồng")
                        .setMessage("Bạn có chắc chắn muốn rời khỏi cộng đồng không?")
                        .setPositiveButton("Có", (dialog, which) -> {
                            viewModel.leaveCommunity();
                        })
                        .setNegativeButton("Không", null)
                        .show();

        });
        binding.notiButtonSetting.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.toggleNotificationStatus(isChecked);
        });
        viewModel.getIsNotified().observe(this, isNotified -> {
            binding.notiButtonSetting.setChecked(isNotified);
        });
        viewModel.getIsLeaveSuccess().observe(this, isLeaveSuccess -> {
            if(isLeaveSuccess) {
                Intent intent = new Intent(SettingCommunityActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        viewModel.getErrorMessage().observe(this, errorMessage -> {
            if(errorMessage != null) {
                makeSnackBar(errorMessage);
            }
        });

        if(isAdmin){
            binding.removeCommunity.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("Xóa cộng đồng")
                        .setMessage("Bạn có chắc chắn muốn xóa cộng đồng không?")
                        .setPositiveButton("Có", (dialog, which) -> {
                            viewModel.deleteCommunity();
                            Intent intent = new Intent(SettingCommunityActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        })
                        .setNegativeButton("Không", null)
                        .show();
            });
        }
    }
    void makeSnackBar(String message){
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
    }
}