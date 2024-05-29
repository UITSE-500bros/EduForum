package com.example.eduforum.activity.repository.community.dto;

import com.example.eduforum.activity.model.community_manage.Community;

public interface IUpdateCommunityCallback {
    void onUpdateCommunitySuccess(Community newCommunity);
    void onUpdateCommunityFailure(String errorMsg);
}
