package com.example.eduforum.activity.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.activity.ui.main.fragment.CreateCommunityViewState;
import com.example.eduforum.databinding.ItemForumBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder> {

    Context context;
    List<CreateCommunityViewState> communityList;
    FirebaseAuth currentUser;
    public CommunityAdapter(Context context, List<CreateCommunityViewState> communityList, FirebaseAuth currentUser) {
        this.context = context;
        this.communityList = communityList;
        this.currentUser = currentUser;
    }
    public void setCommunityList(List<CreateCommunityViewState> communityList) {
        this.communityList = communityList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CommunityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemForumBinding binding = ItemForumBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CommunityViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityViewHolder holder, int position) {
        holder.bind(communityList.get(position));
        holder.itemView.setOnClickListener(v -> {
            //Intent intent = new Intent(context, CommunityActivity.class);
            //intent.putExtra("community", communityList.get(position));
            //context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CommunityViewHolder extends RecyclerView.ViewHolder {
        ItemForumBinding binding;
        public CommunityViewHolder(@NonNull ItemForumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(CreateCommunityViewState community) {
            binding.communityNameTextView.setText(community.getName());
            if(community.getCommuAvt()!=null){
                // Glide...
            }
        }

    }
}
