package com.example.eduforum.activity.model;

import com.google.firebase.Timestamp;

public interface UserBuilder {
    UserBuilder setUserId(String userId);
    UserBuilder setUserName(String userName);
    UserBuilder setName(String name);
    UserBuilder setEmail(String email);
    UserBuilder setPassword(String password);
    UserBuilder setPhoneNumber(String phoneNumber);

    UserBuilder setGender(Gender gender);
    UserBuilder setSchoolYear(String schoolYear);
    UserBuilder build();
}
//repo for signin, signup, signout,post,comment