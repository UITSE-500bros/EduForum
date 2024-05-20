package com.example.eduforum.activity.repository.community.dto;

import com.example.eduforum.activity.model.user_manage.User;

public class JoinRequestDTO {
    private String name;
    private String department;
    private String profilePicture;

    public JoinRequestDTO(User user) {
        this.name = user.getName();
        this.department = user.getDepartment();
        this.profilePicture = user.getProfilePicture();
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
}
