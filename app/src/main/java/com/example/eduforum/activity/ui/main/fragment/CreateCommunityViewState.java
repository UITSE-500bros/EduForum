package com.example.eduforum.activity.ui.main.fragment;

import android.net.Uri;

import com.example.eduforum.activity.model.community_manage.Community;

import java.io.Serializable;

public class CreateCommunityViewState implements Serializable {
    String name;
    String description;
    String category;
    Uri commuAvt;
    String communityProfilePicture;
    String communityID;
    Boolean isDialogClosed;
    String errorMessage;
    Integer unReadposts;
    Integer totalPosts;
    Integer totalMembers;
    Boolean isPublic;

    public CreateCommunityViewState(String name, String description, String category, Uri commuAvt, String communityID) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.commuAvt = commuAvt;
        this.communityID = communityID;
        this.isDialogClosed = false;
        this.errorMessage = null;
        this.unReadposts = 0;
        this.totalMembers = 0;
        this.totalPosts = 0;
    }
    public CreateCommunityViewState() {
        this.isDialogClosed = false;
        this.errorMessage = null;
        this.unReadposts = 0;
        this.totalMembers = 0;
        this.totalPosts = 0;
    }

    public CreateCommunityViewState(Community community) {
        this.name = community.getName();
        this.description = community.getDescription();
        this.category = community.getDepartment();
        this.communityProfilePicture = community.getProfilePicture();
        this.communityID = community.getCommunityId();
        if(community.getTotalNewPost()!=null) this.unReadposts = community.getTotalNewPost();
        else this.unReadposts = 0;
        if(community.getTotalPost()!=null) this.totalPosts = community.getTotalPost();
        else this.totalPosts = 0;
        this.totalMembers = community.getUserList().size();
    }
    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }
    public Boolean getIsPublic() {
        if(isPublic==null){
            isPublic = true;
        }
        return isPublic;
    }
    public void setUnReadposts(Integer unReadposts) {
        this.unReadposts = unReadposts;
    }
    public Integer getUnReadposts() {
        return unReadposts;
    }
    public void setTotalPosts(Integer totalPosts) {
        this.totalPosts = totalPosts;
    }
    public Integer getTotalPosts() {
        return totalPosts;
    }
    public void setTotalMembers(Integer totalMembers) {
        this.totalMembers = totalMembers;
    }
    public Integer getTotalMembers() {
        return totalMembers;
    }
    public void setCommunityProfilePicture(String communityProfilePicture) {
        this.communityProfilePicture = communityProfilePicture;
    }
    public String getCommunityProfilePicture() {
        return communityProfilePicture;
    }

    public Uri getCommuAvt() {
        return commuAvt;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        if(this.description==null) this.description = "";
        return description;
    }
    public String getCategory() {
        return category;
    }
    public String getCommunityID() {return communityID;}
    public Boolean getIsDialogClosed() {
        return isDialogClosed;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setCommuAvt(Uri commuAvt) {
        this.commuAvt = commuAvt;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setCommunityID(String communityID) {this.communityID = communityID;}
    public void setIsDialogClosed(Boolean isDialogClosed) {
        this.isDialogClosed = isDialogClosed;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
