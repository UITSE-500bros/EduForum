package com.example.eduforum.activity.ui.community;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.eduforum.R;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.activity.model.post_manage.Category;
import com.example.eduforum.activity.repository.post.PostQuery;
import com.example.eduforum.activity.ui.community.adapter.PostAdapter;
import com.example.eduforum.activity.ui.community.adapter.TagsAdapter;
import com.example.eduforum.activity.ui.community.viewstate.FilterViewState;
import com.example.eduforum.activity.ui.main.fragment.CreateCommunityViewState;
import com.example.eduforum.activity.viewmodel.community.NewsFeedViewModel;
import com.example.eduforum.activity.viewmodel.community.settings.CustomTagsViewModel;
import com.example.eduforum.databinding.ActivityCommunityBinding;
import com.example.eduforum.databinding.CommunityFilterBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class CommunityActivity extends AppCompatActivity {
    ActivityCommunityBinding binding;
    NewsFeedViewModel viewModel;
    //CustomTagsViewModel customTagsViewModel;
    PostAdapter postAdapter;
    Boolean isAdmin;
    Boolean isExploring;
    Boolean isUITcommunity;
    CreateCommunityViewState currentCommunity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityCommunityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(NewsFeedViewModel.class);
        //customTagsViewModel = new ViewModelProvider(this).get(CustomTagsViewModel.class);
        binding.setLifecycleOwner(this);

        currentCommunity = (CreateCommunityViewState) getIntent().getSerializableExtra("currentCommunity");
        if (currentCommunity != null) {
            viewModel.setCurrentCommunity(currentCommunity);
            viewModel.updateCategories();
            binding.communityName.setText(currentCommunity.getName());
            binding.descriptionTextview.setText(currentCommunity.getCategory());
            binding.descriptionContentTextview.setText(currentCommunity.getDescription());
            binding.memberCountTextview.setText(currentCommunity.getTotalMembers().toString());
            binding.postCountTextview.setText(currentCommunity.getTotalPosts().toString());
            if(currentCommunity.getCommunityProfilePicture()!=null && !currentCommunity.getCommunityProfilePicture().equals("default")){
                StorageReference storageReference = FirebaseStorage.getInstance().getReference(currentCommunity.getCommunityProfilePicture());
                Glide.with(binding.getRoot().getContext())
                        .load(storageReference)
                        .into(binding.postImageView);
            }
        } else {
            //finish();
        }
        viewModel.getIsNotified().observe(this, isNotified -> {
            if (isNotified) {
                binding.joinTextview.setText("Thông báo đang bật");
            }
            else{
                binding.joinTextview.setText("Thông báo đang tắt");
            }
        });
        isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        isExploring = getIntent().getBooleanExtra("isExploring", false);
        isUITcommunity = getIntent().getBooleanExtra("isUITcommunity", false);
        // setup postRecyclerView
        postAdapter = new PostAdapter(this, viewModel.getPostList().getValue());
        postAdapter.setIsUITcommunity(isUITcommunity);
        postAdapter.setIsExploring(isExploring);
        binding.postRecyclerView.setAdapter(postAdapter);
        binding.postRecyclerView.setNestedScrollingEnabled(false);
        binding.postRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ActivityResultLauncher<Intent> createPostActivityResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        viewModel.refreshPostList();
                        currentCommunity.setTotalPosts(currentCommunity.getTotalPosts() + 1);
                        binding.postCountTextview.setText(currentCommunity.getTotalPosts().toString());
                    }
                }
        );
        if(isExploring){
            binding.createPostEditTextButton.setEnabled(false);
            binding.joinedTextview.setText("Chưa tham gia");
        }
        if(isUITcommunity){
            binding.createPostEditTextButton.setEnabled(false);
        }
        binding.createPostEditTextButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreatePostActivity.class);
            assert currentCommunity != null;
            intent.putExtra("communityId", currentCommunity.getCommunityID());
            createPostActivityResult.launch(intent);
        });
        viewModel.getCurrentCommunity().observe(this, community -> {
            if (community != null) {

            }
        });
        viewModel.getPostList().observe(this, postList -> {
            postAdapter.setPostList(postList);
        });
        viewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        //setup toolbar
        MaterialToolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.community_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.filterBySearch(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    viewModel.refreshPostList();
                }
                return true;
            }
        });

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       int id = item.getItemId();
        if (id == R.id.filter) {
            Dialog filterDialog = new Dialog(this);
            CommunityFilterBinding filterBinding = CommunityFilterBinding.inflate(getLayoutInflater());
            filterDialog.setContentView(filterBinding.getRoot());
            filterBinding.setLifecycleOwner(this);

            // get the previous filter state
            FilterViewState currentFilter = viewModel.getCurrentFilter().getValue();
            switch (currentFilter.getPostQuery()) {
                case NEWEST:
                    filterBinding.newestRadioButton.setChecked(true);
                    break;
                case OLDEST:
                    filterBinding.oldestRadioButton.setChecked(true);
                    break;
                case MOST_COMMENTED:
                    filterBinding.mostCommentRadioButton.setChecked(true);
                    break;
                case MOST_VOTED:
                    filterBinding.mostVoteRadioButton.setChecked(true);
                    break;
            }
            //setup RadioButtons (allow only one selection)
            List<RadioButton> radioButtons = new ArrayList<>();
            radioButtons.add(filterBinding.newestRadioButton);
            radioButtons.add(filterBinding.oldestRadioButton);
            radioButtons.add(filterBinding.mostCommentRadioButton);
            radioButtons.add(filterBinding.mostVoteRadioButton);
            for(RadioButton radioButton : radioButtons) {
                radioButton.setOnClickListener(v -> {
                    for(RadioButton rb : radioButtons) {
                        if (rb != radioButton) {
                            rb.setChecked(false);
                        }
                    }
                });
            }

            TagsAdapter tagsAdapter = new TagsAdapter( viewModel.getAllCategories().getValue(), currentFilter.getTags(), true, false);
            filterBinding.categoryRecyclerView.setAdapter(tagsAdapter);
            filterBinding.categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            filterBinding.applyButton.setOnClickListener(v -> {
                FilterViewState filterViewState = new FilterViewState();
                if (filterBinding.newestRadioButton.isChecked()) {
                    filterViewState.setPostQuery(PostQuery.NEWEST);
                } else if (filterBinding.oldestRadioButton.isChecked()) {
                    filterViewState.setPostQuery(PostQuery.OLDEST);
                } else if (filterBinding.mostCommentRadioButton.isChecked()) {
                    filterViewState.setPostQuery(PostQuery.MOST_COMMENTED);
                } else if (filterBinding.mostVoteRadioButton.isChecked()) {
                    filterViewState.setPostQuery(PostQuery.MOST_VOTED);
                }
                if(tagsAdapter.getSelectedTags().size() != 0){
                    filterViewState.setTags(tagsAdapter.getSelectedTags());
                }
                viewModel.setFilter(filterViewState);
                filterDialog.dismiss();
            });
            filterBinding.cancelButton.setOnClickListener(v -> {
                filterDialog.dismiss();
            });

            filterDialog.show();
            return true;
        }

        if (id == R.id.setting) {
           Intent intent = new Intent(this, SettingCommunityActivity.class);
            if(currentCommunity==null) return true;
            intent.putExtra("currentCommunity", currentCommunity);
            intent.putExtra("communityId", viewModel.getCurrentCommunity().getValue().getCommunityID());
           intent.putExtra("isAdmin", isAdmin);
           startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}