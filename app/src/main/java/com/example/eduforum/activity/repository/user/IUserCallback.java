package com.example.eduforum.activity.repository.user;

import com.example.eduforum.activity.model.user_manage.User;

public interface IUserCallback {
    void onGetUserSuccess(User user);
    void onGetUserFailure(String errorMsg);
}
