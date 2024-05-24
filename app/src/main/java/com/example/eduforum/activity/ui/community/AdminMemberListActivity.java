package com.example.eduforum.activity.ui.community;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.R;
import com.example.eduforum.activity.model.community_manage.CommunityMember;
import com.example.eduforum.activity.ui.community.adapter.MemberListAdapter;
import com.example.eduforum.activity.util.LoadingDialog;
import com.example.eduforum.activity.viewmodel.community.settings.AdminMemberListViewModel;
import com.example.eduforum.databinding.ActivityAdminMemberListBinding;
import com.example.eduforum.databinding.BottomDialogMemberListBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import  androidx.appcompat.widget.SearchView;

import java.util.List;

public class AdminMemberListActivity extends AppCompatActivity {
    private ActivityAdminMemberListBinding binding;
    private AdminMemberListViewModel viewModel;
    private MemberListAdapter memberListAdapter;
    private MemberListAdapter adminListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminMemberListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // This should be the only call to setContentView
        viewModel = new ViewModelProvider(this).get(AdminMemberListViewModel.class);

        String communityId = getIntent().getStringExtra("communityId");
        // isAdmin is set to true for testing
        Boolean isAdmin = getIntent().getBooleanExtra("isAdmin", true);
        if(communityId != null) {
            viewModel.setCommunityId(communityId);
        }

        MaterialToolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // setup recyclerView
        //admin recycler view
        RecyclerView memberMemberListRecyclerView = binding.MemberrecyclerView;
        memberMemberListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        memberMemberListRecyclerView.setHasFixedSize(true);
        memberListAdapter = new MemberListAdapter(viewModel.getMemberList().getValue());
        memberListAdapter.setOnMemberClickListener(new MemberListAdapter.OnMemberClickListener() {
            @Override
            public void onMemberClick(CommunityMember member) {
                // xem thong tin thanh vien
            }

            @Override
            public void onMemberLongClick(CommunityMember member) {
// Create and show the BottomSheetDialog
                BottomDialogMemberListBinding bottomDialogBinding = BottomDialogMemberListBinding.inflate(getLayoutInflater());
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(AdminMemberListActivity.this);
                bottomSheetDialog.setContentView(bottomDialogBinding.getRoot());

                bottomDialogBinding.setMember(member);

                // Set up the buttons in the BottomSheetDialog

                bottomDialogBinding.btnpromote.setOnClickListener(v1 -> {
                    viewModel.manageAdmin(member.getMemberId(), true);
                    bottomSheetDialog.dismiss();
                });

                bottomDialogBinding.btndelete.setOnClickListener(v1 -> {
                    viewModel.deleteMember(member.getMemberId());
                    bottomSheetDialog.dismiss();
                });

                bottomSheetDialog.show();

            }
        });
        memberMemberListRecyclerView.setAdapter(memberListAdapter);

        //member recycler view
        RecyclerView adminListRecyclerView = binding.AdminrecyclerView;
        adminListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adminListRecyclerView.setHasFixedSize(true);
        adminListAdapter = new MemberListAdapter(viewModel.getAdminList().getValue());
        adminListAdapter.setOnMemberClickListener(new MemberListAdapter.OnMemberClickListener() {
            @Override
            public void onMemberClick(CommunityMember member) {
                // xem thong tin admin
            }

            @Override
            public void onMemberLongClick(CommunityMember member) {
                // create and show the BottomSheetDialog for admin
                // viewModel.manageAdmin(member.getMemberId(), false);
                // viewModel.deleteMember(member.getMemberId());
            }
            });
        adminListRecyclerView.setAdapter(adminListAdapter);

        viewModel.getMemberList().observe(this, communityMembers -> {
            memberListAdapter.setMemberList(communityMembers);
        });
        viewModel.getAdminList().observe(this, communityMembers -> {
            adminListAdapter.setMemberList(communityMembers);
        });

        LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoadingDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismissDialog();
            }
        }, 3000); // Delay of 3 seconds (3000 milliseconds)


    }
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Tìm kiếm thành viên");

        ((EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text)).setTextColor(getResources().getColor((R.color.white)));
        ((EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text)).setHintTextColor(getResources().getColor((R.color.hintColorText)));

//        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // filter(query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                // filter(newText);
//                return true;
//            }
//        });

        return true;
    }
}