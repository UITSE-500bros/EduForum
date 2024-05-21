package com.example.eduforum.activity.ui.community.adapter;

import android.app.AlertDialog;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.databinding.ItemImageBinding;

import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ImageViewHolder> {
    private List<Uri> mediaItems;

    private AdapterView.OnItemClickListener onItemClickListener;

    public MediaAdapter(List<Uri> mediaItems) {
        this.mediaItems = mediaItems;
    }

    public  void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemImageBinding itemImageBinding = ItemImageBinding.inflate(layoutInflater, parent, false);
        return new ImageViewHolder(itemImageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Uri mediaItem = mediaItems.get(position);
        holder.binding.image.setImageURI(mediaItem);
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(null, v, position, 0);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Xóa ảnh")
                    .setMessage("Bạn muốn xóa ảnh này?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        removeItem((int) position);
                        notifyDataSetChanged();
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
            return true;
        });
    }

    public void removeItem(int position) {
        mediaItems.remove(position);
    }

    @Override
    public int getItemCount() {
        return mediaItems.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        private ItemImageBinding binding;

        ImageViewHolder(ItemImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}