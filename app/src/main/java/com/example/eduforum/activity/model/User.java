package com.example.eduforum.activity.model;

public class User {
    private String userId;
    private String userName;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String gender;
    private String schoolYear;

    private String department;

    //private Image avatar;
    public User() {
    }

    public User(String userId,String userName, String name, String email, String password, String phoneNumber, String gender, String schoolYear,String department) {
        this.userId = userId;
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.schoolYear = schoolYear;
        this.department = department;
    }


    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public String getDepartment() {
        return department;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
