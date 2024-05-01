package com.example.eduforum.activity.ui.main.fragment;

public class JoinCommunityViewState {
    private String communityId;
    public JoinCommunityViewState(String communityId) {
        this.communityId = communityId;
    }
    public JoinCommunityViewState() {}
    public String getCommunityId() {
        return communityId;
    }
    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }
}
