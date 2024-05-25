package com.example.eduforum.activity.ui.main.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eduforum.R;
import com.example.eduforum.activity.EduForum;
import com.example.eduforum.activity.model.post_manage.Creator;
import com.example.eduforum.activity.ui.community.viewstate.PostViewState;
import com.example.eduforum.activity.ui.main.adapter.ExploreCommunityAdapter;
import com.example.eduforum.activity.viewmodel.main.ExploreViewModel;
import com.example.eduforum.activity.viewmodel.shared.UserViewModel;
import com.example.eduforum.databinding.FragmentExploreBinding;
import com.google.android.material.snackbar.Snackbar;

public class ExploreFragment extends Fragment {
    FragmentExploreBinding binding;
    ExploreViewModel viewModel;
    UserViewModel userViewModel;
    ExploreCommunityAdapter adapter;

    public ExploreFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentExploreBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ExploreViewModel.class);
        EduForum app = (EduForum) getActivity().getApplication();
        userViewModel = app.getSharedViewModel(UserViewModel.class);
        userViewModel.getCurrentUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if(user != null) {
                viewModel.setCurrentUser(user);
            }
        });
        binding.setLifecycleOwner(this);

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMsg -> {
            Snackbar.make(binding.getRoot(), errorMsg, Snackbar.LENGTH_SHORT).show();
        });

        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        adapter = new ExploreCommunityAdapter(viewModel.getCommunityList().getValue());
        viewModel.getCommunityList().observe(getViewLifecycleOwner(), communities -> {
            adapter.setCommunityList(communities);
        });
        adapter.setOnJoinCommunityClickListener((v, communityId) -> {
            viewModel.joinCommunity(communityId);
        });
        RecyclerView recyclerView = binding.exploreCommusRecyclerView;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}