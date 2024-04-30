package com.example.eduforum.activity.model.community_manage;

import android.net.Uri;

import java.io.File;

public class Community {
    private String communityId;
    private String communityName;

    private Uri profileImage;
    private String createdDate;
    private String description;//todo
    private String department;


    public Community() {
    }

    public Community(String communityName, String createdDate, String department, Uri profileImg) {
        this.communityName = communityName;
        this.createdDate = createdDate;
        this.department = department;
        this.profileImage = profileImg;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public Uri getProfileImage() {
        return profileImage;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public void setProfileImage(Uri profileImage) {
        this.profileImage = profileImage;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
