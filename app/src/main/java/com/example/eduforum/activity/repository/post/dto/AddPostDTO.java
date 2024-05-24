package com.example.eduforum.activity.repository.post.dto;

import com.example.eduforum.activity.model.post_manage.Creator;
import com.example.eduforum.activity.model.post_manage.Post;
import com.example.eduforum.activity.model.post_manage.PostCategory;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class AddPostDTO {
    private String postID;
    private String communityID;
    private String title;
    private String content;
    private Creator creator;
    private List<PostCategory> category;
    private Boolean isAnonymous;
    private List<String> downloadImage;



    public AddPostDTO(Post post) {
        this.postID = post.getPostID();
        this.communityID = post.getCommunityID();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.category = post.getCategory();
        this.creator = post.getCreator();
        this.downloadImage = post.getDownloadImage();
        this.isAnonymous = (post.getAnonymous() != null && post.getAnonymous());
    }

    public Map<String, Object> convertToDataObject() {
        Map<String, Object> data = new java.util.HashMap<>();
        data.put("postID", postID);
        data.put("communityID", communityID);
        data.put("title", title);
        data.put("content", content);
        data.put("creator", creator.convertToDataObject());
        // convert the category list to a list of data object
        List<Map<String, Object>> categoryList = new java.util.ArrayList<>();
        for (PostCategory postCategory : this.category) {
            categoryList.add(postCategory.convertToDataObject());
        }
        data.put("category", categoryList);
        data.put("isAnonymous", isAnonymous);
        data.put("downloadImage", downloadImage);
        return data;
    }

    public String getCommunityID() { return communityID; }

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

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }
}

