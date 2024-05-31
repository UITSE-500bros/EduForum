package com.example.eduforum.activity.ui.community;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eduforum.R;
import com.example.eduforum.activity.EduForum;
import com.example.eduforum.activity.model.post_manage.Creator;
import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.activity.ui.community.adapter.CommentAdapter;
import com.example.eduforum.activity.ui.community.adapter.CommentChildAdapter;
import com.example.eduforum.activity.ui.community.adapter.MediaAdapter;
import com.example.eduforum.activity.ui.community.viewstate.CommentViewState;
import com.example.eduforum.activity.ui.community.viewstate.PostViewState;
import com.example.eduforum.activity.viewmodel.community.PostDetailsViewModel;
import com.example.eduforum.activity.viewmodel.shared.UserViewModel;
import com.example.eduforum.databinding.ActivityPostDetailBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity {
    private ActivityPostDetailBinding binding;
    private MediaAdapter mediaAdapter;


    private PostDetailsViewModel viewModel;

    private CommentAdapter commentAdapter;
    private boolean isUpVoted = false;
    private boolean isDownVoted = false;
    private boolean isParentComment = true;
    private MaterialAlertDialogBuilder builder;

    private UserViewModel userViewModel;
    private Creator creator;
    public static final String KEY_CURRENT_POST = "currentPost";
    public static final String KEY_NOTI_POST = "noti";
    public static final String KEY_COMMUNITY_ID = "notiCommunityID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_detail);



        viewModel = new ViewModelProvider(this).get(PostDetailsViewModel.class);

        //set up turn back button in ActionBar
        binding.toolBarCreatePost.setNavigationOnClickListener(v -> {
            finish();
        });

        EduForum app = (EduForum) getApplication();
        userViewModel = app.getSharedViewModel(UserViewModel.class);
        userViewModel.getCurrentUserLiveData().observe(this, user -> {
            if(user != null){
                creator = mapToCreator(user);
            }
        });

        createDeleteDialog();

        String communityName;
        String key = (String) getIntent().getSerializableExtra("key");
        if (key != null) {
            switch (key) {
                case KEY_CURRENT_POST:


                    Boolean isExploring = (Boolean) getIntent().getBooleanExtra("isExploring", false);
                    if (isExploring) {
                        binding.commentEditText.setVisibility(View.GONE);
                        binding.sendButton.setVisibility(View.GONE);
                    }

                    PostViewState currentPost = (PostViewState) getIntent().getSerializableExtra(KEY_CURRENT_POST);
                    if (currentPost != null) {
                        viewModel.setCurrentPost(currentPost);
                    }
                    Boolean isUITcommunity = (Boolean) getIntent().getBooleanExtra("isUITcommunity", false);
                    if (isUITcommunity) {
                        binding.userNameTextView.setText("Quản trị viên");
                        binding.khoaTextView.setText("");
                    }else {
                        binding.userNameTextView.setText(currentPost.getCreator().getName());
                        binding.khoaTextView.setText(currentPost.getCreator().getDepartment());
                    }

                    binding.toolBarCreatePost.setTitle(currentPost.getCommunity().getName());
                    binding.titlePost.setText(currentPost.getTitle());
                    binding.contentPost.setText(currentPost.getContent());
                    binding.commentCountTextView.setText(String.valueOf(currentPost.getTotalComment()));
                    binding.voteCountTextView.setText(String.valueOf(currentPost.getVoteDifference()));
                    binding.timeCommentTextView.setText(currentPost.getDate());

                    if(currentPost.getPictures()!=null ){
                        RecyclerView recyclerView = binding.recycleImage;
                        mediaAdapter = new MediaAdapter(currentPost.getPictures());
                        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView.setAdapter(mediaAdapter);
                    }


                    if(currentPost.getCreator()!= null ) {
                        if (currentPost.getCreator().getProfilePicture() != null) {
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(currentPost.getCreator().getProfilePicture());
                            Glide.with(binding.getRoot().getContext())
                                    .load(storageReference)
                                    .into(binding.avatarImageView);
                        }
                    }

                    binding.downVoteButton.setOnClickListener(v -> {
                        if(!isDownVoted){
                            viewModel.downVote(currentPost);
                            ColorStateList colorStateList = ContextCompat.getColorStateList(binding.getRoot().getContext(), R.color.likedButtonColor);
                            binding.downVoteButton.setIconTint(colorStateList);
                            binding.voteCountTextView.setText(String.valueOf(currentPost.getVoteDifference() - 1));
                            currentPost.setVoteDifference(currentPost.getVoteDifference() - 1);
                            isDownVoted = true;
                            isUpVoted = false;
                        }else {
                            binding.downVoteButton.setIconTint(null);
                        }
                    });

                    binding.upVoteButton.setOnClickListener(v -> {
                        if(!isUpVoted){
                            viewModel.upVote();
                            ColorStateList colorStateList = ContextCompat.getColorStateList(binding.getRoot().getContext(), R.color.likedButtonColor);
                            binding.upVoteButton.setIconTint(colorStateList);
                            binding.voteCountTextView.setText(String.valueOf(currentPost.getVoteDifference()+ 1));
                            currentPost.setVoteDifference(currentPost.getVoteDifference() + 1);
                            isUpVoted = true;
                            isDownVoted = false;
                        }else{
                            binding.upVoteButton.setIconTint(null);
                        }
                    });

                    commentAdapter = new CommentAdapter(this,
                            viewModel.getComments().getValue(),
                            viewModel.getCommentsChild().getValue(),
                            new CommentAdapter.OnReplyClickListener() {
                                @Override
                                public void onReplyClick(CommentViewState comment) {
                                    // Yêu cầu focus trên EditText
                                    binding.commentEditText.requestFocus();
                                    // Hiển thị bàn phím
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.showSoftInput(binding.commentEditText, InputMethodManager.SHOW_IMPLICIT);

                                    binding.commentEditText.setText("@" + comment.getCreator().getName() + " ");

                                    binding.commentEditText.setVisibility(View.VISIBLE);
                                    binding.sendButton.setVisibility(View.VISIBLE);

                                    binding.sendButton.setOnClickListener(v -> {
                                        String commentText = binding.commentEditText.getText().toString();
                                        if (!commentText.isEmpty()) {

                                            CommentViewState commentViewState = new CommentViewState(
                                                    null,
                                                    commentText,
                                                    null,
                                                    creator,
                                                    0,
                                                    0,
                                                    0,
                                                    null,
                                                    null,
                                                    comment.getCommentID(),
                                                    0
                                            );

                                            viewModel.addChildComment(comment, commentViewState);
                                            binding.commentEditText.setText("");
                                        }
                                    });
                                }

                            },
                            new CommentAdapter.OnDownVoteClickListener() {
                                @Override
                                public void onDownClick(CommentViewState comment) {
                                    viewModel.downVote(comment);
                                }
                            },
                            new CommentAdapter.OnUpVoteClickListener() {
                                @Override
                                public void onUpVote(CommentViewState comment) {
                                    viewModel.upVote(comment);
                                }
                            },
                            new CommentAdapter.OnShowUpReplies() {
                                @Override
                                public void onShowUpReplies(CommentViewState comment) {
                                    viewModel.loadChildComments(comment);
                                }
                            }
                    );

                    viewModel.getComments().observe(this, commentViewStates -> {
                        List<CommentViewState> commentChildList = new ArrayList<>();
                        List<CommentViewState> itemsToRemove = new ArrayList<>();

                        for (CommentViewState commentViewState : commentViewStates) {
                            if (commentViewState.getReplyCommentID() != null) {
                                commentChildList.add(commentViewState);
                                itemsToRemove.add(commentViewState);
                            }

                        }
                        commentViewStates.removeAll(itemsToRemove);
                        commentAdapter.setCommentList(commentViewStates);
                        commentAdapter.setChildCommentList(commentChildList);
                    });

                    binding.recyclecomment.setAdapter(commentAdapter);
                    binding.recyclecomment.setLayoutManager(new LinearLayoutManager(this));




                    binding.setLifecycleOwner(this);
                    binding.sendButton.setOnClickListener(v -> {
                        String commentText = binding.commentEditText.getText().toString();
                        if (!commentText.isEmpty() && this.isParentComment) {
                            CommentViewState commentViewState = new CommentViewState(
                                    null,
                                    commentText,
                                    null,
                                    creator,
                                    0,
                                    0,
                                    0,
                                    null,
                                    null,
                                    null,
                                    0
                            );
                            viewModel.addParentComment(commentViewState);
                            binding.commentEditText.setText("");
                        }
                    });

                    binding.commentButton.setOnClickListener(v -> {
                        // Yêu cầu focus trên EditText
                        binding.commentEditText.requestFocus();

                        // Hiển thị bàn phím
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(binding.commentEditText, InputMethodManager.SHOW_IMPLICIT);

                        // Hiển thị nút sendButton
                        binding.sendButton.setVisibility(View.VISIBLE);

                        binding.commentEditText.setText("");

                        // Đánh dấu là bình luận gốc
                        this.isParentComment = true;
                    });

                    binding.moreButton.setOnClickListener(v -> {
                        PopupMenu popupMenu = new PopupMenu(this, v);
                        popupMenu.getMenuInflater().inflate(R.menu.post_option_menu, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(item -> {

                            if(item.getItemId() == R.id.deletePost){
                                //builder.show();
                                viewModel.deletePost(currentPost);
                                finish();
                            }

//                }
                            return true;
                        });
                        popupMenu.show();
                    });
                    break;
                case KEY_NOTI_POST:
                    communityName = (String) getIntent().getSerializableExtra("notiCommunityName");
                    String postID = (String) getIntent().getSerializableExtra("notiPost");
                    String communityID = (String) getIntent().getSerializableExtra(KEY_COMMUNITY_ID);
                    if (postID != null && communityID != null) {
                        viewModel.loadPost(postID, communityID);
                        viewModel.loadComments(postID, communityID);

                    }
                    binding.toolBarCreatePost.setTitle(communityName);
                    break;
            }
        }

        viewModel.getPost().observe(this, currentPost -> {
            if (currentPost != null) {

                binding.titlePost.setText(currentPost.getTitle());
                binding.contentPost.setText(currentPost.getContent());
                binding.commentCountTextView.setText(String.valueOf(currentPost.getTotalComment()));
                binding.voteCountTextView.setText(String.valueOf(currentPost.getVoteDifference()));
                if(currentPost.getAnonymous()){
                    binding.userNameTextView.setText("Ẩn danh");
                    binding.khoaTextView.setText("");
                }else{
                    binding.userNameTextView.setText(currentPost.getCreator().getName());
                    binding.khoaTextView.setText(currentPost.getCreator().getDepartment());
                }

                if(currentPost.getCreator()!= null ) {
                    if (currentPost.getCreator().getProfilePicture() != null) {
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(currentPost.getCreator().getProfilePicture());
                        Glide.with(binding.getRoot().getContext())
                                .load(storageReference)
                                .into(binding.avatarImageView);
                    }
                }

                binding.timeCommentTextView.setText(currentPost.getDate());
                binding.khoaTextView.setText(currentPost.getCreator().getDepartment());


                binding.downVoteButton.setOnClickListener(v -> {
                    if(!isDownVoted){
                        viewModel.downVote(currentPost);
                        ColorStateList colorStateList = ContextCompat.getColorStateList(binding.getRoot().getContext(), R.color.likedButtonColor);
                        binding.downVoteButton.setIconTint(colorStateList);
                        binding.voteCountTextView.setText(String.valueOf(currentPost.getVoteDifference() - 1));
                        currentPost.setVoteDifference(currentPost.getVoteDifference() - 1);
                        isDownVoted = true;
                        isUpVoted = false;
                    }else{
                        binding.downVoteButton.setIconTint(null);
                    }
                });

                binding.upVoteButton.setOnClickListener(v -> {
                    if(!isUpVoted){
                        viewModel.upVote();
                        ColorStateList colorStateList = ContextCompat.getColorStateList(binding.getRoot().getContext(), R.color.likedButtonColor);
                        binding.upVoteButton.setIconTint(colorStateList);
                        binding.voteCountTextView.setText(String.valueOf(currentPost.getVoteDifference()+ 1));
                        currentPost.setVoteDifference(currentPost.getVoteDifference() + 1);
                        isUpVoted = true;
                        isDownVoted = false;
                    }else{
                        binding.upVoteButton.setIconTint(null);
                    }
                });





                viewModel.getComments().observe(this, commentViewStates -> {
                    List<CommentViewState> commentChildList = new ArrayList<>();
                    List<CommentViewState> itemsToRemove = new ArrayList<>();

                    for (CommentViewState commentViewState : commentViewStates) {
                        if (commentViewState.getReplyCommentID() != null) {
                            commentChildList.add(commentViewState);
                            itemsToRemove.add(commentViewState);
                        }

                    }
                    commentViewStates.removeAll(itemsToRemove);


                    if (commentAdapter != null) {
                        commentAdapter.setCommentList(commentViewStates);
                    }

                });


                binding.setLifecycleOwner(this);
                binding.sendButton.setOnClickListener(v -> {
                    String commentText = binding.commentEditText.getText().toString();
                    if (!commentText.isEmpty() && this.isParentComment) {
                        CommentViewState commentViewState = new CommentViewState(
                                null,
                                commentText,
                                null,
                                creator,
                                0,
                                0,
                                0,
                                null,
                                null,
                                null,
                                0
                        );
                        viewModel.addParentComment(commentViewState);
                        binding.commentEditText.setText("");
                    }
                });
                binding.moreButton.setOnClickListener(v -> {
                    PopupMenu popupMenu = new PopupMenu(this, v);
                    popupMenu.getMenuInflater().inflate(R.menu.post_option_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(item -> {

                        if(item.getItemId() == R.id.deletePost){
                            builder.show();
                            viewModel.deletePost(currentPost);
                            finish();
                        }

//                }
                        return true;
                    });
                    popupMenu.show();
                });

            } else {
                Log.d("PostDetailActivity", "postViewState is null");
            }
        });



        binding.commentButton.setOnClickListener(v -> {
            // Yêu cầu focus trên EditText
            binding.commentEditText.requestFocus();

            // Hiển thị bàn phím
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(binding.commentEditText, InputMethodManager.SHOW_IMPLICIT);

            // Hiển thị nút sendButton
            binding.sendButton.setVisibility(View.VISIBLE);

            binding.commentEditText.setText("");

            // Đánh dấu là bình luận gốc
            this.isParentComment = true;
        });





    }

    public void createDeleteDialog(){
        builder = new MaterialAlertDialogBuilder(binding.getRoot().getContext());
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc muốn xóa bài viết này chứ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO: Delete Comment
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    private Creator mapToCreator(User user) {
        return new Creator(user.getUserId(), user.getName(), user.getDepartment(),  user.getProfilePicture());
    }
}