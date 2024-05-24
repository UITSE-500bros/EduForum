package com.example.eduforum.activity.repository.post;

import java.util.List;

public interface IUpload {
    void onSuccess(List<String> url);
    void onFailed(String message);
}
