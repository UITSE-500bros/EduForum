package com.example.eduforum.activity.model.noti_manage;

import com.example.eduforum.activity.model.community_manage.Community;
import com.example.eduforum.activity.model.post_manage.Creator;
import com.google.firebase.Timestamp;

public class Notification {
    private String notificationID;
    private Creator triggerBy;
    private int type;
    private Community community;

    private boolean isRead;
    private Timestamp timeStamp;
    private String commentID;

    public Notification() {

    }


    public void setNotificationID(String notificationID) {
        this.notificationID = notificationID;
    }

    public void setTriggerBy(Creator triggerBy) {
        this.triggerBy = triggerBy;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getNotificationID() {
        return notificationID;
    }

    public Creator getTriggerBy() {
        return triggerBy;
    }

    public int getType() {
        return type;
    }

    public Community getCommunity() {
        return community;
    }

    public boolean isRead() {
        return isRead;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public String getCommentID() {
        return commentID;
    }
}
