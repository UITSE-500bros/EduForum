package com.example.eduforum.activity.ui.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.databinding.ItemForumBinding;

import java.util.List;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ForumViewHolder> {

    // private List<Forum> forums;

    /*
    public ForumAdapter(List<Forum> forums) {
        this.forums = forums;
    }
*/
    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemForumBinding itemBinding = ItemForumBinding.inflate(layoutInflater, parent, false);
        return new ForumViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        //return forums.size();
        return 0;
    }

    static class ForumViewHolder extends RecyclerView.ViewHolder {
        private final ItemForumBinding binding;

        public ForumViewHolder(ItemForumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}
