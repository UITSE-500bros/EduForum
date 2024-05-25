package com.example.eduforum.activity.ui.community.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.databinding.ItemMemberRequestBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class MemberRequestsAdapter extends RecyclerView.Adapter<MemberRequestsAdapter.MemberRequestsViewHolder> {

    List<User> memberRequests;
    OnMemberRequestListener listener;
    public interface OnMemberRequestListener {
        void onReview(User user, Boolean isApproved);
    }
    public MemberRequestsAdapter(List<User> memberRequests) {
        this.memberRequests = memberRequests;
    }
    public void setMemberRequests(List<User> memberRequests) {
        this.memberRequests = memberRequests;
        notifyDataSetChanged();
    }
    public void setOnMemberRequestListener(OnMemberRequestListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public MemberRequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemMemberRequestBinding itemMemberRequestBinding = ItemMemberRequestBinding.inflate(layoutInflater, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MemberRequestsViewHolder holder, int position) {
       holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return memberRequests.size();
    }

    public class MemberRequestsViewHolder extends RecyclerView.ViewHolder {
        ItemMemberRequestBinding binding;
        public MemberRequestsViewHolder(ItemMemberRequestBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position) {
            User user = memberRequests.get(position);
            binding.userNameTextView.setText(user.getName());
            binding.khoaTextView.setText(user.getDepartment());

            // buttons action
            binding.acceptRequestButton.setOnClickListener(v -> {
                if(listener != null) {
                    listener.onReview(user, true);
                }
            });
            binding.denytRequestButton.setOnClickListener(v -> {
                if(listener != null) {
                    listener.onReview(user, false);
                }
            });
            if(memberRequests.get(position).getProfilePicture()!=null){
                StorageReference storageReference = FirebaseStorage.getInstance().getReference(memberRequests.get(position).getProfilePicture());
                Glide.with(binding.getRoot().getContext())
                        .load(storageReference)
                        .into(binding.memberRequestAva);
            }

        }
    }
}
