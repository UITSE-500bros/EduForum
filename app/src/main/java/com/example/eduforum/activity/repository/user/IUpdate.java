package com.example.eduforum.activity.repository.user;

import com.example.eduforum.activity.model.user_manage.User;

public interface IUpdate {
    void onUpdateSuccess(User newUser);
    void onUpdateFailed(String message);
}
