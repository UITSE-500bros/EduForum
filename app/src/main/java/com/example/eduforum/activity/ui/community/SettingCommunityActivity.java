package com.example.eduforum.activity.ui.community;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eduforum.R;
import com.example.eduforum.activity.ui.main.fragment.CreateCommunityViewState;
import com.example.eduforum.databinding.ActivitySettingCommunityBinding;
import com.google.android.material.snackbar.Snackbar;

public class SettingCommunityActivity extends AppCompatActivity {

    ActivitySettingCommunityBinding binding;
    CreateCommunityViewState currentCommunity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingCommunityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //set up turn back button in ActionBar
        binding.toolBarSettingCommunity.setNavigationOnClickListener(v -> {
            finish();
        });

        Boolean isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        currentCommunity = (CreateCommunityViewState) getIntent().getSerializableExtra("currentCommunity");
        if(currentCommunity == null) {
            Log.e("Intent to SettingCommunityActivity", "currentCommunity is null");
            finish();
        }

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
    }
    void makeSnackBar(String message){
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
    }
}