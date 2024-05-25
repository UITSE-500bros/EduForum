package com.example.eduforum.activity.ui.main.fragment;

import android.net.Uri;

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

    public CreateCommunityViewState(String name, String description, String category, Uri commuAvt, String communityID) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.commuAvt = commuAvt;
        this.communityID = communityID;
        this.isDialogClosed = false;
        this.errorMessage = null;
    }
    public CreateCommunityViewState() {
        this.isDialogClosed = false;
        this.errorMessage = null;
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
