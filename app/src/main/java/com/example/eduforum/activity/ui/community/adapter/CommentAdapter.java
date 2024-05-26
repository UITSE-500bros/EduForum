package com.example.eduforum.activity.ui.community.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{
    private Context context;
    private static List<CommentViewState> commentList;

    private static List<CommentViewState> childCommentList;
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

    private OnReplyClickListener onReplyClickListener;
    private static OnUpVoteClickListener onUpVoteClickListener;
    private static OnDownVoteClickListener onDownVoteClickListener;
    private static OnShowUpReplies onShowUpReplies;
    private CommentChildAdapter commentChildAdapter;

    public CommentAdapter(Context context,
                          List<CommentViewState> commentList,
                          List<CommentViewState> childCommentList,
                          OnReplyClickListener onReplyClickListener,
                          OnDownVoteClickListener onDownVoteClickListener,
                          OnUpVoteClickListener onUpVoteClickListener,
                          OnShowUpReplies onShowUpReplies,
                          CommentChildAdapter commentChildAdapter) {
        this.context = context;
        if (commentList != null) {
            this.commentList = commentList;
        } else {
            this.commentList = new ArrayList<>();
        }
        if (childCommentList != null) {
            this.childCommentList = childCommentList;
        } else {
            this.childCommentList = new ArrayList<>();
        }
        this.onReplyClickListener = onReplyClickListener;
        this.onUpVoteClickListener = onUpVoteClickListener;
        this.onDownVoteClickListener = onDownVoteClickListener;
        this.onShowUpReplies = onShowUpReplies;
        this.commentChildAdapter = commentChildAdapter;
    }
    public void setCommentList(List<CommentViewState> commentList) {
        if (commentList != null) {
            this.commentList = commentList;
            notifyDataSetChanged();
        } else {
            this.commentList = new ArrayList<>();
        }
    }

    public  void setChildCommentList(List<CommentViewState> childCommentList) {
        if (childCommentList != null) {
            this.childCommentList = childCommentList;
        } else {
            this.childCommentList = new ArrayList<>();
        }
    }


    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemListCommentBinding itemListCommentBinding = ItemListCommentBinding.inflate(layoutInflater, parent, false);
        return new CommentViewHolder(itemListCommentBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        createDeleteDialog();

        CommentViewState comment = commentList.get(position);

        holder.binding.moreChildCommentButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, holder.binding.moreChildCommentButton);
            popupMenu.inflate(R.menu.comment_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.deleteComment) {
                    builder.show();
                } else {
                    //TODO: Edit Comment
                }
                return true;
            });
            popupMenu.show();
        });

        List<CommentViewState> temp = new ArrayList<>();

        for (CommentViewState commentViewState : childCommentList) {
            if (Objects.equals(commentViewState.getReplyCommentID(), comment.getCommentID())) {
                temp.add(commentViewState);
            }
        }

        holder.bind(comment, onReplyClickListener, temp, onDownVoteClickListener, onUpVoteClickListener, onShowUpReplies);
    }

    public void createDeleteDialog() {
        builder = new MaterialAlertDialogBuilder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc muốn xóa bình luận này chứ?");
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

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        ItemListCommentBinding binding;
        public CommentViewHolder(ItemListCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(CommentViewState comment,
                         OnReplyClickListener onReplyClickListener,
                         List<CommentViewState> temp,
                         OnDownVoteClickListener onDownVoteClickListener,
                         OnUpVoteClickListener onUpVoteClickListener,
                         OnShowUpReplies onShowUpReplies) {
            binding.contentNotiParentTextView.setText(comment.getContent());
            binding.userNameParentTextView.setText(comment.getCreator().getName());
            binding.voteCountParentTextView.setText(String.valueOf(comment.getVoteDifference()));
            binding.timeParentCommentTextView.setText(comment.getLastModified());


            if(comment.getCreator().getProfilePicture()!=null){
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(comment.getCreator().getProfilePicture());
                Glide.with(binding.getRoot().getContext())
                        .load(storageReference)
                        .into(binding.avatarParentComment);
            }

            binding.replyParentTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommentViewState comment = commentList.get(getAdapterPosition());
                    onReplyClickListener.onReplyClick(comment);
                }
            });
            binding.nestedRecyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
            binding.nestedRecyclerView.setAdapter(new ChildCommentAdapter(temp));



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