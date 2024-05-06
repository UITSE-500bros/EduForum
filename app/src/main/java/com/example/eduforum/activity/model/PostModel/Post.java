package com.example.eduforum.activity.model.PostModel;
import java.util.List;
class Creator{
    public String creatorID;
    public String creatorName;
    public String creatorDepartment;
    public String creatorImage;

    public Creator() {
    }

    public Creator(String creatorID, String creatorName, String creatorDepartment, String creatorImage) {
        this.creatorID = creatorID;
        this.creatorName = creatorName;
        this.creatorDepartment = creatorDepartment;
        this.creatorImage = creatorImage;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorDepartment() {
        return creatorDepartment;
    }

    public void setCreatorDepartment(String creatorDepartment) {
        this.creatorDepartment = creatorDepartment;
    }

    public String getCreatorImage() {
        return creatorImage;
    }

    public void setCreatorImage(String creatorImage) {
        this.creatorImage = creatorImage;
    }
}

class Category {
    public String categoryID;
    public String title;
    public Boolean isAnnouncement;

    public Category() {
    }

    public Category(String categoryID, String title, Boolean isAnnouncement) {
        this.categoryID = categoryID;
        this.title = title;
        this.isAnnouncement = isAnnouncement;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getAnnouncement() {
        return isAnnouncement;
    }

    public void setAnnouncement(Boolean announcement) {
        isAnnouncement = announcement;
    }
}

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
