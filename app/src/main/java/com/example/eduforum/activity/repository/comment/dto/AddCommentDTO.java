package com.example.eduforum.activity.repository.comment.dto;

import com.example.eduforum.activity.model.post_manage.Comment;
import com.example.eduforum.activity.model.post_manage.Creator;
import com.google.firebase.Timestamp;

import java.util.List;

public class AddCommentDTO {
    private String postID;
    private String communityID;
    private String content;
    private String replyCommentID;
    private Creator creator;
    private List<String> downloadImage;
    private Integer totalUpVote;
    private Integer totalDownVote;
    private Integer voteDifference;
    private Integer totalReply;
    private Timestamp timeCreated;
    private Timestamp lastModified;

    public AddCommentDTO() {
    }
    public AddCommentDTO(Comment comment) {
        this.postID = comment.getPostID();
        this.communityID = comment.getCommunityID();
        this.content = comment.getContent();
        this.replyCommentID = comment.getReplyCommentID();
        this.creator = comment.getCreator();
        this.downloadImage = comment.getDownloadImage();
        this.totalUpVote = 0;
        this.totalDownVote = 0;
        this.voteDifference = 0;
        this.totalReply = 0;
        this.timeCreated = null;
        this.lastModified = null;
    }

    public Integer getVoteDifference() {
        return voteDifference;
    }

    public void setVoteDifference(Integer voteDifference) {
        this.voteDifference = voteDifference;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getCommunityID() {
        return communityID;
    }

    public void setCommunityID(String communityID) {
        this.communityID = communityID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplyCommentID() {
        return replyCommentID;
    }

    public void setReplyCommentID(String replyCommentID) {
        this.replyCommentID = replyCommentID;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public List<String> getDownloadImage() {
        return downloadImage;
    }

    public void setDownloadImage(List<String> downloadImage) {
        this.downloadImage = downloadImage;
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

    public Integer getTotalReply() {
        return totalReply;
    }

    public void setTotalReply(Integer totalReply) {
        this.totalReply = totalReply;
    }

    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }
}
