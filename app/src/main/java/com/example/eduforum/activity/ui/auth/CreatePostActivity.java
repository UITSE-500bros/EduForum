package com.example.eduforum.activity.ui.auth;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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

        //Stlye rich editor through binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_post);
        binding.contentRichEditor.setEditorHeight(217);
        binding.contentRichEditor.setEditorFontSize(14);
        binding.contentRichEditor.setEditorFontSize(R.color.black);
        binding.contentRichEditor.setEditorBackgroundColor(R.color.white);
        binding.contentRichEditor.setPlaceholder("Nhập nội dung bài viết");

        //set up action buttons
        binding.actionRedo.setOnClickListener(v -> binding.contentRichEditor.redo());
        binding.actionUndo.setOnClickListener(v -> binding.contentRichEditor.undo());
        binding.actionBold.setOnClickListener(v -> binding.contentRichEditor.setBold());
        binding.actionItalic.setOnClickListener(v -> binding.contentRichEditor.setItalic());
        binding.actionUnderline.setOnClickListener(v -> binding.contentRichEditor.setUnderline());
        binding.actionAlignCenter.setOnClickListener(v -> binding.contentRichEditor.setAlignCenter());
        binding.actionAlignLeft.setOnClickListener(v -> binding.contentRichEditor.setAlignLeft());
        binding.actionAlignRight.setOnClickListener(v -> binding.contentRichEditor.setAlignRight());
        binding.actionHeading1.setOnClickListener(v -> binding.contentRichEditor.setHeading(1));
        binding.actionHeading2.setOnClickListener(v -> binding.contentRichEditor.setHeading(2));
        binding.actionHeading3.setOnClickListener(v -> binding.contentRichEditor.setHeading(3));
        binding.actionInsertBullets.setOnClickListener(v -> binding.contentRichEditor.setBullets());
        binding.actionInsertNumbers.setOnClickListener(v -> binding.contentRichEditor.setNumbers());
        binding.actionInsertLink.setOnClickListener(v -> {
            AlertDialog builder = new AlertDialog.Builder(this).create();
            builder.setTitle("Chèn link vào bài viết");

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);

            final EditText textInput = new EditText(this);
            textInput.setHint("Text");
            layout.addView(textInput);
            final EditText urlInput = new EditText(this);
            urlInput.setHint("URL");
            layout.addView(urlInput);

            builder.setView(layout);

            builder.setButton(DialogInterface.BUTTON_POSITIVE, "OK", (dialog, which) -> {
                String text = textInput.getText().toString();
                String url = urlInput.getText().toString();
                binding.contentRichEditor.insertLink(url, text);
            });

            builder.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });
    }
}