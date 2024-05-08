package com.example.eduforum.activity.ui.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.databinding.ItemNotiBinding;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>{
    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemNotiBinding itemNotiBinding = ItemNotiBinding.inflate(layoutInflater, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{
        ItemNotiBinding binding;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = binding;
        }
    }
}
