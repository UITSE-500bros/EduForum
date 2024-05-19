package com.example.eduforum.activity.model.post_manage;
import android.net.Uri;

import com.google.firebase.Timestamp;

import java.util.List;

public class Post extends PostingObject {
    private String title;
    private Boolean isAnonymous;
    private Integer totalComment;
    private List<String> taggedUsers;
    private List<PostCategory> category;


    public Post() {
        super();
    }

    public Post(String postID, String communityID, String title, String content, Boolean isAnonymous, Timestamp timeCreated, Timestamp lastModified, Creator creator, Integer totalUpVote, Integer totalDownVote, Integer voteDifference, Integer totalComment, List<Uri> image, List<String> taggedUsers, List<PostCategory> category) {
        super(communityID, postID, content, timeCreated, lastModified, creator, totalUpVote, totalDownVote, voteDifference, image);
        this.title = title;
        this.isAnonymous = isAnonymous;
        this.totalComment = totalComment;
        this.taggedUsers = taggedUsers;
        this.category = category;
    }

    public Post(String communityID, String postID, String content, Timestamp timeCreated, Timestamp lastModified, Creator creator, Integer totalUpVote, Integer totalDownVote, Integer voteDifference, List<Uri> image, List<String> downloadImage, String title, Boolean isAnonymous, Integer totalComment, List<String> taggedUsers, List<PostCategory> category) {
        super(communityID, postID, content, timeCreated, lastModified, creator, totalUpVote, totalDownVote, voteDifference, image, downloadImage);
        this.title = title;
        this.isAnonymous = isAnonymous;
        this.totalComment = totalComment;
        this.taggedUsers = taggedUsers;
        this.category = category;
    }

    public Boolean getAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        isAnonymous = anonymous;
    }

    public List<String> getTaggedUsers() {
        return taggedUsers;
    }

    public void setTaggedUsers(List<String> taggedUsers) {
        this.taggedUsers = taggedUsers;
    }

    public List<PostCategory> getCategory() {
        return category;
    }

    public void setCategory(List<PostCategory> category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(Integer totalComment) {
        this.totalComment = totalComment;
    }
}
