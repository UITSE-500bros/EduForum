package com.example.eduforum.activity.ui.community;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
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
//       tagsAdapter = new TagsAdapter( tagsList);
//        recyclerView.setAdapter(tagsAdapter);

    }
}