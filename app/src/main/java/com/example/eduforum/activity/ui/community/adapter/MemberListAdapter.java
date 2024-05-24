package com.example.eduforum.activity.ui.community.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.R;
import com.example.eduforum.activity.model.community_manage.CommunityMember;
import com.example.eduforum.databinding.ItemMemberListBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MemberViewHolder>{
    private List<CommunityMember> memberList;
    private OnMemberClickListener onMemberClickListener;
    public interface OnMemberClickListener {
        void onMemberClick(CommunityMember member);
        void onMemberLongClick(CommunityMember member);
    }
    public void setOnMemberClickListener(OnMemberClickListener onMemberClickListener) {
        this.onMemberClickListener = onMemberClickListener;
    }
    public void setMemberList(List<CommunityMember> memberList) {
        this.memberList = memberList;
        notifyDataSetChanged();
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
        holder.itemView.setOnClickListener(v -> {
            if (onMemberClickListener != null) {
                onMemberClickListener.onMemberClick(member);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (onMemberClickListener != null) {
                onMemberClickListener.onMemberLongClick(member);
            }
            return true;
        });

    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder{
        private ItemMemberListBinding binding;
        public MemberViewHolder(ItemMemberListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

        }

    }
}
