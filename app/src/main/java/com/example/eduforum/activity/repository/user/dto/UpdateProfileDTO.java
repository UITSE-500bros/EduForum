package com.example.eduforum.activity.repository.user.dto;

import com.example.eduforum.activity.model.user_manage.User;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfileDTO {
    private String name;
    private String phoneNumber;
    private String department;
    private String gender;
    private String schoolYear;
    private String profilePicture;

    public UpdateProfileDTO(User user) {
        this.name = user.getName();
        this.phoneNumber = user.getPhoneNumber();
        this.department = user.getDepartment();
        this.gender = user.getGender();
        this.schoolYear = user.getSchoolYear();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public Map<String, Object> toMap() {
        Map<String, Object> res = new HashMap<>();
        res.put("name", name);
        res.put("phoneNumber", phoneNumber);
        res.put("department", department);
        res.put("gender", gender);
        res.put("schoolYear", schoolYear);
        if (profilePicture != null)
            res.put("profilePicture", profilePicture);
        return res;
    }

    public void setProfilePicture(String url) {
        this.profilePicture = url;
    }
}
