package com.example.eduforum.activity.repository.community;

public interface INotificationStatus {
    void onNotificationStatusSuccess(Boolean status); // false: off, true: on
    void onNotificationStatusFailure(String errorMsg);
}
