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
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick( int position);
    }
    public TagsAdapter(List<PostCategory> tagsList, OnItemClickListener listener) {
        this.tagsList = tagsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TagsAdapter.TagsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemTagsBinding itemTagsBinding = ItemTagsBinding.inflate(layoutInflater, parent, false);

        return new TagsViewHolder(itemTagsBinding,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TagsAdapter.TagsViewHolder holder, int position) {
        PostCategory tag = tagsList.get(position);
        holder.binding.setTagText(tag.getTitle());
        holder.binding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return tagsList.size();
    }

    public class TagsViewHolder extends RecyclerView.ViewHolder{

        private ItemTagsBinding binding;
        public TagsViewHolder(ItemTagsBinding binding, OnItemClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });


        }
    }
}
