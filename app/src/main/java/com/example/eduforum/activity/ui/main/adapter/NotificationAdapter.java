package com.example.eduforum.activity.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.R;
import com.example.eduforum.databinding.ItemNotiBinding;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    Context context;
    public NotificationAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemNotiBinding itemNotiBinding = ItemNotiBinding.inflate(layoutInflater, parent, false);
        return new NotificationViewHolder(itemNotiBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.binding.moreOptionImageButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, v);
            popupMenu.getMenuInflater().inflate(R.menu.noti_option_menu, popupMenu.getMenu());
            //popupMenu.setOnMenuItemClickListener(item -> {
            //TODO: Handle menu item click
            //});
            popupMenu.show();
        });
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
