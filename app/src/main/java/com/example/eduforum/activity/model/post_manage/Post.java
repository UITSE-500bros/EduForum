package com.example.eduforum.activity.model.post_manage;
import java.util.List;

public class Post extends PostingObject {
    private String postID;
    private String communityID;
    private String title;
    private Boolean isAnonymous;
    private Integer totalComment;
    private List<String> taggedUsers;
    private List<Category> category;


    public Post() {
        super();
    }

    public Post(String postID, String communityID, String title, String content, Boolean isAnonymous, String timeCreated, String lastModified, Creator creator, Integer totalUpVote, Integer totalDownVote, Integer voteDifference, Integer totalComment, List<String> image, List<String> taggedUsers, List<Category> category) {
        super(postID, communityID, content, timeCreated, lastModified, creator, totalUpVote, totalDownVote, voteDifference, image);
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

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
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
