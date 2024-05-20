package com.example.eduforum.activity.ui.community;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.example.eduforum.R;
import com.example.eduforum.databinding.ActivityProfileCommunityBinding;

public class ProfileCommunityActivity extends AppCompatActivity {

    private ActivityProfileCommunityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_community);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_community);

        String[] departmentItems = getResources().getStringArray(R.array.category_community);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(binding.getRoot().getContext(),
                android.R.layout.simple_dropdown_item_1line, departmentItems);

        binding.ACTVCategory.setAdapter(categoryAdapter);

        String[] accessItems = getResources().getStringArray(R.array.access_community);
        ArrayAdapter<String> accessAdapter = new ArrayAdapter<>(binding.getRoot().getContext(),
                android.R.layout.simple_dropdown_item_1line, accessItems);

        binding.ACTVAccess.setAdapter(accessAdapter);
    }
}