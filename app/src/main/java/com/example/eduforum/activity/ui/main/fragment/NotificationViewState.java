package com.example.eduforum.activity.ui.main.fragment;

import com.example.eduforum.activity.model.noti_manage.Sender;
import com.example.eduforum.activity.model.post_manage.Creator;

public class NotificationViewState {

    private String notificationID;
    private Sender triggerBy;
    private int type;
    private boolean isRead;
    private String date;
    private String communityID;
    private String content;
    private String communityName;

    private String postID;
    private String commentID;


    public NotificationViewState() {

    }

    public NotificationViewState(String notificationID, Sender triggerBy, int type, boolean isRead, String date, String communityName, String postID, String commentID, String communityID) {
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
        this.communityID = communityID;
    }


    public String getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(String notificationID) {
        this.notificationID = notificationID;
    }

    public Sender getTriggerBy() {
        return triggerBy;
    }

    public void setTriggerBy(Sender triggerBy) {
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

    public String getCommunityID() {
        return communityID;
    }
}
