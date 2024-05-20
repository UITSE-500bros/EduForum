package com.example.eduforum.activity.ui.community.viewstate;

import android.net.Uri;

import com.example.eduforum.activity.model.post_manage.Creator;

import java.util.List;

public class CommentViewState {
    private CommentViewState commentViewState;
    private String commentID;
    private String communityID;
    private String content;
    private String timeCreated;
    private Creator creator;
    private Integer totalUpVote;
    private Integer totalDownVote;
    private Integer voteDifference;
    private String lastModified;
    private List<Uri> image;
    private String replyCommentID;
    private Integer totalReply;

    public CommentViewState(String commentID, String content, String timeCreated, Creator creator, Integer totalUpVote, Integer totalDownVote, Integer voteDifference, String lastModified, List<Uri> image, String replyCommentID, Integer totalReply) {
        this.commentID = commentID;
        this.content = content;
        this.timeCreated = timeCreated;
        this.creator = creator;
        this.totalUpVote = totalUpVote;
        this.totalDownVote = totalDownVote;
        this.voteDifference = voteDifference;
        this.lastModified = lastModified;
        this.image = image;
        this.replyCommentID = replyCommentID;
        this.totalReply = totalReply;
    }

    public CommentViewState getCommentViewState() {
        return commentViewState;
    }

    public void setCommentViewState(CommentViewState commentViewState) {
        this.commentViewState = commentViewState;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public Integer getTotalUpVote() {
        return totalUpVote;
    }

    public void setTotalUpVote(Integer totalUpVote) {
        this.totalUpVote = totalUpVote;
    }

    public Integer getTotalDownVote() {
        return totalDownVote;
    }

    public void setTotalDownVote(Integer totalDownVote) {
        this.totalDownVote = totalDownVote;
    }

    public Integer getVoteDifference() {
        return voteDifference;
    }

    public void setVoteDifference(Integer voteDifference) {
        this.voteDifference = voteDifference;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public List<Uri> getImage() {
        return image;
    }

    public void setImage(List<Uri> image) {
        this.image = image;
    }

    public String getReplyCommentID() {
        return replyCommentID;
    }

    public void setReplyCommentID(String replyCommentID) {
        this.replyCommentID = replyCommentID;
    }

    public Integer getTotalReply() {
        return totalReply;
    }

    public void setTotalReply(Integer totalReply) {
        this.totalReply = totalReply;
    }
}
