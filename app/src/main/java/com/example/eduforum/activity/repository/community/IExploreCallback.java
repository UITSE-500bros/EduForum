package com.example.eduforum.activity.repository.community;

import com.example.eduforum.activity.model.community_manage.Community;

import java.util.List;

public interface IExploreCallback {
    void onGetCommunitySuccess(List<Community> communities);
    void onGetCommunityFailure(String errorMsg);
}
