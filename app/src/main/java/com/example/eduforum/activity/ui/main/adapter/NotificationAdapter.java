package com.example.eduforum.activity.ui.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eduforum.R;
import com.example.eduforum.activity.model.noti_manage.Notification;
import com.example.eduforum.activity.ui.community.PostDetailActivity;
import com.example.eduforum.activity.ui.community.viewstate.PostViewState;
import com.example.eduforum.activity.ui.main.fragment.NotificationViewState;
import com.example.eduforum.databinding.ItemNotiBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    Context context;
    private List<NotificationViewState> notificationList;
    public NotificationAdapter(Context context, List<NotificationViewState> notificationList) {
        this.context = context;
        if (notificationList == null) {
            this.notificationList = new ArrayList<>();
        }
        else {
            this.notificationList = notificationList;
        }
    }

    public void setNotificationList(List<NotificationViewState> notificationList) {
        this.notificationList = notificationList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemNotiBinding itemNotiBinding = ItemNotiBinding.inflate(layoutInflater, parent, false);
        return new NotificationViewHolder(itemNotiBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationViewState notification = notificationList.get(position);
        holder.bind(notification);

        holder.binding.moreOptionImageButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, v);
            popupMenu.getMenuInflater().inflate(R.menu.noti_option_menu, popupMenu.getMenu());
            //popupMenu.setOnMenuItemClickListener(item -> {
            //TODO: Handle menu item click
            //});
            popupMenu.show();
        });
        holder.binding.notiCardView.setOnClickListener(v -> {
            ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#001D3D"));
            holder.binding.notiCardView.setCardBackgroundColor(colorStateList);
            holder.binding.notiCardView.setRadius(10);
            handleCardViewClick(notificationList.get(position));
            notification.setRead(true);
        });


    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public int getUnreadCount() {
        int count = 0;
        for (NotificationViewState notification : notificationList) {
            if (!notification.isRead()) {
                count++;
            }
        }
        return count;
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        ItemNotiBinding binding;
        public NotificationViewHolder(ItemNotiBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bindingComponents(NotificationViewState notificationViewState) {
            if(notificationViewState.getTriggerBy().getProfilePicture()!=null){
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(notificationViewState.getTriggerBy().getProfilePicture());
                Glide.with(binding.getRoot().getContext())
                        .load(storageReference)
                        .into(binding.avatarCardView);
            }
            String communityName = notificationViewState.getCommunityName();
            binding.commuNotiTextView.setText(communityName);
            binding.timeNotiTextView.setText(notificationViewState.getDate());

            if(notificationViewState.isRead()) binding.notiCardView.setBackgroundColor(Color.parseColor("#001D3D"));

            switch (notificationViewState.getType()) {
                case 1:
                    binding.contentNotiTextView.setText(notificationViewState.getTriggerBy().getName() + " đã đăng một bình luận mới vào bài viết của bạn.");
                    break;
                case 2:
                    binding.contentNotiTextView.setText(notificationViewState.getTriggerBy().getName() + " đăng một post mới trong cộng đồng.");
                    break;
                case 3:
                    binding.contentNotiTextView.setText(notificationViewState.getTriggerBy().getName() + " đã trả lời bình luận của bạn.");
                    break;
                case 4: // new announcement
                    binding.contentNotiTextView.setText(notificationViewState.getTriggerBy().getName() + " đã đăng một thông báo mới.");
                    break;
                case 5:
                    binding.contentNotiTextView.setText(notificationViewState.getTriggerBy().getName() + " đã bình luận vào bài viết bạn quan tâm.");
                    break;
            }

        }

        public void bind(NotificationViewState notificationViewState) {
            bindingComponents(notificationViewState);

        }
    }
    //TODO: Handle card view click
    public void handleCardViewClick(NotificationViewState notificationViewState) {
        Intent intent = new Intent(context, PostDetailActivity.class);
        intent.putExtra("key", "noti");
        intent.putExtra("notiCommunityID", notificationViewState.getCommunityID());
        intent.putExtra("notiCommunityName", notificationViewState.getCommunityName());
        intent.putExtra("notiPost", notificationViewState.getPostID());
        context.startActivity(intent);
    }
}
