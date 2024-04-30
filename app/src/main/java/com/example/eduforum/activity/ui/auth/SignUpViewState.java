package com.example.eduforum.activity.ui.auth;

import android.net.Uri;

public class SignUpViewState {
    public String email;
    public String password;
    public String passwordCheck;
    public String fullName;
    public String schoolYear;
    public String gender;
    public String department;
    public String phoneNumber;
    public Uri profilePicture;

    public SignUpViewState(String email, String password, String passwordCheck, String fullName, String schoolYear, String gender, String department, String phoneNumber, Uri profilePicture) {
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.fullName = fullName;
        this.schoolYear = schoolYear;
        this.gender = gender;
        this.department = department;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
    }
    public SignUpViewState() {}

    public Uri getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Uri profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public String getGender() {
        return gender;
    }

    public String getDepartment() {
        return department;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
