package com.example.eduforum.activity.ui.community;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.R;
import com.example.eduforum.activity.model.post_manage.PostCategory;
import com.example.eduforum.activity.ui.community.adapter.TagsAdapter;
import com.example.eduforum.activity.viewmodel.community.settings.CustomTagsViewModel;
import com.example.eduforum.databinding.ActivityCustomTagsBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class CustomTagsActivity extends AppCompatActivity {
    private ActivityCustomTagsBinding binding;
    private TagsAdapter tagsAdapter;
    private CustomTagsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCustomTagsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(CustomTagsViewModel.class);
        binding.setLifecycleOwner(this);

        String communityId = getIntent().getStringExtra("communityId");
        if(communityId != null) {
            viewModel.setCommunityId(communityId);
            viewModel.refreshCategories();
        }
        MaterialToolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        RecyclerView recyclerView = binding.recyclerViewTags;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        tagsAdapter = new TagsAdapter(viewModel.getCategoryList().getValue(), null, false, true);
        tagsAdapter.setOnTagInteractionListener(new TagsAdapter.OnTagInteractionListener() {
            @Override
            public void onTagDeleted(PostCategory deletedTag) {
                viewModel.deleteCategory(deletedTag);
            }
        });
        recyclerView.setAdapter(tagsAdapter);

        viewModel.getCategoryList().observe(this, postCategories -> {
            tagsAdapter.setTagsList(postCategories);
        });

        viewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Snackbar.make(binding.getRoot(), errorMessage, Snackbar.LENGTH_SHORT).show();
            }
        });

        binding.addTagButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thêm tag mới");
            builder.setMessage("Nhập tên tag mới");
            EditText editText = new EditText(this);
            builder.setView(editText);
            builder.setPositiveButton("Thêm", (dialog, which) -> {
                String newTagName = editText.getText().toString();
                PostCategory newTag = new PostCategory(null,newTagName);
                viewModel.addCategory(newTag);
            });
            builder.setNegativeButton("Hủy", (dialog, which) -> {
                dialog.dismiss();
            });
            builder.show();
        });

    }
}