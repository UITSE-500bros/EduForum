package com.example.eduforum.activity.ui.main.fragment;

import com.example.eduforum.activity.model.post_manage.Creator;

public class NotificationViewState {

    private String notificationID;
    private Creator triggerBy;
    private int type;
    private boolean isRead;
    private String date;

    private String content;
    private String communityName;

    private String postID;
    private String commentID;

    public NotificationViewState() {

    }

    public NotificationViewState(String notificationID, Creator triggerBy, int type, boolean isRead, String date, String communityName, String postID, String commentID) {
        this.notificationID = notificationID;
        this.triggerBy = triggerBy;
        this.type = type;
        this.isRead = isRead;
        this.date = date;

        if (communityName==null) this.communityName = "";
        else this.communityName = communityName;

        if (postID==null) this.postID = "";
        else this.postID = postID;

        if(commentID==null) this.commentID = "";
        else this.commentID = commentID;

        if (postID==null) this.postID = "";
        else this.postID = postID;

        if (communityName==null) this.communityName = "";
        else this.communityName = communityName;
    }

    public String getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(String notificationID) {
        this.notificationID = notificationID;
    }

    public Creator getTriggerBy() {
        return triggerBy;
    }

    public void setTriggerBy(Creator triggerBy) {
        this.triggerBy = triggerBy;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }
}
