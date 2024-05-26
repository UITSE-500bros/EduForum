package com.example.eduforum.activity.ui.community.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.R;
import com.example.eduforum.activity.ui.community.viewstate.CommentViewState;
import com.example.eduforum.databinding.ItemChildCommentBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class CommentLevel3Adapter extends RecyclerView.Adapter<CommentLevel3Adapter.CommentViewHolder>{
    private Context context;
    private List<CommentViewState> commentChildList;
    private MaterialAlertDialogBuilder builder;

    public CommentLevel3Adapter(Context context, List<CommentViewState> commentChildList) {
        this.context = context;
        if (commentChildList != null) {
            this.commentChildList = commentChildList;
        } else {
            this.commentChildList = null;
        }
    }


    public void setCommentChildList(List<CommentViewState> commentChildList) {
        this.commentChildList = commentChildList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentLevel3Adapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemChildCommentBinding itemChildCommentBinding = ItemChildCommentBinding.inflate(layoutInflater, parent, false);
        return new CommentLevel3Adapter.CommentViewHolder(itemChildCommentBinding);
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
    public void onBindViewHolder(@NonNull CommentLevel3Adapter.CommentViewHolder holder, int position) {
        CommentViewState comment = commentChildList.get(position);
        holder.bind(comment);
        createDeleteDialog();

        holder.binding.moreChildCommentButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, holder.binding.moreChildCommentButton);
            popupMenu.inflate(R.menu.comment_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.deleteComment) {
                    builder.show();
                }
                else{

                }
                return true;
            });
            popupMenu.show();
        });

        // If the comment has child comments, set up a new CommentLevel3Adapter for them
//        if (comment.getChildComments() != null) {
//            CommentLevel3Adapter childCommentAdapter = new CommentLevel3Adapter(context, comment.getChildComments());
//            holder.binding.childCommentRecyclerView.setLayoutManager(new LinearLayoutManager(context));
//            holder.binding.childCommentRecyclerView.setAdapter(childCommentAdapter);
//        }


    }

    @Override
    public int getItemCount() {
        return commentChildList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        ItemChildCommentBinding binding;
        public CommentViewHolder(ItemChildCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CommentViewState comment) {
            binding.contentChildTextView.setText("2");
//            binding.timeCreatedChildTextView.setText(comment.getTimeCreated());
//            binding.creatorChildTextView.setText(comment.getCreator().getUsername());
//            binding.totalUpVoteChildTextView.setText(String.valueOf(comment.getTotalUpVote()));

            binding.upVoteChildCommentButton.setOnClickListener(v -> {

                ColorStateList colorStateList = ContextCompat.getColorStateList(binding.getRoot().getContext(), R.color.likedButtonColor);
                binding.downVoteChildButton.setIconTint(colorStateList);
            });

            binding.downVoteChildButton.setOnClickListener(v -> {
                ColorStateList colorStateList = ContextCompat.getColorStateList(binding.getRoot().getContext(), R.color.likedButtonColor);
                binding.downVoteChildButton.setIconTint(colorStateList);
            });

        }
    }
}
