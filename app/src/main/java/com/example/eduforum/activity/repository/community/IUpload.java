package com.example.eduforum.activity.repository.community;

public interface IUpload {
    void onUploadSuccess(String url);
    void onUploadFailed(String message);
}
