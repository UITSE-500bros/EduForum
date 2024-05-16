package com.example.eduforum.activity.ui.community;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eduforum.R;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.eduforum.activity.ui.community.adapter.PostAdapter;
import com.example.eduforum.activity.ui.main.fragment.CreateCommunityViewState;
import com.example.eduforum.activity.viewmodel.community.NewsFeedViewModel;
import com.example.eduforum.databinding.ActivityCommunityBinding;
import com.google.android.material.dialog.MaterialDialogs;

public class CommunityActivity extends AppCompatActivity {
    ActivityCommunityBinding binding;
    NewsFeedViewModel viewModel;

    PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityCommunityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(NewsFeedViewModel.class);
        binding.setLifecycleOwner(this);

        CreateCommunityViewState currentCommunity = (CreateCommunityViewState) getIntent().getSerializableExtra("currentCommunity");
        if(currentCommunity != null) {
            viewModel.setCurrentCommunity(currentCommunity);
        }
        else{
            //finish();
        }
        // setup postRecyclerView
        postAdapter = new PostAdapter(this, viewModel.getPostList().getValue());
        binding.postRecyclerView.setAdapter(postAdapter);
        binding.postRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.createPostEditTextButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreatePostActivity.class);
            intent.putExtra("communityId", currentCommunity.getCommunityID());
            startActivity(intent);
        });
        viewModel.getCurrentCommunity().observe(this, community -> {
            if(community != null){
                binding.communityName.setText(community.getName());
                binding.descriptionContentTextview.setText(community.getDescription());
            }
        });
        viewModel.getPostList().observe(this, postList -> {
            postAdapter.setPostList(postList);
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.communitymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.filter)
        {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.community_filter);
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}