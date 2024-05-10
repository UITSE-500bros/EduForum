package com.example.eduforum.activity.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.databinding.ItemCommentBinding;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHoloder>{
    Context context;
    public CommentAdapter() {
    }
    @NonNull
    @Override
    public CommentViewHoloder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommentBinding binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CommentViewHoloder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHoloder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class CommentViewHoloder extends RecyclerView.ViewHolder {
        ItemCommentBinding binding;
        public CommentViewHoloder(@NonNull ItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
