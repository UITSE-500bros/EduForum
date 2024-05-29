package com.example.eduforum.activity.ui.community;

import android.app.AlertDialog;
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

import com.bumptech.glide.Glide;
import com.example.eduforum.R;
import com.example.eduforum.activity.ui.community.adapter.MediaAdapter;
import com.example.eduforum.activity.ui.community.adapter.MediaItem;
import com.example.eduforum.activity.ui.community.viewstate.PostViewState;
import com.example.eduforum.activity.ui.main.fragment.CreateCommunityViewState;
import com.example.eduforum.activity.viewmodel.community.settings.ProfileCommunityViewModel;
import com.example.eduforum.databinding.ActivityProfileCommunityBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ProfileCommunityActivity extends AppCompatActivity {

    private ActivityProfileCommunityBinding binding;
    private ProfileCommunityViewModel viewModel;
    private CreateCommunityViewState currentCommunity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileCommunityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(ProfileCommunityViewModel.class);
        binding.setLifecycleOwner(this);
        currentCommunity = (CreateCommunityViewState) getIntent().getSerializableExtra("currentCommunity");
        if(currentCommunity == null) {
            Log.d("Intent to ProfileCommunityActivity", "currentCommunity is null");
            finish();
        }
        viewModel.setCommunityViewState(currentCommunity);
        binding.nameCommunityEditText.getEditText().setText(currentCommunity.getName());
        binding.descriptionCommnunityEditText.getEditText().setText(currentCommunity.getDescription());


        if(currentCommunity.getCommunityProfilePicture()!=null){
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(currentCommunity.getCommunityProfilePicture());
            Glide.with(binding.getRoot().getContext())
                    .load(storageReference)
                    .into(binding.avaCommunityImageView);
        }

        ActivityResultLauncher<PickVisualMediaRequest> pickImage =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    if (uri != null) {
                        binding.avaCommunityImageView.setImageURI(uri);
                        CreateCommunityViewState state = viewModel.getCommunityViewState().getValue();
                        assert state != null;
                        state.setCommuAvt(uri);
                        viewModel.setCommunityViewState(state);
                    }
                    else {
                        Snackbar.make(binding.getRoot(), "Không thể tải ảnh", Snackbar.LENGTH_SHORT).show();
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
        binding.ACTVCategory.setOnItemClickListener((parent, view, position, id) -> {
            CreateCommunityViewState state = viewModel.getCommunityViewState().getValue();
            assert state != null;
            state.setCategory(binding.ACTVCategory.getText().toString());
            viewModel.setCommunityViewState(state);
        });
        for(int i = 0; i < departmentItems.length; i++){
            if(departmentItems[i].equals(currentCommunity.getCategory())){
                binding.ACTVCategory.setListSelection(i);
                break;
            }
        }



        String[] accessItems = getResources().getStringArray(R.array.access_community);
        ArrayAdapter<String> accessAdapter = new ArrayAdapter<>(binding.getRoot().getContext(),
                android.R.layout.simple_dropdown_item_1line, accessItems);
        binding.ACTVAccess.setAdapter(accessAdapter);
        binding.ACTVAccess.setOnItemClickListener((parent, view, position, id) -> {
            CreateCommunityViewState state = viewModel.getCommunityViewState().getValue();
            assert state != null;
            if(parent.getItemAtPosition(position).toString().equals("Riêng tư")){
                state.setIsPublic(false);
            }
            else{
                state.setIsPublic(true);
            }
            viewModel.setCommunityViewState(state);
        });
        if(currentCommunity.getIsPublic()){
            binding.ACTVAccess.setListSelection(0);
        }
        else{
            binding.ACTVAccess.setListSelection(1);
        }

        binding.updateBtn.setOnClickListener(v -> {
            CreateCommunityViewState state = viewModel.getCommunityViewState().getValue();
            assert state != null;
            state.setName(binding.nameCommunityEditText.getEditText().getText().toString());
            state.setDescription(binding.descriptionCommnunityEditText.getEditText().getText().toString());
            viewModel.updateCommunityInfo();
        });
        viewModel.getIsSuccess().observe(this, isSuccess -> {
            if(isSuccess){
                new AlertDialog.Builder(this)
                        .setTitle("Thông báo")
                        .setMessage("Cập nhật thông tin cộng đồng thành công")
                        .setPositiveButton("OK", (dialog, which) -> {
                            finish();
                        })
                        .show();

            }
        });
    }
}