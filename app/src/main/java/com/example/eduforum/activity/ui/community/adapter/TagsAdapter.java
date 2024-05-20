package com.example.eduforum.activity.ui.community.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.R;
import com.example.eduforum.activity.model.post_manage.Category;
import com.example.eduforum.activity.model.post_manage.Post;
import com.example.eduforum.activity.model.post_manage.PostCategory;
import com.example.eduforum.databinding.ItemTagsBinding;

import java.util.ArrayList;
import java.util.List;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagsViewHolder>{
    private List<PostCategory> tagsList;
    private List<Category> selectedTags;
    private Boolean isFiltering;

    public TagsAdapter(List<PostCategory> tagsList, List<Category> selectedTags, Boolean isFiltering) {
        this.tagsList = tagsList;
        if(selectedTags == null) {
            this.selectedTags = new ArrayList<>();
        } else {
            this.selectedTags = selectedTags;
        }
        this.isFiltering = isFiltering;
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
        if(isFiltering){
            holder.itemView.setOnClickListener(v -> {
                Category category = new Category(tag.getCategoryID(), tag.getTitle(), false);
                if (selectedTags.contains(category)) {
                    selectedTags.remove(category);
                    int unselectedColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.tagBackgroundColor);
                    holder.binding.tagCardView.setCardBackgroundColor(unselectedColor);
                } else {
                    selectedTags.add(category);
                    int selectedColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.selectedTagBackgroundColor);
                    holder.binding.tagCardView.setCardBackgroundColor(selectedColor);
                }

            });
        }
        holder.itemView.setOnLongClickListener(v -> {
            tagsList.remove(position);
            notifyItemRemoved(position);
            return true;
        });

    }

    public List<Category> getSelectedTags() {
        return selectedTags;
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
