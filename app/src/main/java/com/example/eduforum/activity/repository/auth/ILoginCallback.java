package com.example.eduforum.activity.repository.auth;

public interface ILoginCallback {
    void onLoginSuccess();
    void onLoginFailed(String errorMsg);
}
