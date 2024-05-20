package com.example.eduforum.activity.model.post_manage;

import android.net.Uri;

import com.google.firebase.Timestamp;

import java.util.List;

public class Comment extends PostingObject {
    private String commentID;
    private String replyCommentID;
    private Integer totalReply;

    public Comment() {
        super();
    }

    public Comment(String commentID, String postID, String communityID, String parentID, String content, Timestamp timeCreated, Timestamp lastModified, Creator creator, Integer totalUpVote, Integer totalDownVote, Integer voteDifference, List<Uri> image) {
        super(postID, communityID, content, timeCreated, lastModified, creator, totalUpVote, totalDownVote, voteDifference, image);
        this.commentID = commentID;
        this.replyCommentID = parentID;
    }

    public Comment(String communityID, String postID, String content, Timestamp timeCreated, Timestamp lastModified, Creator creator, Integer totalUpVote, Integer totalDownVote, Integer voteDifference, List<Uri> image, String commentID, String replyCommentID, String postID1, String communityID1, Integer totalReply) {
        super(communityID, postID, content, timeCreated, lastModified, creator, totalUpVote, totalDownVote, voteDifference, image);
        this.commentID = commentID;
        this.replyCommentID = replyCommentID;
        this.postID = postID1;
        this.communityID = communityID1;
        this.totalReply = totalReply;
    }

    public Integer getTotalReply() {
        return totalReply;
    }

    public void setTotalReply(Integer totalReply) {
        this.totalReply = totalReply;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }


    public String getReplyCommentID() {
        return replyCommentID;
    }

    public void setReplyCommentID(String replyCommentID) {
        this.replyCommentID = replyCommentID;
    }
}
