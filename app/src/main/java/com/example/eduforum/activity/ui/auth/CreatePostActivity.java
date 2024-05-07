package com.example.eduforum.activity.ui.auth;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.example.eduforum.R;
import com.example.eduforum.databinding.ActivityCreatePostBinding;

import jp.wasabeef.richeditor.RichEditor;

public class CreatePostActivity extends AppCompatActivity {

    private ActivityCreatePostBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_post);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_post);
        binding.contentRichEditor.setEditorHeight(217);
        binding.contentRichEditor.setEditorFontSize(14);
        binding.contentRichEditor.setEditorFontSize(R.color.black);
        binding.contentRichEditor.setEditorBackgroundColor(R.color.white);

        binding.contentRichEditor.setPlaceholder("Nhập nội dung bài viết");
    }
}