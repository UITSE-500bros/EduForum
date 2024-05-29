package com.example.eduforum.activity.repository.noti;

import com.example.eduforum.activity.model.noti_manage.Notification;

import java.util.List;

public interface INotificationCallback {
    void onGetRealtimeSuccess(List<Notification> data);
    void onGetRealtimeFailure(String errorMsg);

}
