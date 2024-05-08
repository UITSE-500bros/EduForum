package com.example.eduforum.activity.ui.main.fragment;
import android.app.Dialog;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.eduforum.R;
import com.example.eduforum.activity.ui.community.adapter.CommunityAdapter;
import com.example.eduforum.activity.viewmodel.main.HomeViewModel;
import com.example.eduforum.databinding.DialogCreateCommunityBinding;
import com.example.eduforum.databinding.DialogJoinCommunityBinding;
import com.example.eduforum.databinding.FragmentHomeBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private CommunityAdapter joinedCommunitiesAdapter;
    private CommunityAdapter myCommunitiesAdapter;
    private ActivityResultLauncher<String> mGetContent;

    private DialogCreateCommunityBinding dialogBinding;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
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
//        pickMedia =
//                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
//                    if (uri != null) {
//                        Log.d("Gallery is opened", uri.toString());
//                        //TO DO: Handle the image uri data here
//                        dialogBinding.communityImage.setImageURI(uri);
//                        CreateCommunityViewState state = viewModel.getCommuLiveData().getValue();
//                        assert state != null;
//                        state.setCommuAvt(uri);
//                        viewModel.setCommuLiveData(state);
//
//                    } else {
//                        Snackbar.make(binding.getRoot(), "Không thể mở tài nguyên", Snackbar.LENGTH_SHORT).show();
//                    }
//                });
        joinedCommunitiesAdapter = new CommunityAdapter(getContext(), viewModel.getJoinedCommunityList().getValue(),  FirebaseAuth.getInstance());
        myCommunitiesAdapter = new CommunityAdapter(getContext(), viewModel.getIsAdminCommunityList().getValue(), FirebaseAuth.getInstance());
        binding.joinedCommunitiesRecyclerView.setAdapter(joinedCommunitiesAdapter);
        binding.myCommunitiesRecyclerView.setAdapter(myCommunitiesAdapter);
        binding.joinedCommunitiesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.myCommunitiesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        binding.createCommuButton.setOnClickListener(v -> {
            showCreateCommunityDialog();
        });
        binding.joinCommuButton.setOnClickListener(v -> {
            showJoinCommunityDialog();
        });

        viewModel.getJoinedCommunityList().observe(getViewLifecycleOwner(), joinedCommunities -> {
            joinedCommunitiesAdapter.setCommunityList(joinedCommunities);
        });

        viewModel.getIsAdminCommunityList().observe(getViewLifecycleOwner(), myCommunities -> {
            myCommunitiesAdapter.setCommunityList(myCommunities);
        });

    }
    private void showCreateCommunityDialog() {
        viewModel.setNewCommunityLiveData(new CreateCommunityViewState());
        Dialog createCommunityDialog = new Dialog(this.getContext());
        dialogBinding = DialogCreateCommunityBinding.inflate(LayoutInflater.from(this.getContext()));
        // Set up dialog
        createCommunityDialog.setContentView(dialogBinding.getRoot());
        dialogBinding.setViewModel(viewModel);
        dialogBinding.setLifecycleOwner(this);
        String[] departmentItems = getResources().getStringArray(R.array.ds_khoa);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_dropdown_item_1line, departmentItems);

        dialogBinding.categoryACTV.setAdapter(categoryAdapter);
        dialogBinding.categoryACTV.setOnItemClickListener((parent, view, position, id) -> {
            CreateCommunityViewState newCommunityState = viewModel.getNewCommunityLiveData().getValue();
            newCommunityState.setCategory(parent.getItemAtPosition(position).toString());
            viewModel.setNewCommunityLiveData(newCommunityState);
        });



//        dialogBinding.uploadImageButton.setOnClickListener(v -> {
//            // Launch the photo picker and let the user choose image
////            pickMedia.launch(new PickVisualMediaRequest.Builder()
////                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
////                    .build());
//        });

        viewModel.getIsAdminCommunityList().observe(getViewLifecycleOwner(), joinedCommunities -> {
            myCommunitiesAdapter.setCommunityList(joinedCommunities);
        });
        viewModel.getNewCommunityLiveData().observe(getViewLifecycleOwner(), newCommunity -> {
            if(newCommunity.getErrorMessage() != null){
                Snackbar.make(dialogBinding.getRoot(), newCommunity.getErrorMessage(), Snackbar.LENGTH_SHORT).show();
            }
            if (newCommunity.getIsDialogClosed()== true) {
                createCommunityDialog.dismiss();
            }
        });
        createCommunityDialog.show();

    }
    private void showJoinCommunityDialog(){
        viewModel.setJoinCommunityLiveData(new JoinCommunityViewState());
        Dialog joinCommunityDialog = new Dialog(this.getContext());
        DialogJoinCommunityBinding dialogBinding = DialogJoinCommunityBinding.inflate(LayoutInflater.from(this.getContext()));
        // Set up dialog
        joinCommunityDialog.setContentView(dialogBinding.getRoot());
        dialogBinding.setViewModel(viewModel);
        dialogBinding.setLifecycleOwner(this);

        viewModel.getJoinedCommunityList().observe(getViewLifecycleOwner(), joinedCommunities -> {
            joinedCommunitiesAdapter.setCommunityList(joinedCommunities);
        });
        viewModel.getJoinCommuLiveData().observe(getViewLifecycleOwner(), joinCommunity -> {
            if(joinCommunity.getErrorMessage() != null){
                Snackbar.make(dialogBinding.getRoot(), joinCommunity.getErrorMessage(), Snackbar.LENGTH_SHORT).show();
            }
            if (joinCommunity.getIsDialogClosed()) {
                joinCommunityDialog.dismiss();
            }
        });

        joinCommunityDialog.show();
    }

}