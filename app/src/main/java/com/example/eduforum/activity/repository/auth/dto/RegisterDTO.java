package com.example.eduforum.activity.repository.auth.dto;

import com.example.eduforum.activity.model.user_manage.User;

public class RegisterDTO {
    private String name;
    private String email;
    private String phoneNumber;
    private String gender;
    private String schoolYear;
    private String department;
    private String profilePicture;

    public RegisterDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.department = user.getDepartment();
        this.gender = user.getGender();
        this.schoolYear = user.getSchoolYear();
        this.profilePicture = user.getProfilePicture();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
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
