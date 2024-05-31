package com.example.eduforum.activity.ui.community.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eduforum.R;
import com.example.eduforum.activity.ui.community.PostDetailActivity;
import com.example.eduforum.activity.ui.community.viewstate.PostViewState;
import com.example.eduforum.databinding.ItemCommunityBinding;
import com.example.eduforum.databinding.ItemNotiBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private List<PostViewState> postList;
    private Boolean isUITcommunity;
    private Boolean isExploring;

    public PostAdapter(Context context, List<PostViewState> postList) {
        this.context = context;
        if(postList == null) {
            this.postList = new ArrayList<>();
        }
        else this.postList = postList;
        this.isUITcommunity = false;
        this.isExploring = false;

    }
    public void setPostList(List<PostViewState> postList) {
        this.postList = postList;
        notifyDataSetChanged();
    }
    public void setIsExploring(Boolean isExploring) {
        this.isExploring = isExploring;
    }
    public void setIsUITcommunity(Boolean isUITcommunity) {
        this.isUITcommunity = isUITcommunity;
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
        holder.bind(post, isUITcommunity);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PostDetailActivity.class);
            intent.putExtra("key", "currentPost");
            intent.putExtra("currentPost", postList.get(position));
            intent.putExtra("isUITcommunity", isUITcommunity);
            intent.putExtra("isExploring", isExploring);
            context.startActivity(intent);
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
        void bind(PostViewState post, Boolean isUIT) {
            if(isUIT){
                binding.username.setText("Quản trị viên");
                binding.falcuty.setText("");
            }
            else if(post.getCreator()!=null){
                if(post.getAnonymous() != null && !post.getAnonymous())
                {
                    binding.username.setText(post.getCreator().getName());
                    binding.falcuty.setText(post.getCreator().getDepartment());
                    if(post.getCreator().getProfilePicture()!=null){
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(post.getCreator().getProfilePicture());
                        Glide.with(binding.getRoot().getContext())
                                .load(storageReference)
                                .into(binding.avatar);
                    }

                }
                else{
                    binding.username.setText("Ẩn danh");
                    binding.falcuty.setText("");

                }
            }
            binding.title.setText(post.getTitle());
            binding.time.setText(post.getDate());
            binding.like.setText(post.getVoteDifference()+" lượt bình chọn");
            binding.comment.setText(post.getTotalComment()+" bình luận");
            binding.time.setText(post.getDate());

            if(post.getTags()!=null){
                TagsAdapter tagsAdapter = new TagsAdapter(post.getTags(), null, false, false);
                binding.tagslayout.setAdapter(tagsAdapter);
                binding.tagslayout.setLayoutManager(new LinearLayoutManager(binding.tagslayout.getContext(), LinearLayoutManager.HORIZONTAL, false));
            }


        }
    }
}
