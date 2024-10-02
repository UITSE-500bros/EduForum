package com.example.eduforum.activity.ui.community;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.R;
import com.example.eduforum.activity.EduForum;
import com.example.eduforum.activity.model.post_manage.Category;
import com.example.eduforum.activity.model.post_manage.Creator;
import com.example.eduforum.activity.model.post_manage.PostCategory;
import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.activity.ui.community.adapter.MediaAdapter;
import com.example.eduforum.activity.ui.community.viewstate.PostViewState;
import com.example.eduforum.activity.viewmodel.community.CreatePostViewModel;
import com.example.eduforum.activity.viewmodel.shared.UserViewModel;
import com.example.eduforum.databinding.ActivityCreatePostBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CreatePostActivity extends AppCompatActivity {

    private ActivityCreatePostBinding binding;
    private CreatePostViewModel viewModel;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_post);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        String communityId =  getIntent().getStringExtra("communityId");
        if(communityId != null){
            viewModel.setCommunityId(communityId);
            viewModel.updateCategories();
        }
        else{
            //finish();
        }

        initComponents();
        viewModel.getIsPostCreated().observe(this, isCreated -> {
            if(isCreated){
                setResult(Activity.RESULT_OK);
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

        binding.toolBarCreatePost.setNavigationOnClickListener(v -> {
            finish();
        });

        //Stlye rich editor through binding
        binding.contentRichEditor.setBackgroundColor(Color.WHITE);
        binding.contentRichEditor.setEditorHeight(250);
        binding.contentRichEditor.setEditorFontSize(14);
        binding.contentRichEditor.setEditorFontColor(Color.BLACK);
        binding.contentRichEditor.setEditorBackgroundColor(Color.WHITE);
        binding.contentRichEditor.setPadding(10, 10, 10, 10);

        binding.actionUndo.addOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.contentRichEditor.undo();
            }
        });
        binding.actionRedo.addOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.contentRichEditor.redo();
            }
        });
        binding.actionBold.addOnCheckedChangeListener((buttonView, isChecked) -> {
                binding.contentRichEditor.setBold();
        });
        binding.actionUnderline.addOnCheckedChangeListener((buttonView, isChecked) -> {
                binding.contentRichEditor.setUnderline();
        });
        binding.actionItalic.addOnCheckedChangeListener((buttonView, isChecked) -> {
                binding.contentRichEditor.setItalic();
        });
        binding.actionAlignLeft.addOnCheckedChangeListener((buttonView, isChecked) -> {
            if(!binding.actionAlignCenter.isChecked() && !binding.actionAlignRight.isChecked()){
                binding.contentRichEditor.setAlignLeft();
            }
            else{
                binding.actionAlignLeft.setChecked(false);
            }
        });
        binding.actionAlignRight.addOnCheckedChangeListener((buttonView, isChecked) -> {
            if(!binding.actionAlignCenter.isChecked() && !binding.actionAlignLeft.isChecked()){
                binding.contentRichEditor.setAlignRight();
            }
            else{
                binding.actionAlignRight.setChecked(false);
            }
        });
        binding.actionAlignCenter.addOnCheckedChangeListener((buttonView, isChecked) -> {
            if(!binding.actionAlignLeft.isChecked() && !binding.actionAlignRight.isChecked()){
                binding.contentRichEditor.setAlignCenter();
            }
            else{
                binding.actionAlignCenter.setChecked(false);
            }
        });
        binding.actionHeading1.addOnCheckedChangeListener((buttonView, isChecked) -> {
                if(!binding.actionHeading2.isChecked() && !binding.actionHeading3.isChecked()){
                    binding.contentRichEditor.setHeading(1);
                }
                else{
                    binding.actionHeading1.setChecked(false);
                }
        });
        binding.actionHeading2.addOnCheckedChangeListener((buttonView, isChecked) -> {
            if(!binding.actionHeading1.isChecked() && !binding.actionHeading3.isChecked()){
                binding.contentRichEditor.setHeading(2);
            }
            else{
                binding.actionHeading2.setChecked(false);
            }
        });
        binding.actionHeading3.addOnCheckedChangeListener((buttonView, isChecked) -> {
            if(!binding.actionHeading2.isChecked() && !binding.actionHeading1.isChecked()){
                binding.contentRichEditor.setHeading(3);
            }
            else{
                binding.actionHeading3.setChecked(false);
            }
        });
        binding.actionInsertBullets.addOnCheckedChangeListener((buttonView, isChecked) -> {
                binding.contentRichEditor.setBullets();
        });
        binding.actionInsertNumbers.addOnCheckedChangeListener((buttonView, isChecked) -> {
                binding.contentRichEditor.setNumbers();
        });
        binding.actionInsertLink.addOnCheckedChangeListener((buttonView, isChecked) -> {
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
        ActivityResultLauncher<PickVisualMediaRequest> pickImages =
                registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(), uris -> {
                    if (uris != null) {
                        List<Uri> uriList1 = new ArrayList<>();
                        // Get the current list of images
                        PostViewState postViewState = viewModel.getPostViewState().getValue();
                        if (postViewState != null && postViewState.getImage() != null) {
                            uriList1.addAll(postViewState.getImage());
                        }
                        for(int i = 0; i < uris.size(); i++) {
                            ImageView imageView = new ImageView(this);
                            imageView.setImageURI(uris.get(i));
                            int sizeInDp = 40;
                            float scale = getResources().getDisplayMetrics().density;
                            int sizeInPx = (int) (sizeInDp * scale + 0.5f);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(sizeInPx, sizeInPx);
                            imageView.setLayoutParams(layoutParams);
                            uriList1.add(uris.get(i));
                        }
                        MediaAdapter imageAdapter = new MediaAdapter(uriList1, false);
                        binding.imageRecyclerView.setAdapter(imageAdapter);
                        postViewState.setImage(uriList1);
                        viewModel.setPostViewState(postViewState);
                    } else {
                        // TODO: Show errors
                    }
                });

        binding.imageInsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImages.launch(new PickVisualMediaRequest.Builder().build());
            }
        });

        AtomicReference<Boolean> isAnonymous = new AtomicReference<>(false);
        binding.incognitoModeButton.setOnClickListener(v -> {
            if(binding.incognitoModeButton.getIconTint() == ContextCompat.getColorStateList(binding.getRoot().getContext(), R.color.unLikedButtonColor)){
                ColorStateList colorStateList = ContextCompat.getColorStateList(binding.getRoot().getContext(), R.color.likedButtonColor);
                binding.incognitoModeButton.setIconTint(colorStateList);
                isAnonymous.set(true);
            }
            else {
                ColorStateList colorStateList = ContextCompat.getColorStateList(binding.getRoot().getContext(), R.color.unLikedButtonColor);
                binding.incognitoModeButton.setIconTint(colorStateList);
                isAnonymous.set(false);
            }
        });

        //Handle tag items
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn các mục");

        viewModel.getAllCategories().observe(this, newCategories -> {
               List<Category> categories = newCategories;
            String[] items = new String[categories.size()];
            for(int i = 0; i < categories.size(); i++){
                items[i] = categories.get(i).getTitle();
            }
            final boolean[] checkedItems = new boolean[items.length];

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
                    List<PostCategory> selectedCategories = new ArrayList<>();
                    for (int i = 0; i < checkedItems.length; i++) {
                        if (checkedItems[i]) {
                            selectedItems.append(items[i]).append(", ");
                            PostCategory newPostCategory = new PostCategory(categories.get(i));
                            selectedCategories.add(newPostCategory);
                        }
                    }
                    // Xóa dấu phẩy cuối cùng
                    if (selectedItems.length() > 0) {
                        selectedItems.setLength(selectedItems.length() - 2);
                    }
                    binding.categoryTextView.setText(selectedItems.toString());
                    PostViewState postViewState = viewModel.getPostViewState().getValue();
                    assert postViewState != null;
                    postViewState.setTags(selectedCategories);
                    viewModel.setPostViewState(postViewState);
                }
            });
            builder.setNegativeButton("Cancel", null);
        });


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
            newPost.setAnonymous(isAnonymous.get());
            //newPost.setDate(LocalDate.now().toString());
            // newPost.setCategory(...);
            viewModel.setPostViewState(newPost);
            viewModel.createPost(viewModel.getPostViewState().getValue());
        });
    }
    private Creator mapToCreator(User user) {
        return new Creator(user.getUserId(), user.getName(), user.getDepartment(),  user.getProfilePicture());
    }
}

