package com.example.eduforum.activity.model.PostModel;
import java.util.List;

public class Post {
    private String postID;
    private String communityID;
    private String postContent;
    private Boolean isAnonymous;
    private String createTime;
    private Creator creator;
    private List<String> postImages;

    private Integer numOfVotes;

    private List<String> taggedUsers;

    private List<Category> categories;

    public Post() {
    }

    public Post(String postID, String communityID, String postContent, Boolean isAnonymous, String createTime, Creator creator, List<String> postImages, Integer numOfVotes, List<String> taggedUsers, List<Category> categories) {
        this.postID = postID;
        this.communityID = communityID;
        this.postContent = postContent;
        this.isAnonymous = isAnonymous;
        this.createTime = createTime;
        this.creator = creator;
        this.postImages = postImages;
        this.numOfVotes = numOfVotes;
        this.taggedUsers = taggedUsers;
        this.categories = categories;
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

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public Boolean getAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        isAnonymous = anonymous;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public List<String> getPostImages() {
        return postImages;
    }

    public void setPostImages(List<String> postImages) {
        this.postImages = postImages;
    }

    public Integer getNumOfVotes() {
        return numOfVotes;
    }

    public void setNumOfVotes(Integer numOfVotes) {
        this.numOfVotes = numOfVotes;
    }

    public List<String> getTaggedUsers() {
        return taggedUsers;
    }

    public void setTaggedUsers(List<String> taggedUsers) {
        this.taggedUsers = taggedUsers;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
