package com.example.eduforum.activity.repository;

public interface ISignUpCallback {
    void onSignUpSuccess();
    void onSignUpFailure(String errorMsg);
}
