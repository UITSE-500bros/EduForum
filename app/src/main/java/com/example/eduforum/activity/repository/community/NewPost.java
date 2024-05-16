package com.example.eduforum.activity.repository.community;

public class NewPost {
    private String userID;
    private String communityID;
    private Integer totalNewPost;

    public NewPost(String userID, String communityID, Integer totalNewPost) {
        this.userID = userID;
        this.communityID = communityID;
        this.totalNewPost = totalNewPost;
    }

    public NewPost() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCommunityID() {
        return communityID;
    }

    public void setCommunityID(String communityID) {
        this.communityID = communityID;
    }

    public Integer getTotalNewPost() {
        return totalNewPost;
    }

    public void setTotalNewPost(Integer totalNewPost) {
        this.totalNewPost = totalNewPost;
    }
}
