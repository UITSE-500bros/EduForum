package com.example.eduforum.activity.ui.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.databinding.ItemForumBinding;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder> {

    // private List<Forum> forums;

    /*
    public ForumAdapter(List<Forum> forums) {
        this.forums = forums;
    }
*/
    @NonNull
    @Override
    public CommunityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemForumBinding itemBinding = ItemForumBinding.inflate(layoutInflater, parent, false);
        return new CommunityViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        //return forums.size();
        return 0;
    }

    static class CommunityViewHolder extends RecyclerView.ViewHolder {
        private final ItemForumBinding binding;

        public CommunityViewHolder(ItemForumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}
