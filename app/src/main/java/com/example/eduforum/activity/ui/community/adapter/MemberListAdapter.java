package com.example.eduforum.activity.ui.community.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.activity.model.community_manage.CommunityMember;
import com.example.eduforum.databinding.ItemMemberListBinding;

import java.util.List;

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MemberViewHolder>{
    private List<CommunityMember> memberList;
    private OnMemberClickListener onMemberClickListener;
    public interface OnMemberClickListener {
        void onMemberClick(CommunityMember member);
    }
    public void setOnMemberClickListener(OnMemberClickListener onMemberClickListener) {
        this.onMemberClickListener = onMemberClickListener;
    }

    public MemberListAdapter(List<CommunityMember> memberList) {
        this.memberList = memberList;
    }
    @NonNull
    @Override
    public MemberListAdapter.MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemMemberListBinding itemMemberListBinding = ItemMemberListBinding.inflate(layoutInflater, parent, false);

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MemberListAdapter.MemberViewHolder holder, int position) {
        CommunityMember member = memberList.get(position);
        holder.binding.setMember(member);
        holder.binding.executePendingBindings();
        holder.binding.cardView.setOnClickListener(v -> {
            if (onMemberClickListener != null) {
                onMemberClickListener.onMemberClick(member);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder{
        private ItemMemberListBinding binding;
        public MemberViewHolder(ItemMemberListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

        }
    }
}
