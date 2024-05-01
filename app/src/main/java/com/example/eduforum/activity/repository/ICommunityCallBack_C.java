package com.example.eduforum.activity.repository;

import com.example.eduforum.activity.model.community_manage.Community;

import java.util.List;

public interface ICommunityCallBack_C {
    void onRoleAdmin(List<Community> communityList);
}
