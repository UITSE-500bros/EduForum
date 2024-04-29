package com.example.eduforum.activity.repository;

public interface ILoginCallback {
    void onLoginSuccess();
    void onLoginFailed(String errorMsg);
}
