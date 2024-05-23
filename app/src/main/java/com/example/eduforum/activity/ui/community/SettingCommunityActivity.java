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
import com.example.eduforum.databinding.ActivitySettingCommunityBinding;

public class SettingCommunityActivity extends AppCompatActivity {

    ActivitySettingCommunityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingCommunityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String communityId = getIntent().getStringExtra("communityId");
        if(communityId == null) {
            Log.e("Intent to SettingCommunityActivity", "communityId is null");
            finish();
        }

        binding.categorySetting.setOnClickListener(v -> {
            //if user is admin- need a check from database
            Intent intent = new Intent(this, CustomTagsActivity.class);
            intent.putExtra("communityId", communityId);
            startActivity(intent);
        });

        binding.memberRequest.setOnClickListener(v -> {
            //if user is admin- need a check from database
            Intent intent = new Intent(this, MemberRequestActivity.class);
            intent.putExtra("communityId", communityId);
            startActivity(intent);
        });
        binding.memberSetting.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminMemberListActivity.class);
            intent.putExtra("communityId", communityId);
            // isAdmin is set to true for testing
            intent.putExtra("isAdmin", true);
            startActivity(intent);
        });
    }
}