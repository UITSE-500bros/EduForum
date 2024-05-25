package com.example.eduforum.activity.model.community_manage;

import android.net.Uri;

import com.example.eduforum.activity.model.user_manage.User;

public class CommunityMember {
    private String communityId;
    private String name;
    private String memberId;

    private String profileImage;
    private String createdDate;
    private String department;

    private Boolean isAdmin;

    public CommunityMember() {
    }

    public CommunityMember(String communityId, String memberId, String name, String profileImage, String createdDate, String department, Boolean isAdmin) {
        this.communityId = communityId;
        this.memberId = memberId;
        this.name = name;
        this.profileImage = profileImage;
        this.createdDate = createdDate;
        this.department = department;
        this.isAdmin = isAdmin;
    }

    public CommunityMember(String communityId, User user, Boolean isAdmin) {
        this.communityId = communityId;
        this.name = user.getName();
        this.memberId = user.getUserId();
        this.isAdmin = isAdmin;
        this.department = user.getDepartment();
        this.profileImage = user.getProfilePicture();
    }

    public String getMemberId() {
        return memberId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
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
