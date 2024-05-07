package com.example.eduforum.activity.repository.auth;

public interface ISignUpCallback {
    void onSignUpSuccess();
    void onSignUpFailure(String errorMsg);
}
