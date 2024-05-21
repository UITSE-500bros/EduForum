package com.example.eduforum.activity.ui.community;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.R;
import com.example.eduforum.activity.model.post_manage.PostCategory;
import com.example.eduforum.activity.ui.community.adapter.TagsAdapter;
import com.example.eduforum.databinding.ActivityCustomTagsBinding;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;


public class CustomTagsActivity extends AppCompatActivity {
    private ActivityCustomTagsBinding binding;
    private TagsAdapter tagsAdapter;
    private List<PostCategory> tagsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_custom_tags);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = ActivityCustomTagsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        RecyclerView recyclerView = binding.recyclerViewTags;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//      tagsAdapter = new TagsAdapter( tagsList);
//        recyclerView.setAdapter(tagsAdapter);

        binding.addTagButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thêm tag mới");
            builder.setMessage("Nhập tên tag mới");
            EditText editText = new EditText(this);
            builder.setView(editText);
            builder.setPositiveButton("Thêm", (dialog, which) -> {
                String newTagName = editText.getText().toString();
                // Add new tag to the list
                //Xu ly id nha tri
                PostCategory newTag = new PostCategory("0",newTagName);
                tagsList.add(newTag);
                tagsAdapter.notifyDataSetChanged();
            });
            builder.setNegativeButton("Hủy", (dialog, which) -> {
                dialog.dismiss();
            });
            builder.show();
        });

    }
}