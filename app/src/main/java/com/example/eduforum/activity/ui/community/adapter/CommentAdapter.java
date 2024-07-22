package com.example.eduforum.activity.ui.community.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.eduforum.R;
import com.example.eduforum.activity.model.post_manage.Comment;
import com.example.eduforum.activity.repository.comment.CommentCallback;
import com.example.eduforum.activity.repository.comment.CommentRepository;
import com.example.eduforum.activity.ui.community.viewstate.CommentViewState;
import com.example.eduforum.activity.viewmodel.community.PostDetailsViewModel;
import com.example.eduforum.databinding.ItemListCommentBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{
    private Context context;
    private static List<CommentViewState> commentList;
    private static List<CommentViewState> childCommentList;
    private MaterialAlertDialogBuilder builder;
    private CommentRepository commentRepository;
    private String userID;
    private LifecycleOwner lifecycleOwner;
    private String postId;
    private String communityId;


    public interface OnReplyClickListener {
        void onReplyClick(CommentViewState comment);
    }
    public interface OnUpVoteClickListener {
        void onUpVote(CommentViewState comment);
    }
    public interface OnDownVoteClickListener {
        void onDownClick(CommentViewState comment);
    }

    public interface OnShowUpReplies {
        void onShowUpReplies(CommentViewState comment);
    }

    private OnReplyClickListener onReplyClickListener;
    private static OnUpVoteClickListener onUpVoteClickListener;
    private static OnDownVoteClickListener onDownVoteClickListener;
    private static OnShowUpReplies onShowUpReplies;

    public CommentAdapter(Context context, LifecycleOwner lifecycleOwner, String userId,String postId,String communityId,

                          List<CommentViewState> commentList,
                          List<CommentViewState> childCommentList,
                          OnReplyClickListener onReplyClickListener,
                          OnDownVoteClickListener onDownVoteClickListener,
                          OnUpVoteClickListener onUpVoteClickListener,
                          OnShowUpReplies onShowUpReplies
                          ) {
        commentRepository = new CommentRepository();
        this.context = context;
        if (commentList != null) {
            this.commentList = commentList;
        } else {
            this.commentList = new ArrayList<>();
        }
        if (childCommentList != null) {
            this.childCommentList = childCommentList;
        } else {
            this.childCommentList = new ArrayList<>();
        }
        this.onReplyClickListener = onReplyClickListener;
        this.onUpVoteClickListener = onUpVoteClickListener;
        this.onDownVoteClickListener = onDownVoteClickListener;
        this.onShowUpReplies = onShowUpReplies;
        this.userID = userId;
        this.lifecycleOwner = lifecycleOwner;
        this.postId = postId;
        this.communityId = communityId;
    }
    public void setCommentList(List<CommentViewState> commentList) {
        if (commentList != null) {
            this.commentList = commentList;
            notifyDataSetChanged();
        } else {
            this.commentList = new ArrayList<>();
        }
    }

    public  void setChildCommentList(List<CommentViewState> childCommentList) {
        if (childCommentList != null) {
            this.childCommentList = childCommentList;
        } else {
            this.childCommentList = new ArrayList<>();
        }
    }


    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemListCommentBinding itemListCommentBinding = ItemListCommentBinding.inflate(layoutInflater, parent, false);
        return new CommentViewHolder(itemListCommentBinding, lifecycleOwner,userID,postId,communityId);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentViewState comment = commentList.get(position);

        List<CommentViewState> temp = new ArrayList<>();

        for (CommentViewState commentViewState : childCommentList) {
            if (Objects.equals(commentViewState.getReplyCommentID(), comment.getCommentID())) {
                temp.add(commentViewState);
            }
        }

        holder.bind(comment, onReplyClickListener, temp, onDownVoteClickListener, onUpVoteClickListener, onShowUpReplies);
    }
    public void removeItem(int position) {
        commentList.remove(position);
        notifyItemRemoved(position);
    }

    public void createDeleteDialog(int position) {
        builder = new MaterialAlertDialogBuilder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc muốn xóa bình luận này chứ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeItem(position);
                Comment comment  = new Comment();
                comment.setCommentID(commentList.get(position).getCommentID());


                commentRepository.deleteComment(comment, new CommentCallback() {
                    @Override
                    public void onCreateSuccess(Comment comments) {

                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }

                    @Override
                    public void onInitialLoadSuccess(List<Comment> comments) {

                    }

                    @Override
                    public void onLoadRepliesSuccess(List<Comment> comments) {

                    }

                    @Override
                    public void onDeleteSuccess() {

                    }

                    @Override
                    public void onUpdateSuccess(Comment comment) {

                    }

                    @Override
                    public void onGetVoteStatusSuccess(int voteType) {

                    }
                });
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        ItemListCommentBinding binding;
        private PostDetailsViewModel viewModel;
        private LifecycleOwner lifecycleOwner;
        private String userID;
        private String postId;
        private String communityId;

        public CommentViewHolder(ItemListCommentBinding binding, LifecycleOwner lifecycleOwner,String userId,String postId,String communityId){
            super(binding.getRoot());
            this.binding = binding;
            this.lifecycleOwner = lifecycleOwner;
            this.userID = userId;
            viewModel = new PostDetailsViewModel();
            this.postId = postId;
            this.communityId = communityId;
        }

        public void bindingComponents(CommentViewState comment) {
            binding.contentNotiParentTextView.setText(comment.getContent());
            binding.voteCountParentTextView.setText(String.valueOf(comment.getVoteDifference()));
            binding.timeParentCommentTextView.setText(comment.getTimeCreated());
            binding.userNameParentTextView.setText(comment.getCreator().getName());
            binding.khoaParentTextView.setText(comment.getCreator().getDepartment());


            if(comment.getCreator().getProfilePicture()!=null){
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(comment.getCreator().getProfilePicture());
                Glide.with(binding.getRoot().getContext())
                        .load(storageReference)
                        .into(binding.avatarParentComment);
            }

            viewModel.isVotedComment(comment, userID,postId,communityId).observe(lifecycleOwner, voteType -> {
                if (voteType != null) {
                    if (voteType == 1) {
                        ColorStateList colorStateList = ContextCompat.getColorStateList(binding.getRoot().getContext(), R.color.likedButtonColor);
                        binding.upVoteParentButton.setIconTint(colorStateList);

                    } else if (voteType == -1) {
                        ColorStateList colorStateList = ContextCompat.getColorStateList(binding.getRoot().getContext(), R.color.likedButtonColor);
                        binding.downVoteParentButton.setIconTint(colorStateList);

                    }
                }
            });
        }

        public void bind(CommentViewState comment, OnReplyClickListener onReplyClickListener,List<CommentViewState> temp,OnDownVoteClickListener onDownVoteClickListener,OnUpVoteClickListener onUpVoteClickListener,OnShowUpReplies onShowUpReplies) {

            bindingComponents(comment);

            /*Set up RecyclerView*/
            binding.nestedRecyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
            binding.nestedRecyclerView.setAdapter(new CommentChildAdapter(this.binding.getRoot().getContext(), lifecycleOwner, userID, postId, communityId,
                    temp,
                    onReplyClickListener,
                    onDownVoteClickListener,
                    onUpVoteClickListener
                    ));


            /*Set up click listener*/
            binding.replyParentTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommentViewState comment = commentList.get(getAdapterPosition());
                    onReplyClickListener.onReplyClick(comment);

                }
            });

            ColorStateList colorStateList = ContextCompat.getColorStateList(binding.getRoot().getContext(), R.color.likedButtonColor);
            binding.upVoteParentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(binding.downVoteParentButton.getIconTint().equals(colorStateList)){
                        binding.downVoteParentButton.setIconTint(null);
                        binding.upVoteParentButton.setIconTint(colorStateList);
                        binding.voteCountParentTextView.setText(String.valueOf(comment.getVoteDifference() + 2));
                        comment.setVoteDifference(comment.getVoteDifference() + 2);
                    }else  {
                        binding.upVoteParentButton.setIconTint(colorStateList);
                        binding.voteCountParentTextView.setText(String.valueOf(comment.getVoteDifference() + 1));
                        comment.setVoteDifference(comment.getVoteDifference() + 1);
                    }
                    onUpVoteClickListener.onUpVote(comment);
                }
            });

            binding.downVoteParentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(binding.upVoteParentButton.getIconTint().equals(colorStateList)){
                        binding.downVoteParentButton.setIconTint(colorStateList);
                        binding.upVoteParentButton.setIconTint(null);
                        binding.voteCountParentTextView.setText(String.valueOf(comment.getVoteDifference() - 2));
                        comment.setVoteDifference(comment.getVoteDifference() - 2);
                    }else{
                        binding.downVoteParentButton.setIconTint(colorStateList);
                        binding.voteCountParentTextView.setText(String.valueOf(comment.getVoteDifference() - 1));
                        comment.setVoteDifference(comment.getVoteDifference() - 1);
                    }
                    onDownVoteClickListener.onDownClick(comment);
                }
            });

            binding.showReplyParentTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onShowUpReplies.onShowUpReplies(comment);
                }
            });
        }
    }
}