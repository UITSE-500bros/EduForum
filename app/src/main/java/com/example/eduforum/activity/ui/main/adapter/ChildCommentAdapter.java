package com.example.eduforum.activity.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.activity.ui.community.viewstate.CommentViewState;
import com.example.eduforum.databinding.ItemChildCommentBinding;
import com.example.eduforum.databinding.ItemListCommentBinding;

import java.util.List;

public class ChildCommentAdapter extends RecyclerView.Adapter<ChildCommentAdapter.ChildCommentViewHolder> {

    private Context context;
    private List<CommentViewState> childCommentList;
    public ChildCommentAdapter(List<CommentViewState> childCommentList) {
        this.childCommentList = childCommentList;
    }

    @NonNull
    @Override
    public ChildCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemChildCommentBinding itemListCommentBinding = ItemChildCommentBinding.inflate(layoutInflater.from(parent.getContext()), parent, false);

        return new ChildCommentViewHolder(itemListCommentBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildCommentViewHolder holder, int position) {

        CommentViewState comment = childCommentList.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return childCommentList.size();
    }
    public static class ChildCommentViewHolder extends RecyclerView.ViewHolder {
        ItemChildCommentBinding binding;
        public ChildCommentViewHolder(@NonNull ItemChildCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CommentViewState commentViewState) {
            binding.contentChildTextView.setText(commentViewState.getContent());
        }
    }
}
