package com.example.eduforum.activity.model.post_manage;
import java.util.List;

public class Post {
    private String postID;
    private String communityID;
    private String title;
    private String content;
    private Boolean isAnonymous;
    private String timeCreated;
    private String lastModified;
    private Creator creator;
    private Integer totalUpVote;
    private Integer totalDownVote;
    private Integer totalComment;
    private List<String> image;
    private List<String> taggedUsers;

    private List<Category> category;


    public Post() {}

    public Post(String postID, String communityID, String title, String content, Boolean isAnonymous, String timeCreated, String lastModified, Creator creator, Integer totalUpVote, Integer totalDownVote, Integer totalComment, List<String> image, List<String> taggedUsers, List<Category> category) {
        this.postID = postID;
        this.communityID = communityID;
        this.title = title;
        this.content = content;
        this.isAnonymous = isAnonymous;
        this.timeCreated = timeCreated;
        this.lastModified = lastModified;
        this.creator = creator;
        this.totalUpVote = totalUpVote;
        this.totalDownVote = totalDownVote;
        this.totalComment = totalComment;
        this.image = image;
        this.taggedUsers = taggedUsers;
        this.category = category;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        isAnonymous = anonymous;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
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

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public List<String> getTaggedUsers() {
        return taggedUsers;
    }

    public void setTaggedUsers(List<String> taggedUsers) {
        this.taggedUsers = taggedUsers;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
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

    public Integer getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(Integer totalComment) {
        this.totalComment = totalComment;
    }
}
