package com.example.eduforum.activity.ui.main.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.activity.model.community_manage.Community;
import com.example.eduforum.activity.ui.community.CommunityActivity;
import com.example.eduforum.activity.ui.main.fragment.CreateCommunityViewState;
import com.example.eduforum.databinding.ItemExploreCommunityBinding;

import java.util.List;

public class ExploreCommunityAdapter extends RecyclerView.Adapter<ExploreCommunityAdapter.ExploreCommunityViewHolder>{
    private List<Community> communityList;
    OnJoinCommunityClickListener listener;
    public interface OnJoinCommunityClickListener {
        void onJoinCommunityClick(View view, String communityId);
    }
    public ExploreCommunityAdapter(List<Community> communityList) {
        this.communityList = communityList;
    }
    public void setOnJoinCommunityClickListener(OnJoinCommunityClickListener listener) {
        this.listener = listener;
    }
    public void setCommunityList(List<Community> communityList) {
        this.communityList = communityList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ExploreCommunityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemExploreCommunityBinding binding = ItemExploreCommunityBinding.inflate(layoutInflater, parent, false);
        return new ExploreCommunityViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreCommunityViewHolder holder, int position) {
        holder.bind(communityList.get(position), listener);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), CommunityActivity.class);
            intent.putExtra("currentCommunity", new CreateCommunityViewState(communityList.get(position)));
            intent.putExtra("isExploring", true);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return communityList.size();
    }

    public static class ExploreCommunityViewHolder extends RecyclerView.ViewHolder {
        ItemExploreCommunityBinding binding;
        public ExploreCommunityViewHolder(@NonNull ItemExploreCommunityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(Community community, ExploreCommunityAdapter.OnJoinCommunityClickListener listener){
            binding.communityNameTextView.setText(community.getName());
            binding.communityMemberNumberTextView.setText(community.getUserList().size()+" thành viên");
            if(!community.getRequestSent()) {
                binding.joinButton.setOnClickListener(v -> {
                    listener.onJoinCommunityClick(v, community.getCommunityId());
                    binding.joinButton.setText("Đang chờ phê duyệt");
                    binding.joinButton.setEnabled(false);
                });
            }
            else{
                binding.joinButton.setText("Đã gửi yêu cầu");
            }
        }
    }
}
