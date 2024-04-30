package com.example.eduforum.activity.ui.main.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.eduforum.R;
import com.example.eduforum.activity.ui.main.adapter.CommunityAdapter;
import com.example.eduforum.activity.viewmodel.main.CreateCommunityViewModel;
import com.example.eduforum.databinding.DialogCreateCommunityBinding;
import com.example.eduforum.databinding.FragmentHomeBinding;
import com.google.android.material.snackbar.Snackbar;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private CommunityAdapter generalCommusAdapter;
    private CommunityAdapter myCommusAdapter;
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

    }
    private void showCreateCommunityDialog() {
        Dialog createCommunityDialog = new Dialog(this.getContext());
        DialogCreateCommunityBinding dialogBinding = DialogCreateCommunityBinding.inflate(LayoutInflater.from(this.getContext()));
        CreateCommunityViewModel createCommuViewModel = new ViewModelProvider(this).get(CreateCommunityViewModel.class);
        // Set up dialog
        createCommunityDialog.setContentView(dialogBinding.getRoot());
        dialogBinding.setCreateCommuViewModel(createCommuViewModel);
        dialogBinding.setLifecycleOwner(this);
        String[] departmentItems = getResources().getStringArray(R.array.ds_khoa);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_dropdown_item_1line, departmentItems);

        dialogBinding.categoryACTV.setAdapter(categoryAdapter);
        dialogBinding.categoryACTV.setOnItemClickListener((parent, view, position, id) -> {
            createCommuViewModel.setCommunityCategory(categoryAdapter.getItem(position));
        });
        createCommuViewModel.getErrorMsg().observe(getViewLifecycleOwner(), errorMsg -> {
            if (errorMsg != null) {
                Snackbar.make(binding.getRoot(), errorMsg, Snackbar.LENGTH_SHORT).show();
            }
        });

        createCommuViewModel.getCreateNewCommunity().observe(getViewLifecycleOwner(), createNewCommunity -> {
            if (createNewCommunity) {
                //add new community to list
            }
        });
        createCommuViewModel.getCloseDialog().observe(getViewLifecycleOwner(), closeDialog -> {
            if (closeDialog) {
                createCommunityDialog.dismiss();
            }
        });
        createCommunityDialog.show();

    }
}