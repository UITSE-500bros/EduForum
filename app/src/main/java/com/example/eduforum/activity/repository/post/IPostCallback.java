package com.example.eduforum.activity.repository.post;
import com.example.eduforum.activity.model.post_manage.Post;

import java.util.List;
public interface IPostCallback {
    void onGetPostSuccess(List<Post> posts);
    void onGetPostFailure(String errorMsg);

    void onAddPostFailure(String errorMsg);

    void onAddPostSuccess(Post newPost);

    void onEditPostSuccess();

    void onEditPostFailure(String errorMsg);

    void onQueryPostError(String errorMsg);

    void onQueryPostSuccess(List<Post> queryPostResults);

    void onDeletePostSuccess();

    void onDeletePostError(String errorMsg);

    void onSubscriptionSuccess();

    void onSubscriptionError(String errorMsg);

    void onBookmarkError(String errorMsg);

    void onBookmarkSuccess();

    void onGetVoteStatusSuccess(int voteType);  // 0: no vote, 1: upvote, -1: downvote
}
