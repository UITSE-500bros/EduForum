package com.example.eduforum.activity.ui.community.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eduforum.activity.ui.community.viewstate.CommentViewState;
import com.example.eduforum.databinding.ItemChildCommentBinding;
import com.example.eduforum.databinding.ItemListCommentBinding;
import java.util.ArrayList;
import java.util.List;

public class CommentChildAdapter extends RecyclerView.Adapter<CommentChildAdapter.CommentViewHolder>{
    private Context context;
    private List<CommentViewState> commentList;

    public CommentChildAdapter(Context context, List<CommentViewState> commentList) {
        this.context = context;
        if (commentList != null) {
            this.commentList = commentList;
        } else {
            this.commentList = new ArrayList<>();
        }
    }
    public void setCommentList(List<CommentViewState> commentList) {
        if (commentList != null) {
            this.commentList = commentList;
            notifyDataSetChanged();
        } else {
            this.commentList = new ArrayList<>();
        }
    }
    @NonNull
    @Override
    public CommentChildAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemChildCommentBinding itemChildCommentBinding = ItemChildCommentBinding.inflate(layoutInflater, parent, false);
        return new CommentViewHolder(itemChildCommentBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentViewState comment = commentList.get(position);
        holder.bind(comment);

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        ItemChildCommentBinding binding;
        public CommentViewHolder(ItemChildCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(CommentViewState comment) {
            binding.contentChildTextView.setText(comment.getContent());
        }
    }
}
