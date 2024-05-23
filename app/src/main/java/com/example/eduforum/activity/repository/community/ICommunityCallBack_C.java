package com.example.eduforum.activity.repository.community;

import com.example.eduforum.activity.model.community_manage.Community;
import com.example.eduforum.activity.model.user_manage.User;

import java.util.List;

public interface ICommunityCallBack_C {
    void onRoleAdmin(List<Community> communityList);

    void onGetCommunityMemberSuccess(List<User> userList, List<User> adminList);
}
