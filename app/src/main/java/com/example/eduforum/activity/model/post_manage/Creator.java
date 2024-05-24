package com.example.eduforum.activity.model.post_manage;

import java.io.Serializable;
import java.util.Map;

public class Creator implements Serializable {
    public String creatorID;
    public String name;
    public String department;
    public String profilePicture;

    public Creator() {
    }

    public Creator(String creatorID, String name, String department, String profilePicture) {
        this.creatorID = creatorID;
        this.name = name;
        this.department = department;
        this.profilePicture = profilePicture;
    }

    public String getCreatorID() {
        return creatorID;
    }
    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Map<String, Object> convertToDataObject() {
        Map<String, Object> data = new java.util.HashMap<>();
        data.put("creatorID", creatorID);
        data.put("name", name);
        data.put("department", department);
        data.put("profilePicture", profilePicture);
        return data;
    }
}
