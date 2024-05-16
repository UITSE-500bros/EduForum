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
    private Integer totalPost;
    private Integer totalNewPost;


    private List<String> userList = new ArrayList<>();

    public Community() {
    }


    public Community(String communityId, String name, List<String> adminList, Uri profileImage, String createdDate, String description, String department, List<String> userList) {
        this.communityId = communityId;
        this.name = name;
        this.adminList = adminList;
        this.profileImage = profileImage;
        this.createdDate = createdDate;
        this.description = description;
        this.department = department;
        this.userList = userList;
    }

    public Community(String communityId, String name, List<String> adminList, Uri profileImage, String createdDate, String description, String department, Integer totalPost, Integer totalNewPost, List<String> userList) {
        this.communityId = communityId;
        this.name = name;
        this.adminList = adminList;
        this.profileImage = profileImage;
        this.createdDate = createdDate;
        this.description = description;
        this.department = department;
        this.totalPost = totalPost;
        this.totalNewPost = totalNewPost;
        this.userList = userList;
    }

    public Integer getTotalPost() {
        return totalPost;
    }

    public void setTotalPost(Integer totalPost) {
        this.totalPost = totalPost;
    }

    public Integer getTotalNewPost() {
        return totalNewPost;
    }

    public void setTotalNewPost(Integer totalNewPost) {
        this.totalNewPost = totalNewPost;
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


    public void setUserList(List<String> userList) {
        this.userList = userList;
    }

    public List<String> getUserList() {
        return userList;
    }
}
