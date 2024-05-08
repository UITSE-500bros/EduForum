package com.example.eduforum.activity.ui.auth;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
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

        //set up image
        ActivityResultLauncher<PickVisualMediaRequest> pickImages =
        //parameter in PickVisualMediaRequest is the max item user can select
                registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(), uri -> {
                    if (uri != null) {
                        for(int i = 0; i < uri.size(); i++) {
                            ImageView imageView = new ImageView(this);
                            imageView.setImageURI(uri.get(i));
                            imageView.setMaxHeight(48);
                            imageView.setMaxWidth(48);
                            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            binding.resourceList.addView(imageView);
                        }
                    } else {
//                        TODO: Show errors
                    }
                });

        binding.imageInsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImages.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });

        ActivityResultLauncher<PickVisualMediaRequest> pickVideos =
                //parameter in PickVisualMediaRequest is the max item user can select
                registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(), uri -> {
                    if (uri != null) {
                        for(int i = 0; i < uri.size(); i++) {
                            VideoView videoView = new VideoView(binding.getRoot().getContext());
                            videoView.setVideoURI(uri.get(i));
                            binding.resourceList.addView(videoView);
                        }
                    } else {
//                        TODO: Show errors
                    }
                });
        binding.imageInsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickVideos.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.VideoOnly.INSTANCE)
                        .build());
            }
        });


        //Handle tag items
        //TODO: add category items
        final String[] items = new String[]{"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};

        final boolean[] checkedItems = new boolean[items.length];

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn các mục");
        builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // Event when user check or uncheck an item
                checkedItems[which] = isChecked;
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 5. Khi người dùng nhấp vào "OK", cập nhật text của TextView để hiển thị các mục đã chọn
                StringBuilder selectedItems = new StringBuilder();
                for (int i = 0; i < checkedItems.length; i++) {
                    if (checkedItems[i]) {
                        selectedItems.append(items[i]).append(", ");
                    }
                }
                // Xóa dấu phẩy cuối cùng
                if (selectedItems.length() > 0) {
                    selectedItems.setLength(selectedItems.length() - 2);
                }
                binding.categoryTextView.setText(selectedItems.toString());
            }
        });
        builder.setNegativeButton("Cancel", null);

// Hiển thị AlertDialog khi nhấp vào TextView
        binding.categoryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.show();
            }
        });
    }
}