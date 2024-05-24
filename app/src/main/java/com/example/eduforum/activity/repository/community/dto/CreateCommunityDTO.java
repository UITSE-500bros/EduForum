package com.example.eduforum.activity.repository.community.dto;

import com.example.eduforum.activity.model.community_manage.Community;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CreateCommunityDTO {
    private String communityID;
    private String name;
    private String description;
    private String profilePicture;
    private List<String> adminList;
    private String visibility;
    private Boolean waitForApproval;

    public CreateCommunityDTO(Community community) {
        this.communityID = community.getCommunityId();
        this.name = community.getName();
        this.description = community.getDescription();
        this.profilePicture = community.getProfilePicture();
        this.adminList = community.getAdminList();
        this.visibility = "public";
        this.waitForApproval = false;
    }
    public Map<String, Object> convertToDataObject() {
        Map<String, Object> data = new java.util.HashMap<>();
        data.put("name", name);
        data.put("description", description);
        data.put("profilePicture", profilePicture);
        data.put("adminList", adminList);
        data.put("visibility", visibility);
        data.put("waitForApproval", waitForApproval);
        return data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<String> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<String> adminList) {
        this.adminList = adminList;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Boolean getWaitForApproval() {
        return waitForApproval;
    }

    public void setWaitForApproval(Boolean waitForApproval) {
        this.waitForApproval = waitForApproval;
    }
}
