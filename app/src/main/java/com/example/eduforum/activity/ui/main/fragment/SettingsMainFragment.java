package com.example.eduforum.activity.ui.main.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.eduforum.R;
import com.example.eduforum.activity.EduForum;
import com.example.eduforum.activity.ui.auth.NewPassActivity;
import com.example.eduforum.activity.ui.setting_main.ContactActivity;
import com.example.eduforum.activity.ui.setting_main.ProfileUserSettingActivity;
import com.example.eduforum.activity.viewmodel.shared.UserViewModel;
import com.example.eduforum.databinding.FragmentSettingsMainBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SettingsMainFragment extends Fragment {
    private FragmentSettingsMainBinding binding;
    private UserViewModel userViewModel;
    public SettingsMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsMainBinding.inflate(inflater, container, false);
        EduForum app  = (EduForum) getActivity().getApplication();
        userViewModel = app.getSharedViewModel(UserViewModel.class);
        userViewModel.getCurrentUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if(user != null) {
                binding.username.setText(user.getName());
                if(user.getProfilePicture()!=null && !user.getProfilePicture().isEmpty()){
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(user.getProfilePicture());
                    Glide.with(binding.getRoot().getContext())
                            .load(storageReference)
                            .into(binding.avatar);
                }
            }
        });


        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonBarCustomProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProfileUserSettingActivity.class);
            startActivity(intent);
        });

        binding.buttonBarChangePassword.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NewPassActivity.class);
            startActivity(intent);
        });
        binding.buttonBarSupport.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ContactActivity.class);
            startActivity(intent);
        });
        binding.buttonBarLogout.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Đăng xuất")
                    .setMessage("Bạn có chắc chắn muốn đăng xuất không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        // Logout
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }
    }
