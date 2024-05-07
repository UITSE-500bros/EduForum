package com.example.eduforum.activity.model.community_manage;

import android.net.Uri;

public class CommunityMember {
    private String communityId;
    private String name;

    private Uri profileImage;
    private String createdDate;
    private String department;

    private Boolean isAdmin;

    public CommunityMember() {
    }

    public CommunityMember(String communityId, String name, Uri profileImage, String createdDate, String department, Boolean isAdmin) {
        this.communityId = communityId;
        this.name = name;
        this.profileImage = profileImage;
        this.createdDate = createdDate;
        this.department = department;
        this.isAdmin = isAdmin;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Uri profileImage) {
        this.profileImage = profileImage;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }


}
