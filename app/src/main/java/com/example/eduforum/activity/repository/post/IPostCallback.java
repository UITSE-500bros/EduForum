package com.example.eduforum.activity.repository.post;
import com.example.eduforum.activity.model.post_manage.Post;

import java.util.List;
public interface IPostCallback {
    void onGetPostSuccess(List<Post> posts);
    void onGetPostFailure(String errorMsg);

    void onAddPostFailure(String errorMsg);

    void onAddPostSuccess();
}
