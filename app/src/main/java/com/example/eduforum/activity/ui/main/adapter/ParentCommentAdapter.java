package com.example.eduforum.activity.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.databinding.ItemListCommentBinding;

public class ParentCommentAdapter extends RecyclerView.Adapter<ParentCommentAdapter.ParentCommentViewHoloder>{
    Context context;
    public ParentCommentAdapter() {
    }
    @NonNull
    @Override
    public ParentCommentViewHoloder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListCommentBinding binding = ItemListCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ParentCommentViewHoloder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentCommentViewHoloder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ParentCommentViewHoloder extends RecyclerView.ViewHolder {
        ItemListCommentBinding binding;
        public ParentCommentViewHoloder(@NonNull ItemListCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
