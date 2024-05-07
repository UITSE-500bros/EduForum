package com.example.eduforum.activity.repository.post;
import com.example.eduforum.activity.model.post_manage.Post;

import java.util.List;
public interface IPostCallback {
    void onGetPostSuccess(List<Post> posts);
    void onGetPostFailure(String errorMsg);

    void onAddPostFailure(String errorMsg);

    void onAddPostSuccess();

    void onEditPostSuccess();

    void onEditPostFailure(String errorMsg);

    void onQueryPostError(String errorMsg);

    void onQueryPostSuccess(List<Post> queryPostResults);
}
