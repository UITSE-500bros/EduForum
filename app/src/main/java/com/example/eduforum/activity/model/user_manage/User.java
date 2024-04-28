package com.example.eduforum.activity.model.user_manage;

public class User {
    private String userId;
    private String userName;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private Gender gender;
    private String schoolYear;

    //private Image avatar;
    public User() {
    }

    public User(String userId,String userName, String name, String email, String password, String phoneNumber, Gender gender, String schoolYear) {
        this.userId = userId;
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.schoolYear = schoolYear;
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

    public Gender getGender() {
        return gender;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

}
