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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eduforum.R;
import com.example.eduforum.activity.ui.community.PostDetailActivity;
import com.example.eduforum.activity.ui.community.viewstate.CommentViewState;
import com.example.eduforum.activity.ui.main.adapter.ChildCommentAdapter;
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



    public interface OnReplyClickListener {
        void onReplyClick(CommentViewState comment);
    }
    public interface OnUpVoteClickListener {
        void onUpVote(CommentViewState comment);
    }
    public interface OnDownVoteClickListener {
        void onDownClick(CommentViewState comment);
    }

    public interface OnShowUpReplies {
        void onShowUpReplies(CommentViewState comment);
    }

    private CommentAdapter.OnReplyClickListener onReplyClickListener;
    private static CommentAdapter.OnUpVoteClickListener onUpVoteClickListener;
    private static CommentAdapter.OnDownVoteClickListener onDownVoteClickListener;
    private static CommentAdapter.OnShowUpReplies onShowUpReplies;

    public CommentChildAdapter(Context context,
                               List<CommentViewState> commentList,
                               CommentAdapter.OnReplyClickListener onReplyClickListener,
                               CommentAdapter.OnDownVoteClickListener onDownVoteClickListener,
                               CommentAdapter.OnUpVoteClickListener onUpVoteClickListener,
                               CommentAdapter.OnShowUpReplies onShowUpReplies) {
        this.context = context;
        if (commentList2 != null) {
            this.commentList2 = commentList;
        } else {
            this.commentList2 = new ArrayList<>();
        }

        this.onReplyClickListener = onReplyClickListener;
        this.onUpVoteClickListener = onUpVoteClickListener;
        this.onDownVoteClickListener = onDownVoteClickListener;
        this.onShowUpReplies = onShowUpReplies;
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
        ItemListCommentBinding itemChildCommentBinding = ItemListCommentBinding.inflate(layoutInflater, parent, false);
        return new CommentViewHolder(itemChildCommentBinding);
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



        holder.bind(comment, onReplyClickListener,onDownVoteClickListener,onUpVoteClickListener, onShowUpReplies);

    }

    @Override
    public int getItemCount() {
        return commentList2.size();
    }


    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        ItemListCommentBinding binding;
        public CommentViewHolder(ItemListCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
        private void bindingComponents(CommentViewState comment) {
            binding.contentNotiParentTextView.setText(comment.getContent());
            binding.voteCountParentTextView.setText(String.valueOf(comment.getVoteDifference()));
            binding.timeParentCommentTextView.setText(comment.getTimeCreated());
            binding.userNameParentTextView.setText(comment.getCreator().getName());
            binding.khoaParentTextView.setText(comment.getCreator().getDepartment());


            if(comment.getCreator().getProfilePicture()!=null){
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(comment.getCreator().getProfilePicture());
                Glide.with(binding.getRoot().getContext())
                        .load(storageReference)
                        .into(binding.avatarParentComment);
            }


        }

        public void bind(CommentViewState comment, CommentAdapter.OnReplyClickListener onReplyClickListener, CommentAdapter.OnDownVoteClickListener onDownVoteClickListener, CommentAdapter.OnUpVoteClickListener onUpVoteClickListener, CommentAdapter.OnShowUpReplies onShowUpReplies) {

            bindingComponents(comment);

            if(comment.getCreator().getProfilePicture()!=null){
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(comment.getCreator().getProfilePicture());
                Glide.with(binding.getRoot().getContext())
                        .load(storageReference)
                        .into(binding.avatarParentComment);
            }

            binding.replyParentTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommentViewState comment = commentList2.get(getAdapterPosition());
                    onReplyClickListener.onReplyClick(comment);
                }
            });


            binding.upVoteParentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.voteCountParentTextView.setText(String.valueOf(comment.getVoteDifference() + 1));
                    onUpVoteClickListener.onUpVote(comment);
                    ColorStateList colorStateList = ContextCompat.getColorStateList(binding.getRoot().getContext(), R.color.likedButtonColor);
                    binding.upVoteParentButton.setIconTint(colorStateList);
                }
            });

            binding.downVoteParentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.voteCountParentTextView.setText(String.valueOf(comment.getVoteDifference() - 1));
                    onDownVoteClickListener.onDownClick(comment);
                    ColorStateList colorStateList = ContextCompat.getColorStateList(binding.getRoot().getContext(), R.color.likedButtonColor);
                    binding.downVoteParentButton.setIconTint(colorStateList);
                }
            });

            binding.showReplyParentTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onShowUpReplies.onShowUpReplies(comment);
                }
            });
        }

    }
}
