package com.example.eduforum.activity.model.post_manage;

import java.util.List;

public class Comment extends PostingObject {
    private String commentID;
    private Integer depth;
    private String parentID;

    public Comment() {
        super();
    }

    public Comment(String commentID, Integer depth, String parentID, String content, String timeCreated, String lastModified, Creator creator, Integer totalUpVote, Integer totalDownVote, List<String> image) {
        super(content, timeCreated, lastModified, creator, totalUpVote, totalDownVote, image);
        this.commentID = commentID;
        this.depth = depth;
        this.parentID = parentID;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }
}
