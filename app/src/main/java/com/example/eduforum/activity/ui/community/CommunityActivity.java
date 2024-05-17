package com.example.eduforum.activity.ui.community;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eduforum.R;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.eduforum.activity.ui.community.adapter.PostAdapter;
import com.example.eduforum.activity.ui.main.fragment.CreateCommunityViewState;
import com.example.eduforum.activity.viewmodel.community.NewsFeedViewModel;
import com.example.eduforum.databinding.ActivityCommunityBinding;
import com.google.android.material.appbar.MaterialToolbar;

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
        if (currentCommunity != null) {
            viewModel.setCurrentCommunity(currentCommunity);
        } else {
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
            if (community != null) {
                binding.communityName.setText(community.getName());
                binding.descriptionContentTextview.setText(community.getDescription());
            }
        });
        viewModel.getPostList().observe(this, postList -> {
            postAdapter.setPostList(postList);
        });
        //setup toolbar
        MaterialToolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.community_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.filter) {
            // Thêm mã xử lý sự kiện click vào đây
            // Ví dụ: hiển thị một thông báo
            Toast.makeText(this, "Filter clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}