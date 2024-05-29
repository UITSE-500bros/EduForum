package com.example.eduforum.activity.ui.community;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.R;
import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.activity.ui.community.adapter.MemberRequestsAdapter;
import com.example.eduforum.activity.viewmodel.community.settings.MemberRequestsViewModel;
import com.example.eduforum.databinding.ActivityMemberRequestBinding;

public class MemberRequestActivity extends AppCompatActivity {

    ActivityMemberRequestBinding binding;
    MemberRequestsViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMemberRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MemberRequestsViewModel.class);

        binding.toolBarMemberRequest.setNavigationOnClickListener(v -> {
            finish();
        });

        String communityId = getIntent().getStringExtra("communityId");
        if(communityId != null) {
            viewModel.setCommunityId(communityId);
        }
        RecyclerView recyclerView = binding.recyclerView;
        // set adapter
        MemberRequestsAdapter adapter = new MemberRequestsAdapter(viewModel.getMemberRequests().getValue());
        adapter.setOnMemberRequestListener(new MemberRequestsAdapter.OnMemberRequestListener() {
            @Override
            public void onReview(User user, Boolean isApproved) {
                viewModel.memberApproval(user.getUserId(), isApproved);
            }
        });
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getMemberRequests().observe(this, users -> {
            adapter.setMemberRequests(users);
            binding.numberMemberRequestTextView.setText(String.valueOf(users.size())+" yêu cầu tham gia");
        });
    }
}