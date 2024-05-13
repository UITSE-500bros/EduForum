package com.example.eduforum.activity.ui.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.databinding.ItemChildCommentBinding;
import com.example.eduforum.databinding.ItemListCommentBinding;

public class ChildCommentAdapter extends RecyclerView.Adapter<ChildCommentAdapter.ChildCommentViewHolder> {

    @NonNull
    @Override
    public ChildCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChildCommentBinding binding = ItemChildCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ChildCommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildCommentViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public static class ChildCommentViewHolder extends RecyclerView.ViewHolder {
        ItemChildCommentBinding binding;
        public ChildCommentViewHolder(@NonNull ItemChildCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
