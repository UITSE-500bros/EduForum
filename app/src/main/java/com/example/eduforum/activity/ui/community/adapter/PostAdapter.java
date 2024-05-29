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
            Intent intent = new Intent(context, PostDetailActivity.class);
            intent.putExtra("currentPost", postList.get(position));
            context.startActivity(intent);
        });
        holder.binding.setting.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, holder.binding.setting);
            popupMenu.inflate(R.menu.post_option_menu);
            //popupMenu.setOnMenuItemClickListener(item -> {
                //TODO: Handle menu item click
            //});
            popupMenu.show();
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
            if(post.getCreator()!=null){
                binding.username.setText(post.getCreator().getName());
                binding.falcuty.setText(post.getCreator().getDepartment());
                if(post.getCreator().getProfilePicture()!=null){
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(post.getCreator().getProfilePicture());
                    Glide.with(binding.getRoot().getContext())
                            .load(storageReference)
                            .into(binding.avatar);
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
