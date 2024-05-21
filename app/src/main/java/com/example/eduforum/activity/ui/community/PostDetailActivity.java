package com.example.eduforum.activity.ui.community;

import android.os.Bundle;
import android.view.View;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.eduforum.R;
import com.example.eduforum.activity.model.post_manage.Creator;
import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.activity.ui.community.adapter.CommentAdapter;
import com.example.eduforum.activity.ui.community.adapter.CommentChildAdapter;
import com.example.eduforum.activity.ui.community.adapter.MediaAdapter;
import com.example.eduforum.activity.ui.community.viewstate.CommentViewState;
import com.example.eduforum.activity.ui.community.viewstate.PostViewState;
import com.example.eduforum.activity.viewmodel.community.PostDetailsViewModel;
import com.example.eduforum.databinding.ActivityPostDetailBinding;

import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity {
    private ActivityPostDetailBinding binding;
    private MediaAdapter mediaAdapter;


    private PostDetailsViewModel viewModel;

    private CommentAdapter commentAdapter;

    private CommentChildAdapter commentChildAdapter;

    private boolean isUpVoted = false;
    private boolean isDownVoted = false;
    private boolean isParentComment = true;

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

        binding.moreButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, v);
            popupMenu.getMenuInflater().inflate(R.menu.post_option_menu, popupMenu.getMenu());
            //popupMenu.setOnMenuItemClickListener(item -> {
                //TODO: Handle menu item click
            //});
            popupMenu.show();
        });

        viewModel = new ViewModelProvider(this).get(PostDetailsViewModel.class);



        PostViewState postViewState = (PostViewState) getIntent().getSerializableExtra("currentPost");
        if (postViewState != null) {
            viewModel.setCurrentPost(postViewState);
            binding.titlePost.setText(postViewState.getTitle().toString());
            binding.contentPost.setText(postViewState.getContent().toString());
            binding.voteCountTextView.setText(String.valueOf(postViewState.getVoteDifference()));
            binding.commentCountTextView.setText(String.valueOf(postViewState.getTotalComment()));
//            binding.userNameTextView.setText(postViewState.getCreator().getName());

//            binding.voteCountTextView.setText(String.valueOf(postViewState.getVoteDifference()));
        } else {
            //finish();
        }

        commentAdapter = new CommentAdapter(this,
                viewModel.getComments().getValue(),
                viewModel.getCommentsChild().getValue(),
                new CommentAdapter.OnReplyClickListener() {
                    @Override
                    public void onReplyClick(CommentViewState comment) {
                        binding.commentEditText.setText("@Nam ");
                        binding.commentEditText.setVisibility(View.VISIBLE);
                        binding.sendButton.setVisibility(View.VISIBLE);
                        binding.sendButton.setOnClickListener(v -> {
                            String commentText = binding.commentEditText.getText().toString();
                            if (!commentText.isEmpty()) {

                                CommentViewState commentViewState = new CommentViewState(
                                        null,
                                        commentText,
                                        null,
                                        null,
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
        )

        ;



        binding.recyclecomment.setAdapter(commentAdapter);
        binding.recyclecomment.setLayoutManager(new LinearLayoutManager(this));


        assert postViewState != null;
        viewModel.loadComments(postViewState);

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


        binding.setLifecycleOwner(this);
        binding.sendButton.setOnClickListener(v -> {
            String commentText = binding.commentEditText.getText().toString();
            if (!commentText.isEmpty() && this.isParentComment) {
                CommentViewState commentViewState = new CommentViewState(
                        null,
                        commentText,
                        null,
                        null,
                        0,
                        0,
                        0,
                        null,
                        null,
                        null,
                        0
                );

                viewModel.addParentComment(commentViewState, postViewState.getPostId(), postViewState.getCommunity().getCommunityID());
                binding.commentEditText.setText("");

            }
        });

        binding.downVoteButton.setOnClickListener(v -> {
            if(!isDownVoted){
                viewModel.downVote(postViewState);
                binding.voteCountTextView.setText(String.valueOf(postViewState.getVoteDifference() - 1));
                postViewState.setVoteDifference(postViewState.getVoteDifference() - 1);
                isDownVoted = true;
                isUpVoted = false;
            }
        });

        binding.upVoteButton.setOnClickListener(v -> {
            if(!isUpVoted){
                viewModel.upVote();
                binding.voteCountTextView.setText(String.valueOf(postViewState.getVoteDifference() + 1));
                postViewState.setVoteDifference(postViewState.getVoteDifference() + 1);
                isUpVoted = true;
                isDownVoted = false;
            }
        });

        binding.recycleImage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //mediaAdapter = new MediaAdapter(postViewState.getImage());
        binding.recycleImage.setAdapter(mediaAdapter);


    }

}