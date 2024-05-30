package com.example.eduforum.activity.repository.noti;

public interface IGetNumberOfUnread {
    void onGetNumberOfUnreadSuccess(int numberOfUnread);
    void onGetNumberOfUnreadFailure();
}
