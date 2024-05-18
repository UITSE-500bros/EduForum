package com.example.eduforum.activity.ui.community.adapter;

import android.app.AlertDialog;
import android.util.Log;
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


    private ArrayList<MediaItem> mediaItems;

    private AdapterView.OnItemClickListener onItemClickListener;

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
    public  void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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
            holder.itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(null, v, position, 0);
                }
            });
        } else if (holder instanceof VideoViewHolder) {
            VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
            videoViewHolder.binding.video.setVideoURI(mediaItem.getUri());

            videoViewHolder.binding.video.setOnPreparedListener(mp -> {
                mp.setLooping(true);
                mp.start();
            });
            videoViewHolder.binding.video.setOnErrorListener((mp, what, extra) -> {
                // Log the error or show a message to the user
                Log.e("VideoView", "Error occurred while playing video.");
                return true;
            });

        }
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Xóa ảnh")
                    .setMessage("Bạn muốn xóa ảnh/video  này?")
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

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        private ItemVideoBinding binding;

        VideoViewHolder(ItemVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}

