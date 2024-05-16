package com.example.eduforum.activity.model.post_manage;

import java.util.List;

public class Comment extends PostingObject {
    private String commentID;
    private String replyCommentID;
    private String postID;
    private String communityID;

    public Comment() {
        super();
    }

    public Comment(String commentID, String postID, String communityID, String parentID, String content, String timeCreated, String lastModified, Creator creator, Integer totalUpVote, Integer totalDownVote, Integer voteDifference, List<String> image) {
        super(postID, communityID, content, timeCreated, lastModified, creator, totalUpVote, totalDownVote, voteDifference, image);
        this.commentID = commentID;
        this.replyCommentID = parentID;
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
