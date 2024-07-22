package com.example.eduforum.activity.ui.community.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eduforum.R;
import com.example.eduforum.activity.repository.comment.CommentRepository;
import com.example.eduforum.activity.ui.community.PostDetailActivity;
import com.example.eduforum.activity.ui.community.viewstate.CommentViewState;
import com.example.eduforum.activity.ui.main.adapter.ChildCommentAdapter;
import com.example.eduforum.activity.viewmodel.community.PostDetailsViewModel;
import com.example.eduforum.databinding.ItemChildCommentBinding;
import com.example.eduforum.databinding.ItemListCommentBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommentChildAdapter extends RecyclerView.Adapter<CommentChildAdapter.CommentViewHolder>{
    private Context context;
    private static List<CommentViewState> commentList2;
    private MaterialAlertDialogBuilder builder;
    private CommentRepository commentRepository;
    private String userID;
    private LifecycleOwner lifecycleOwner;
    private String postId;
    private String communityId;
    private CommentAdapter.OnReplyClickListener onReplyClickListener;
    private CommentAdapter.OnDownVoteClickListener onDownVoteClickListener;
    private CommentAdapter.OnUpVoteClickListener onUpVoteClickListener;

    public CommentChildAdapter(Context context, LifecycleOwner lifecycleOwner, String userId,String postId,String communityId,
                               List<CommentViewState> commentList,
                               CommentAdapter.OnReplyClickListener onReplyClickListener,
                               CommentAdapter.OnDownVoteClickListener onDownVoteClickListener,
                               CommentAdapter.OnUpVoteClickListener onUpVoteClickListener) {

        this.context = context;
        commentList2 = commentList;
        this.onReplyClickListener = onReplyClickListener;
        this.onDownVoteClickListener = onDownVoteClickListener;
        this.onUpVoteClickListener = onUpVoteClickListener;
        this.userID = userId;
        this.lifecycleOwner = lifecycleOwner;
        this.postId = postId;
        this.communityId = communityId;
    }
    public void setCommentList(List<CommentViewState> commentList) {
        if (commentList != null) {
            this.commentList2 = commentList;
            notifyDataSetChanged();
        } else {
            this.commentList2 = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public CommentChildAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemChildCommentBinding itemChildCommentBinding = ItemChildCommentBinding.inflate(layoutInflater, parent, false);
        return new CommentViewHolder(itemChildCommentBinding,lifecycleOwner,userID,postId,communityId);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentViewState comment = commentList2.get(position);

//        holder.binding.moreChildCommentButton.setOnClickListener(v -> {
//            PopupMenu popupMenu = new PopupMenu(context, holder.binding.moreChildCommentButton);
//            popupMenu.inflate(R.menu.comment_menu);
//            popupMenu.setOnMenuItemClickListener(item -> {
//                if (item.getItemId() == R.id.deleteComment) {
//                    builder.show();
//                }
//                else{
//
//                }
//                return true;
//            });
//            popupMenu.show();
//        });


        holder.bind(comment, onReplyClickListener, onDownVoteClickListener, onUpVoteClickListener);

    }

    @Override
    public int getItemCount() {
        return commentList2.size();
    }


    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        ItemChildCommentBinding binding;
        private LifecycleOwner lifecycleOwner;
        private PostDetailsViewModel viewModel;
        private String userId;
        private String postId;
        private String communityId;
        public CommentViewHolder(ItemChildCommentBinding binding, LifecycleOwner lifecycleOwner, String userId, String postId, String communityId) {
            super(binding.getRoot());
            this.binding = binding;
            this.lifecycleOwner = lifecycleOwner;
            viewModel = new PostDetailsViewModel();
            this.userId = userId;
            this.postId = postId;
            this.communityId = communityId;
        }
        private void bindingComponents(CommentViewState comment) {
            binding.contentChildTextView.setText(comment.getContent());
            binding.voteCountChildTextView.setText(String.valueOf(comment.getVoteDifference()));
            binding.timeChildTextView.setText(comment.getTimeCreated());
            binding.userNameChildTextView.setText(comment.getCreator().getName());
            binding.khoaChildTextView.setText(comment.getCreator().getDepartment());


            if(comment.getCreator().getProfilePicture()!=null){
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(comment.getCreator().getProfilePicture());
                Glide.with(binding.getRoot().getContext())
                        .load(storageReference)
                        .into(binding.avatarChildrenComment);
            }

            binding.replyChildTextView.setVisibility(View.GONE);


            viewModel.isVotedComment(comment, userId,postId,communityId).observe(lifecycleOwner, voteType -> {
                if (voteType != null) {
                    if (voteType == 1) {
                        ColorStateList colorStateList = ContextCompat.getColorStateList(binding.getRoot().getContext(), R.color.likedButtonColor);
                        binding.upVoteChildCommentButton.setIconTint(colorStateList);

                    } else if (voteType == -1) {
                        ColorStateList colorStateList = ContextCompat.getColorStateList(binding.getRoot().getContext(), R.color.likedButtonColor);
                        binding.downVoteChildButton.setIconTint(colorStateList);

                    }
                }
            });

        }

        public void bind(CommentViewState comment,
                         CommentAdapter.OnReplyClickListener onReplyClickListener,
                         CommentAdapter.OnDownVoteClickListener onDownVoteClickListener,
                         CommentAdapter.OnUpVoteClickListener onUpVoteClickListener) {

            bindingComponents(comment);


//            binding.replyChildTextView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    CommentViewState comment = commentList2.get(getAdapterPosition());
//                    onReplyClickListener.onReplyClick(comment);
//                }
//            });

            ColorStateList colorStateList = ContextCompat.getColorStateList(binding.getRoot().getContext(), R.color.likedButtonColor);
            binding.upVoteChildCommentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(binding.downVoteChildButton.getIconTint().equals(colorStateList)){
                        binding.downVoteChildButton.setIconTint(null);
                        binding.upVoteChildCommentButton.setIconTint(colorStateList);
                        binding.voteCountChildTextView.setText(String.valueOf(comment.getVoteDifference() + 2));
                        comment.setVoteDifference(comment.getVoteDifference() + 2);
                    }else  {
                        binding.upVoteChildCommentButton.setIconTint(colorStateList);
                        binding.voteCountChildTextView.setText(String.valueOf(comment.getVoteDifference() + 1));
                        comment.setVoteDifference(comment.getVoteDifference() + 1);
                    }
                    onUpVoteClickListener.onUpVote(comment);
                }
            });

            binding.downVoteChildButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(binding.upVoteChildCommentButton.getIconTint().equals(colorStateList)){
                        binding.downVoteChildButton.setIconTint(colorStateList);
                        binding.upVoteChildCommentButton.setIconTint(null);
                        binding.voteCountChildTextView.setText(String.valueOf(comment.getVoteDifference() - 2));
                        comment.setVoteDifference(comment.getVoteDifference() - 2);
                    }else{
                        binding.downVoteChildButton.setIconTint(colorStateList);
                        binding.voteCountChildTextView.setText(String.valueOf(comment.getVoteDifference() - 1));
                        comment.setVoteDifference(comment.getVoteDifference() - 1);
                    }
                    onDownVoteClickListener.onDownClick(comment);
                }
            });
        }

    }
}
