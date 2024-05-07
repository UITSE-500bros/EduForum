package com.example.eduforum.activity.ui.main.fragment;

public class JoinCommunityViewState {
    private String communityId;
    private Boolean isDialogClosed;
    private String errorMessage;
    public JoinCommunityViewState(String communityId) {
        this.communityId = communityId;
        this.isDialogClosed = false;
        this.errorMessage = null;
    }
    public JoinCommunityViewState() {
        this.isDialogClosed = false;
        this.errorMessage = null;
    }
    public String getCommunityId() {
        return communityId;
    }
    public Boolean getIsDialogClosed() {
        return isDialogClosed;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }
    public void setIsDialogClosed(Boolean isDialogClosed) {
        this.isDialogClosed = isDialogClosed;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
