package com.example.eduforum.activity.model.community_manage;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class Community {
    private String communityId;
    private String name;

    private List<String> adminList = new ArrayList<>();
    private Uri profileImage;
    private String createdDate;
    private String description;
    private String department;


    public Community() {
    }


    public Community(String name, String createdDate, String department, Uri profileImg, String description, List<String> adminList) {
        this.name = name;
        this.createdDate = createdDate;
        this.department = department;
        this.profileImage = profileImg;
        this.description = description;
        this.adminList = adminList;
    }

    public List<String> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<String> adminList) {
        this.adminList = adminList;
    }


    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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

    public void setName(String name) {
        this.name = name;
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
