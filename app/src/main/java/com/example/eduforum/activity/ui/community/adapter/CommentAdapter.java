package com.example.eduforum.activity.ui.community.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eduforum.R;
import com.example.eduforum.activity.model.post_manage.Comment;
import com.example.eduforum.activity.ui.community.PostDetailActivity;
import com.example.eduforum.activity.ui.community.viewstate.CommentViewState;
import com.example.eduforum.activity.ui.main.adapter.ChildCommentAdapter;
import com.example.eduforum.databinding.ItemChildCommentBinding;
import com.example.eduforum.databinding.ItemListCommentBinding;
import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{
    private Context context;
    private static List<CommentViewState> commentList;

    private static List<CommentViewState> childCommentList;

    public CommentAdapter(Context context, List<CommentViewState> commentList, List<CommentViewState> childCommentList) {
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

    }
    public void setCommentList(List<CommentViewState> commentList) {
        if (commentList != null) {
            this.commentList = commentList;
            notifyDataSetChanged();
        } else {
            this.commentList = new ArrayList<>();
        }
    }

    public static void setChildCommentList(List<CommentViewState> childCommentList) {
        CommentAdapter.childCommentList = childCommentList;
    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemListCommentBinding itemListCommentBinding = ItemListCommentBinding.inflate(layoutInflater, parent, false);
        return new CommentViewHolder(itemListCommentBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentViewState comment = commentList.get(position);
        holder.bind(comment);
        ChildCommentAdapter childCommentAdapter = new ChildCommentAdapter(childCommentList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        holder.recyclerView.setAdapter(childCommentAdapter);

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        ItemListCommentBinding binding;

        ItemChildCommentBinding bindingChild;

        RecyclerView recyclerView;


        public CommentViewHolder(ItemListCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            recyclerView = binding.nestedRecyclerView;
            binding.replyParentTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Get parent commnent
                    CommentViewState comment = commentList.get(getAdapterPosition());
                    // Create a new instance of CommentViewState (or whatever class represents your comment)
                    EditText replyEditText = ((PostDetailActivity) v.getContext()).findViewById(R.id.commentEditText);
                    replyEditText.setText("@RauCha ");

                    // Create a new instance of CommentViewState (or whatever class represents your comment)
                    isReply = true;
                    bindingChild = ItemChildCommentBinding.inflate(LayoutInflater.from(v.getContext()));
                    bindingChild.contentChildTextView.setText(comment.getContent());

                }
            });



        }
        public void bind(CommentViewState comment) {
            binding.contentNotiParentTextView.setText(comment.getContent());
        }
    }
}
