package com.example.eduforum.activity.repository;

public class LoginTestRepository extends LoginRepository{
    public LoginTestRepository() {
        super();
        mAuth.useEmulator("10.0.2.2", 9099);
    }
}
