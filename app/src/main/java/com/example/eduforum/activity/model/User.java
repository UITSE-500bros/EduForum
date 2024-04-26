package com.example.eduforum.activity.model;

import android.media.Image;

import com.google.firebase.Timestamp;

public class User implements UserBuilder{
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

    @Override
    public UserBuilder setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public UserBuilder setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    @Override
    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public UserBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public UserBuilder setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    @Override
    public UserBuilder setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
        return this;
    }

    @Override
    public UserBuilder build() {
        return new User(userId,userName, name, email, password, phoneNumber,gender,schoolYear);
    }
}