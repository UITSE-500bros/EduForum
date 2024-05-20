package com.example.eduforum.activity.repository.community;

public interface IRequestCallback {
    void onSuccess(String response);
    void onFailure(String errorMsg);
}
