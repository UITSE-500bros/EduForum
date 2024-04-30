package com.example.eduforum.activity.repository;

public interface ICommunityCallBack {
    void onCommunitySuccess();
    void onCommunityFailure(String errorMsg);
    void onCreateCommunitySuccess();
    void onCreateCommunityFailure(String errorMsg);

}
