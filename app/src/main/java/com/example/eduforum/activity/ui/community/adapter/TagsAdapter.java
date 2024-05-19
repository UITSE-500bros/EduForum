package com.example.eduforum.activity.ui.community.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.activity.model.post_manage.Category;
import com.example.eduforum.activity.model.post_manage.Post;
import com.example.eduforum.activity.model.post_manage.PostCategory;
import com.example.eduforum.databinding.ItemTagsBinding;

import java.util.List;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagsViewHolder>{
    private List<PostCategory> tagsList;

    public TagsAdapter(List<PostCategory> tagsList) {
        this.tagsList = tagsList;
    }

    @NonNull
    @Override
    public TagsAdapter.TagsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemTagsBinding itemTagsBinding = ItemTagsBinding.inflate(layoutInflater, parent, false);

        return new TagsViewHolder(itemTagsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TagsAdapter.TagsViewHolder holder, int position) {
        PostCategory tag = tagsList.get(position);
        holder.binding.setTagText(tag.getTitle());
        holder.binding.executePendingBindings();
        holder.itemView.setOnClickListener(v -> {
            //

        });
    }

    @Override
    public int getItemCount() {
        return tagsList.size();
    }

    public class TagsViewHolder extends RecyclerView.ViewHolder{

        private ItemTagsBinding binding;
        public TagsViewHolder(ItemTagsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;




        }
    }
}
