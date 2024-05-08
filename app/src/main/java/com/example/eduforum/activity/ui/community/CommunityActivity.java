package com.example.eduforum.activity.ui.community;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.eduforum.activity.ui.main.fragment.CreateCommunityViewState;
import com.example.eduforum.activity.viewmodel.community.NewsFeedViewModel;
import com.example.eduforum.databinding.ActivityCommunityBinding;

public class CommunityActivity extends AppCompatActivity {
    ActivityCommunityBinding binding;
    NewsFeedViewModel viewModel;
    //CommunityAdapter communityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCommunityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

        viewModel.getCurrentCommunity().observe(this, community -> {
            if(community != null){
                binding.communityName.setText(community.getName());
                binding.descriptionContentTextview.setText(community.getDescription());
            }
        });
    }
    private void init(){
        viewModel = new ViewModelProvider(this).get(NewsFeedViewModel.class);
        String communityId = getIntent().getStringExtra("communityId");
        if(communityId != null) {
            viewModel.setCommunityId(communityId);
        }
        else{
            // finish();
        }
    }
}