package com.example.eduforum.activity.model.post_manage;

import java.util.List;

public class Comment extends PostingObject {
    private String commentID;
    private String replyCommentID;
    private String totalReply;

    public Comment() {
        super();
    }

    public Comment(String commentID, String postID, String communityID, String parentID, String content, String timeCreated, String lastModified, Creator creator, Integer totalUpVote, Integer totalDownVote, Integer voteDifference, List<String> image) {
        super(postID, communityID, content, timeCreated, lastModified, creator, totalUpVote, totalDownVote, voteDifference, image);
        this.commentID = commentID;
        this.replyCommentID = parentID;
    }

    public Comment(String communityID, String postID, String content, String timeCreated, String lastModified, Creator creator, Integer totalUpVote, Integer totalDownVote, Integer voteDifference, List<String> image, String commentID, String replyCommentID, String postID1, String communityID1, String totalReply) {
        super(communityID, postID, content, timeCreated, lastModified, creator, totalUpVote, totalDownVote, voteDifference, image);
        this.commentID = commentID;
        this.replyCommentID = replyCommentID;
        this.postID = postID1;
        this.communityID = communityID1;
        this.totalReply = totalReply;
    }

    public String getTotalReply() {
        return totalReply;
    }

    public void setTotalReply(String totalReply) {
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
