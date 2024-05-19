package com.example.eduforum.activity.repository.post.dto;

import com.example.eduforum.activity.model.post_manage.Creator;
import com.example.eduforum.activity.model.post_manage.Post;
import com.example.eduforum.activity.model.post_manage.PostCategory;

import java.sql.Timestamp;
import java.util.List;

public class AddPostDTO {
    private String communityID;
    private String title;
    private String content;
    private Creator creator;
    private List<PostCategory> category;
    private Boolean isAnonymous;
    private List<String> downloadImage;
    private Integer totalComment;
    private Integer totalUpVote;
    private Integer totalDownVote;
    private Integer voteDifference;
    private Timestamp timeCreated;
    private Timestamp lastModified;


    public AddPostDTO(Post post) {
        this.communityID = post.getCommunityID();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.category = post.getCategory();
        this.creator = post.getCreator();
        this.isAnonymous = post.getAnonymous();
        this.downloadImage = post.getDownloadImage();
        this.totalComment = 0;
        this.totalUpVote = 0;
        this.totalDownVote = 0;
        this.voteDifference = 0;
        this.timeCreated = null;
        this.lastModified = null;
    }

    public String getCommunityID() {
        return communityID;
    }

    public void setCommunityID(String communityID) {
        this.communityID = communityID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public List<PostCategory> getCategory() {
        return category;
    }

    public void setCategory(List<PostCategory> category) {
        this.category = category;
    }

    public Boolean getAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        isAnonymous = anonymous;
    }

    public List<String> getDownloadImage() {
        return downloadImage;
    }

    public void setDownloadImage(List<String> downloadImage) {
        this.downloadImage = downloadImage;
    }

    public Integer getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(Integer totalComment) {
        this.totalComment = totalComment;
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

