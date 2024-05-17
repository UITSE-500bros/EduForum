package com.example.eduforum.activity.ui.community;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.community_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            //event  when user submit the search query
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return true;
//            }
//
//            //event when user change the search query
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                return true;
//            }
//        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       int id = item.getItemId();
        if (id == R.id.filter) {
            Dialog filterDialog = new Dialog(this);
            filterDialog.setContentView(R.layout.community_filter);
            filterDialog.show();
            return true;
        }

        if (id == R.id.setting) {
            Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}