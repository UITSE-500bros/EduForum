package com.example.eduforum.activity.ui.community.viewstate;

import android.net.Uri;

import com.example.eduforum.activity.model.post_manage.Category;
import com.example.eduforum.activity.model.post_manage.Creator;
import com.example.eduforum.activity.model.post_manage.Post;
import com.example.eduforum.activity.model.post_manage.PostCategory;
import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.activity.ui.main.fragment.CreateCommunityViewState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PostViewState implements Serializable {
    private String postId;
    private Creator creator;
    private CreateCommunityViewState community;
    private String title;
    private String content;
    private String date;
    private List<PostCategory> tags;
    private List<Uri> image;
    private List<String> pictures;
    private List<String> taggedUsers;
    private Boolean isAnonymous;

    private int totalComment;
    private int voteDiff;

    private String communityID;
    public PostViewState(String postId, Creator creator, String communityID, String title, String content, Boolean isAnonymous, String date, List<Uri> image, List<String> taggedUsers,  List<PostCategory> tags, int voteDiff, int totalComment) {
        this.postId = postId;
        this.creator = creator;
        this.communityID = communityID;
        this.title = title;
        if(content==null) this.content = "";
        else this.content = content;
        this.isAnonymous = isAnonymous;
        this.date = date;
        if(tags==null) this.tags = new ArrayList<>();
        else this.tags = tags;
        if(image==null) this.image = new ArrayList<>();
        else  this.image = image;
        if(taggedUsers==null) this.taggedUsers = new ArrayList<>();
        else this.taggedUsers = taggedUsers;
        this.voteDiff = voteDiff;
        this.totalComment = totalComment;
    }
    public PostViewState(String postId, Creator creator, CreateCommunityViewState community, String title, String content, Boolean isAnonymous, String date, List<Uri> image, List<String> taggedUsers,  List<PostCategory> tags, int voteDiff, int totalComment) {
        this.postId = postId;
        this.creator = creator;
        this.community = community;
        this.title = title;
        if(content==null) this.content = "";
        else this.content = content;
        this.isAnonymous = isAnonymous;
        this.date = date;
        if(tags==null) this.tags = new ArrayList<>();
        else this.tags = tags;
        if(image==null) this.image = new ArrayList<>();
         else  this.image = image;
        if(taggedUsers==null) this.taggedUsers = new ArrayList<>();
        else this.taggedUsers = taggedUsers;
        this.voteDiff = voteDiff;
        this.totalComment = totalComment;
    }
    public PostViewState(){
        this.postId = null;
        this.creator = null;
        this.community = null;
        this.title = null;
        this.content = "";
        this.date = null;
        this.tags = new ArrayList<>();
        this.pictures = new ArrayList<>();
        this.taggedUsers = new ArrayList<>();
        this.image = new ArrayList<>();
    }
    public List<Uri> getImage() {
        return image;
    }
    public List<String> getPictures() {
        return pictures;
    }
    public List<String> getTaggedUsers() {
        return taggedUsers;
    }
    public String getPostId() {
        return postId;
    }
    public Boolean getIsAnonymous() {
        return isAnonymous;
    }
    public Creator getCreator() {
        return creator;
    }
    public CreateCommunityViewState getCommunity() {
        return community;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public String getDate() {
        return date;
    }
    public List<PostCategory> getTags() {
        return tags;
    }
    public void setPostId(String postId) {
        this.postId = postId;
    }
    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }
    public void setCreator(Creator creator) {
        this.creator = creator;
    }
    public void setCommunity(CreateCommunityViewState community) {
        this.community = community;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setTags(List<PostCategory> tags) {
        this.tags = tags;
    }
    public void setAnonymous(Boolean anonymous) {
        isAnonymous = anonymous;
    }
    public void setImage(List<Uri> image) {
        this.image = image;
    }
    public void setTaggedUsers(List<String> taggedUsers) {
        this.taggedUsers = taggedUsers;
    }

    public int getVoteDifference() {
        return voteDiff;
    }

    public int getTotalComment() {
        return totalComment;
    }

    public void setVoteDifference(int i) {
        this.voteDiff = i;
    }

    public Boolean getAnonymous() {
        return isAnonymous;
    }

    public void setTotalComment(int totalComment) {
        this.totalComment = totalComment;
    }

    public int getVoteDiff() {
        return voteDiff;
    }

    public void setVoteDiff(int voteDiff) {
        this.voteDiff = voteDiff;
    }

    public String getCommunityID() {
        return communityID;
    }

    public void setCommunityID(String communityID) {
        this.communityID = communityID;
    }
}
