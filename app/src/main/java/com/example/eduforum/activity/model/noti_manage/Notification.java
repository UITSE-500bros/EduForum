package com.example.eduforum.activity.model.noti_manage;

import com.example.eduforum.activity.model.community_manage.Community;
import com.example.eduforum.activity.model.post_manage.Creator;
import com.google.firebase.Timestamp;

public class Notification {
    private String notificationID;
    private Sender triggeredBy;
    private int type;
    private CommunityNotification community;
    private PostNotification post;
    private boolean isRead;
    private Timestamp timestamp;
    private String commentID;

    public Notification() {

    }

    public PostNotification getPost() {
        return post;
    }

    public void setPost(PostNotification post) {
        this.post = post;
    }

    public void setNotificationID(String notificationID) {
        this.notificationID = notificationID;
    }

    public void setTriggeredBy(Sender triggeredBy) {
        this.triggeredBy = triggeredBy;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCommunity(CommunityNotification community) {
        this.community = community;
    }

    public void setIsRead(boolean read) {
        this.isRead = read;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getNotificationID() {
        return notificationID;
    }

    public Sender getTriggeredBy() {
        return triggeredBy;
    }

    public int getType() {
        return type;
    }

    public CommunityNotification getCommunity() {
        return community;
    }

    public boolean isRead() {
        return isRead;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getCommentID() {
        return commentID;
    }
}
