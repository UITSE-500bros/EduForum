package com.example.eduforum.activity.repository.comment.dto;

import com.example.eduforum.activity.model.post_manage.Comment;
import com.example.eduforum.activity.model.post_manage.Creator;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCommentDTO {
    private String commentID;
    private String postID;
    private String communityID;
    private String content;
    private String replyCommentID;
    private Creator creator;
    private List<String> downloadImage;


    public AddCommentDTO() {
    }
    public AddCommentDTO(Comment comment) {
        this.postID = comment.getPostID();
        this.communityID = comment.getCommunityID();
        this.content = comment.getContent();
        this.replyCommentID = comment.getReplyCommentID();
        this.creator = comment.getCreator();
        this.downloadImage = comment.getDownloadImage();
        this.commentID = comment.getCommentID();
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
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

    public Map<String, Object> convertToDataObject() {
        Map<String, Object> data = new HashMap<>();
        data.put("communityID", communityID);
        data.put("commentID", commentID);
        data.put("postID", postID);
        data.put("content", content);
        if (replyCommentID != null) data.put("replyCommentID", replyCommentID);
        data.put("creator", creator.convertToDataObject());
        if (downloadImage != null) {
            data.put("downloadImage", downloadImage);
        } else data.put("downloadImage", new ArrayList<String>());
        return data;
    }
}
