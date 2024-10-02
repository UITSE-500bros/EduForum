package com.example.eduforum.activity.ui.community.adapter;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eduforum.activity.ui.community.ImageDetailActivity;
import com.example.eduforum.databinding.ItemImageBinding;
import com.example.eduforum.generated.callback.OnClickListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ImageViewHolder> {
    private List<String> mediaItems;

    private List<Uri> mediaItemsUri;
    private boolean isPostDetail;
    private AdapterView.OnItemClickListener onItemClickListener;

    public void setMediaItemsUri(List<Uri> mediaItemsUri) {
        this.mediaItemsUri = mediaItemsUri;
    }

    public void setPostDetail(boolean postDetail) {
        isPostDetail = postDetail;
    }

    public MediaAdapter(List<Uri> mediaItemsUri, boolean isPostDetail) {
        this.mediaItemsUri = mediaItemsUri;
        this.isPostDetail = false;
    }

    public MediaAdapter(List<String> mediaItems) {
        this.mediaItems = mediaItems;
        this.isPostDetail = true;
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
//        Uri mediaItem = Uri.parse(mediaItems.get(position));
//            holder.binding.image.setImageURI(mediaItem);
        if(isPostDetail) {
            holder.bind(mediaItems.get(position));
        } else {

            Uri mediaItemUri = mediaItemsUri.get(position);
            holder.binding.image.setImageURI(mediaItemUri);
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







//        holder.binding.image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), ImageDetailActivity.class);
//                intent.setData(mediaItem);
//                v.getContext().startActivity(intent);
//            }
//        });
    }

    public void removeItem(int position) {
        mediaItemsUri.remove(position);
    }

    @Override
    public int getItemCount() {
        if (isPostDetail) {
            return mediaItems.size();
        } else {
            return mediaItemsUri.size();
        }
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        private ItemImageBinding binding;


        ImageViewHolder(ItemImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String mediaItem) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(mediaItem);
            Glide.with(binding.getRoot().getContext())
                    .load(storageReference)
                    .into(binding.image);

        }
    }
}