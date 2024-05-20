package com.example.eduforum.activity.repository.community;

import com.example.eduforum.activity.model.user_manage.User;

import java.util.List;

public interface IGetMemberApproval {
    void onGetMemberApprovalSuccess(List<User> users);
    void onGetMemberApprovalFailure(String errorMsg);
}
