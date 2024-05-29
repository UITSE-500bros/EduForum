package com.example.eduforum.activity.repository.community.dto;

import android.net.Uri;

import com.example.eduforum.activity.model.community_manage.Community;

import java.util.Map;


public class UpdateCommunityDTO {

    private String communityID;
    private String name;
    private String description;
    private String profilePicture;
    private String department;
    private String visibility;
    private String oldProfilePicture;

    public UpdateCommunityDTO(Community community) {
        this.communityID = community.getCommunityId();
        this.name = community.getName();
        this.description = community.getDescription();
        this.profilePicture = community.getProfilePicture();
        this.department = community.getDepartment();
        this.visibility = community.getVisibility();
    }

    public String getOldProfilePicture() {
        return oldProfilePicture;
    }

    public void setOldProfilePicture(String oldProfilePicture) {
        this.oldProfilePicture = oldProfilePicture;
    }

    public String getCommunityID() {
        return communityID;
    }

    public void setCommunityID(String communityID) {
        this.communityID = communityID;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Map<String, Object> convertToDataObject() {
        Map<String, Object> res = new java.util.HashMap<>();
        if (name != null) {
            res.put("name", name);
        }
        if (description != null) {
            res.put("description", description);
        }
        if (profilePicture != null) {
            res.put("profilePicture", profilePicture);
            if (oldProfilePicture != null) {
                res.put("oldProfilePicture", oldProfilePicture);
            }
        }
        if (department != null) {
            res.put("department", department);
        }
        if (visibility != null) {
            res.put("visibility", visibility);
        }
        return res;
    }
}
