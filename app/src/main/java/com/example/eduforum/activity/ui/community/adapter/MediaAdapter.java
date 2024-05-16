package com.example.eduforum.activity.ui.community.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.databinding.ItemImageBinding;
import com.example.eduforum.databinding.ItemVideoBinding;

import java.util.ArrayList;

public class MediaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_IMAGE = 0;
    private static final int TYPE_VIDEO = 1;
    private AdapterView.OnItemClickListener onItemClickListener;


    private ArrayList<MediaItem> mediaItems;

    public  interface  OnItemClickListener{
        void onItemClick(int position);
    }

    public MediaAdapter(ArrayList<MediaItem> mediaItems) {
        this.mediaItems = mediaItems;
    }

    @Override
    public int getItemViewType(int position) {
        if (mediaItems.get(position).isVideo()) {
            return TYPE_VIDEO;
        } else {
            return TYPE_IMAGE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_IMAGE) {
            ItemImageBinding itemImageBinding = ItemImageBinding.inflate(layoutInflater, parent, false);
            return new ImageViewHolder(itemImageBinding);
        } else if (viewType == TYPE_VIDEO) {
            ItemVideoBinding itemVideoBinding = ItemVideoBinding.inflate(layoutInflater, parent, false);
            return new VideoViewHolder(itemVideoBinding);
        } else {
            throw new IllegalArgumentException("Invalid view type");
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MediaItem mediaItem = mediaItems.get(position);
        if (holder instanceof ImageViewHolder) {
            ((ImageViewHolder) holder).binding.image.setImageURI(mediaItem.getUri());
        } else if (holder instanceof VideoViewHolder) {
            ((VideoViewHolder) holder).binding.video.setVideoURI(mediaItem.getUri());
        }
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

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        private ItemVideoBinding binding;

        VideoViewHolder(ItemVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

