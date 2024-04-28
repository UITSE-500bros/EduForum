package com.example.eduforum.activity.model.user_manage;

public class UserConcrete implements UserBuilder{
    private String userId;
    private String userName;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private Gender gender;
    private String schoolYear;

    //private Image avatar;
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
    }

    @Override
    public User build() {
        return new User(userId,userName,name,email,password,phoneNumber,gender,schoolYear);

    }
}
