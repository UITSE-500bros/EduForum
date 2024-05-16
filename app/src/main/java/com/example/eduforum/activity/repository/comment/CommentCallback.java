package com.example.eduforum.activity.repository.comment;

import com.example.eduforum.activity.model.post_manage.Comment;

import java.util.List;

public abstract class CommentCallback {
    public abstract void onCreateSuccess(Comment comments);
    public abstract void onFailure(String errorMsg);

    public abstract void onInitialLoadSuccess(List<Comment> comments);

    public abstract void onLoadRepliesSuccess(List<Comment> comments);

    public abstract void onDeleteSuccess();

    public abstract void onUpdateSuccess(Comment comment);

    public abstract void onGetVoteStatusSuccess(int voteType); // 0: no vote, 1: upvote, -1: downvote
}
