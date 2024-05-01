package com.example.eduforum.activity.ui.main.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.eduforum.R;
import com.example.eduforum.activity.ui.main.adapter.CommunityAdapter;
import com.example.eduforum.activity.viewmodel.main.HomeViewModel;
import com.example.eduforum.databinding.DialogCreateCommunityBinding;
import com.example.eduforum.databinding.DialogJoinCommunityBinding;
import com.example.eduforum.databinding.FragmentHomeBinding;
import com.google.android.material.snackbar.Snackbar;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private CommunityAdapter joinedCommunitiesAdapter;
    private CommunityAdapter myCommunitiesAdapter;
    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //generalCommusAdapter = new CommunityAdapter();
        //myCommusAdapter = new CommunityAdapter();
        binding.createCommuButton.setOnClickListener(v -> {
            showCreateCommunityDialog();
        });
        binding.joinCommuButton.setOnClickListener(v -> {
            showJoinCommunityDialog();
        });
        viewModel.getErrorMsg().observe(getViewLifecycleOwner(), errorMsg -> {
            if (errorMsg != null) {
                Snackbar.make(binding.getRoot(), errorMsg, Snackbar.LENGTH_SHORT).show();
            }
        });



    }
    private void showCreateCommunityDialog() {
        Dialog createCommunityDialog = new Dialog(this.getContext());
        DialogCreateCommunityBinding dialogBinding = DialogCreateCommunityBinding.inflate(LayoutInflater.from(this.getContext()));
        // Set up dialog
        createCommunityDialog.setContentView(dialogBinding.getRoot());
        dialogBinding.setViewModel(viewModel);
        dialogBinding.setLifecycleOwner(this);
        String[] departmentItems = getResources().getStringArray(R.array.ds_khoa);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_dropdown_item_1line, departmentItems);

        dialogBinding.categoryACTV.setAdapter(categoryAdapter);
        dialogBinding.categoryACTV.setOnItemClickListener((parent, view, position, id) -> {
            viewModel.setCommunityCategory(categoryAdapter.getItem(position));
        });


       viewModel.getIsCreateCommunitySuccess().observe(getViewLifecycleOwner(), isCreateCommunitySuccess -> {
            if (isCreateCommunitySuccess) {
                Snackbar.make(binding.getRoot(), "Create community success", Snackbar.LENGTH_SHORT).show();
            }
        });
        viewModel.getCreateCommunityDialogIsClosed().observe(getViewLifecycleOwner(), createCommunityDialogIsClosed -> {
            if (createCommunityDialogIsClosed) {
                createCommunityDialog.dismiss();
            }
        });
        createCommunityDialog.show();

    }
    private void showJoinCommunityDialog(){
        Dialog joinCommunityDialog = new Dialog(this.getContext());
        DialogJoinCommunityBinding dialogBinding = DialogJoinCommunityBinding.inflate(LayoutInflater.from(this.getContext()));
        // Set up dialog
        joinCommunityDialog.setContentView(dialogBinding.getRoot());
        dialogBinding.setViewModel(viewModel);
        dialogBinding.setLifecycleOwner(this);
        viewModel.getIsJoinCommunitySuccess().observe(getViewLifecycleOwner(), isJoinCommunitySuccess -> {
            if (isJoinCommunitySuccess) {
                Snackbar.make(binding.getRoot(), "Join community success", Snackbar.LENGTH_SHORT).show();
            }
        });
        viewModel.getJoinCommunityDialogIsClosed().observe(getViewLifecycleOwner(), joinCommunityDialogIsClosed -> {
            if (joinCommunityDialogIsClosed) {
                joinCommunityDialog.dismiss();
            }
        });

        joinCommunityDialog.show();
    }
}