package com.example.eduforum.activity.repository.user;

public interface IUpload {
    void onUploadSuccess(String url);
    void onUploadFailed(String message);
}
