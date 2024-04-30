package com.example.eduforum.activity.ui.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.R;
import com.example.eduforum.databinding.ItemNotiBinding;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemNotiBinding itemNotiBinding = ItemNotiBinding.inflate(layoutInflater, parent, false);
        return new NotificationViewHolder(itemNotiBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        ItemNotiBinding binding;
        public NotificationViewHolder(ItemNotiBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
