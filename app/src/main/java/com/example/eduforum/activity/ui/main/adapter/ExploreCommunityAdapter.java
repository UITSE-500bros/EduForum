package com.example.eduforum.activity.ui.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.databinding.ItemExploreCommunityBinding;

public class ExploreCommunityAdapter extends RecyclerView.Adapter<ExploreCommunityAdapter.ExploreCommunityViewHolder>{

    @NonNull
    @Override
    public ExploreCommunityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemExploreCommunityBinding binding = ItemExploreCommunityBinding.inflate(layoutInflater, parent, false);
        return new ExploreCommunityViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreCommunityViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ExploreCommunityViewHolder extends RecyclerView.ViewHolder {
        ItemExploreCommunityBinding binding;
        public ExploreCommunityViewHolder(@NonNull ItemExploreCommunityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
