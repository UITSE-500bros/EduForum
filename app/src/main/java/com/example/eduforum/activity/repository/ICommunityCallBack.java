package com.example.eduforum.activity.repository;

import com.example.eduforum.activity.model.community_manage.Community;

import java.util.List;

public interface ICommunityCallBack {

    void onCreateCommunitySuccess(String communityId);
    void onCreateCommunityFailure(String errorMsg);

    void onRoleAdmin(List<Community> communityList);



}
