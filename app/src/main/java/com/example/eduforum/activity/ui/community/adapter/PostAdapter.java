package com.example.eduforum.activity.ui.community.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.activity.ui.community.viewstate.PostViewState;
import com.example.eduforum.databinding.ItemCommunityBinding;
import com.example.eduforum.databinding.ItemNotiBinding;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private Context context;
    private List<PostViewState> postList;

    public PostAdapter(Context context, List<PostViewState> postList) {
        this.context = context;
        if(postList == null) {
            this.postList = new ArrayList<>();
        }
        else this.postList = postList;
    }
    public void setPostList(List<PostViewState> postList) {
        this.postList = postList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemCommunityBinding itemCommunityBinding = ItemCommunityBinding.inflate(layoutInflater, parent, false);
        return new PostViewHolder(itemCommunityBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostViewState post = postList.get(position);
        holder.bind(post);
        holder.itemView.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        ItemCommunityBinding binding;
        public PostViewHolder(ItemCommunityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(PostViewState post) {
            if(post.getAuthor()!=null){
                binding.username.setText(post.getAuthor().getName());
                binding.falcuty.setText(post.getAuthor().getDepartment());
            }
            binding.title.setText(post.getTitle());
            binding.time.setText(post.getDate());
            binding.like.setText("0 lượt thích");
            binding.comment.setText("0 bình luận");
        }
    }
}
