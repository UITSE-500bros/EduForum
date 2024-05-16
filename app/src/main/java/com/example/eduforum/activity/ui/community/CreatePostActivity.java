package com.example.eduforum.activity.ui.community;

import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.R;
import com.example.eduforum.activity.EduForum;
import com.example.eduforum.activity.model.post_manage.Creator;
import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.activity.ui.community.adapter.MediaAdapter;
import com.example.eduforum.activity.ui.community.adapter.MediaItem;
import com.example.eduforum.activity.ui.community.viewstate.PostViewState;
import com.example.eduforum.activity.viewmodel.community.CreatePostViewModel;
import com.example.eduforum.activity.viewmodel.shared.UserViewModel;
import com.example.eduforum.databinding.ActivityCreatePostBinding;
import com.google.android.material.snackbar.Snackbar;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.richeditor.RichEditor;

public class CreatePostActivity extends AppCompatActivity {

    private ActivityCreatePostBinding binding;
    private CreatePostViewModel viewModel;
    private UserViewModel userViewModel;

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
        viewModel = new ViewModelProvider(this).get(CreatePostViewModel.class);
        EduForum app = (EduForum) getApplication();
        userViewModel = app.getSharedViewModel(UserViewModel.class);
        userViewModel.getCurrentUserLiveData().observe(this, user -> {
            if(user != null){
                PostViewState postViewState = viewModel.getPostViewState().getValue();
                Creator creator = mapToCreator(user);
                assert postViewState != null;
                postViewState.setCreator(creator);
                viewModel.setPostViewState(postViewState);
            }
        });

        String communityId =  getIntent().getStringExtra("communityId");
        if(communityId != null){
            viewModel.setCommunityId(communityId);
        }
        else{
            //finish();
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_post);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        initComponents();
        viewModel.getIsPostCreated().observe(this, isCreated -> {
            if(isCreated){
                finish();
            }
        });
        viewModel.getErrorMessage().observe(this, errorMessage -> {
            if(errorMessage != null){
                Snackbar.make(binding.getRoot(), errorMessage, Snackbar.LENGTH_SHORT).show();
            }
        });
        //image recycler view
        RecyclerView ImageRecyclerView = binding.imageRecyclerView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        ImageRecyclerView.setLayoutManager(layoutManager);
    }
    private void initComponents(){
        //Stlye rich editor through binding
        binding.contentRichEditor.setBackgroundColor(Color.WHITE);
        binding.contentRichEditor.setEditorHeight(250);
        binding.contentRichEditor.setEditorFontSize(14);
        binding.contentRichEditor.setEditorFontColor(Color.BLACK);
        binding.contentRichEditor.setEditorBackgroundColor(Color.WHITE);
        binding.contentRichEditor.setPadding(10, 10, 10, 10);

        //set up action buttons
        binding.textDefineRichEditor.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == binding.actionUndo.getId()) {
                    binding.contentRichEditor.undo();
                } else if (checkedId == binding.actionRedo.getId()) {
                    binding.contentRichEditor.redo();
                } else if (checkedId == binding.actionBold.getId()) {
                    binding.contentRichEditor.setBold();
                } else if (checkedId == binding.actionUnderline.getId()){
                    binding.contentRichEditor.setUnderline();
                } else if (checkedId == binding.actionItalic.getId()){
                    binding.contentRichEditor.setItalic();
                } else if (checkedId == binding.actionAlignLeft.getId()){
                    binding.contentRichEditor.setAlignLeft();
                } else if (checkedId == binding.actionAlignRight.getId()){
                    binding.contentRichEditor.setAlignRight();
                } else if (checkedId == binding.actionAlignCenter.getId()){
                    binding.contentRichEditor.setAlignCenter();
                } else if (checkedId == binding.actionHeading1.getId()){
                    binding.contentRichEditor.setHeading(1);
                } else if (checkedId == binding.actionHeading2.getId()){
                    binding.contentRichEditor.setHeading(2);
                } else if (checkedId == binding.actionHeading3.getId()){
                    binding.contentRichEditor.setHeading(3);
                } else if (checkedId == binding.actionInsertBullets.getId()){
                    binding.contentRichEditor.setBullets();
                } else if (checkedId == binding.actionInsertNumbers.getId()){
                    binding.contentRichEditor.setNumbers();
                } else if (checkedId == binding.actionInsertLink.getId()){
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
                }
            }
        });

        //set up image
        ActivityResultLauncher<PickVisualMediaRequest> pickVideos =
                //parameter in PickVisualMediaRequest is the max item user can select
                registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(), uri -> {

                    if (uri != null) {
                        ArrayList<MediaItem> uriList = new ArrayList<>();
                        for(int i = 0; i < uri.size(); i++) {
                            VideoView videoView = new VideoView(this);
                            videoView.setVideoURI(uri.get(i));
                            int sizeInDp = 40;
                            float scale = getResources().getDisplayMetrics().density;
                            int sizeInPx = (int) (sizeInDp * scale + 0.5f);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(sizeInPx, sizeInPx);
                            videoView.setLayoutParams(layoutParams);
                            uriList.add(new MediaItem(uri.get(i), true));
                        }
                        MediaAdapter imageAdapter = new MediaAdapter(uriList);
                        binding.imageRecyclerView.setAdapter(imageAdapter);
                    } else {
//                        TODO: Show errors
                    }
                });
        binding.videoInsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickVideos.launch(new PickVisualMediaRequest.Builder().build());
            }
        });



//        ActivityResultLauncher<PickVisualMediaRequest> pickVideos =
//                //parameter in PickVisualMediaRequest is the max item user can select
//                registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(), uri -> {
//
//                    if (uri != null) {
//                        ArrayList<MediaItem> uriList = new ArrayList<>();
//                        for(int i = 0; i < uri.size(); i++) {
//                            VideoView videoView = new VideoView(this);
//                            videoView.setVideoURI(uri.get(i));
//                            int sizeInDp = 40;
//                            float scale = getResources().getDisplayMetrics().density;
//                            int sizeInPx = (int) (sizeInDp * scale + 0.5f);
//                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(sizeInPx, sizeInPx);
//                            videoView.setLayoutParams(layoutParams);
//                            uriList.add(new MediaItem(uri.get(i), true));
//                        }
//                        MediaAdapter imageAdapter = new MediaAdapter(uriList);
//                       binding.imageRecyclerView.setAdapter(imageAdapter);
//                    } else {
////                        TODO: Show errors
//                    }
//                });
//        binding.videoInsertButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pickVideos.launch(new PickVisualMediaRequest.Builder().build());
//            }
//        });


        //Handle tag items
        // List<Category> categories = viewModel.getCategories();
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
                // List<Category> selectedItems = new ArrayList<>();
                for (int i = 0; i < checkedItems.length; i++) {
                    if (checkedItems[i]) {
                        selectedItems.append(items[i]).append(", ");
                        // selectedItems.add(new Category(...));
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

        binding.createPostButton.setOnClickListener(v -> {
            PostViewState newPost = viewModel.getPostViewState().getValue();
            newPost.setContent(binding.contentRichEditor.getHtml());
            // newPost.setCategory(...);
            viewModel.setPostViewState(newPost);
            viewModel.createPost();
        });
    }
    private Creator mapToCreator(User user) {
        return new Creator(user.getUserId(), user.getName(), user.getDepartment(),  user.getProfilePicture());
    }
}

