package com.example.eduforum.activity.ui.community.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.databinding.ItemImageBinding;
import com.example.eduforum.databinding.ItemNotiBinding;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{
    private ArrayList<Uri> imageList;

    public ImageAdapter(ArrayList<Uri> imageList) {
        this.imageList = imageList;
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
        Uri image = imageList.get(position);
        holder.binding.image.setImageURI(image);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ItemImageBinding binding;
        public ImageViewHolder(ItemImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
