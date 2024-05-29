package com.example.eduforum.activity.ui.community;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

import com.example.eduforum.R;
import com.example.eduforum.activity.ui.community.adapter.MediaAdapter;
import com.example.eduforum.activity.ui.community.adapter.MediaItem;
import com.example.eduforum.activity.ui.community.viewstate.PostViewState;
import com.example.eduforum.activity.ui.main.fragment.CreateCommunityViewState;
import com.example.eduforum.activity.viewmodel.community.settings.ProfileCommunityViewModel;
import com.example.eduforum.databinding.ActivityProfileCommunityBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ProfileCommunityActivity extends AppCompatActivity {

    private ActivityProfileCommunityBinding binding;
    private ProfileCommunityViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileCommunityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(ProfileCommunityViewModel.class);
        binding.setLifecycleOwner(this);
        CreateCommunityViewState currentCommunity = (CreateCommunityViewState) getIntent().getSerializableExtra("currentCommunity");
        if(currentCommunity == null) {
            Log.d("Intent to ProfileCommunityActivity", "currentCommunity is null");
            finish();
        }
        viewModel.setCommunityViewState(currentCommunity);

        ActivityResultLauncher<PickVisualMediaRequest> pickImage =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    if (uri != null) {
                        binding.avaCommunityImageView.setImageURI(uri);
                    }
                    else {

                    }
                });


        //set up turn back button in ActionBar
        binding.toolBarProfileCommunity.setNavigationOnClickListener(v -> {
            finish();
        });

        binding.avaCommunityImageView.setOnClickListener(v -> {
            pickImage.launch(new PickVisualMediaRequest());
        });


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