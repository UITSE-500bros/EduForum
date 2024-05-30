package com.example.eduforum.activity.viewmodel.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.model.noti_manage.Notification;
import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.activity.repository.noti.INotificationCallback;
import com.example.eduforum.activity.repository.noti.NotificationRepository;
import com.example.eduforum.activity.ui.main.fragment.NotificationViewState;
import com.example.eduforum.activity.viewmodel.shared.UserViewModel;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NotificationViewModel extends ViewModel {

    private MutableLiveData<User> currentUser;
    private MutableLiveData<List<NotificationViewState>> notificationList;
    private NotificationRepository notificationRepository;

    public NotificationViewModel(){

        notificationList = new MutableLiveData<>();
        currentUser = new MutableLiveData<>();
        notificationRepository = NotificationRepository.getInstance();
    }

    public void setCurrentUser(User user){
        currentUser.setValue(user);
    }

    public MutableLiveData<List<NotificationViewState>> getNotificationList() {
        return notificationList;
    }

    private String convertTimestamp(Timestamp timestamp){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        return dateFormat.format(timestamp.toDate());
    }

    public void setupListener(UserViewModel userViewModel){
        notificationRepository.observeNotification(currentUser.getValue().getUserId(),
                new INotificationCallback() {
                    @Override
                    public void onGetRealtimeSuccess(List<Notification> data) {
                        List<NotificationViewState> notificationViewStates = new ArrayList<>();
                        for (Notification notification : data) {
                            NotificationViewState notificationViewState = new NotificationViewState(
                                    notification.getNotificationID(),
                                    notification.getTriggeredBy(),
                                    notification.getType(),
                                    notification.isRead(),
                                    convertTimestamp(notification.getTimestamp()),
                                    notification.getCommunity().getName(),
                                    notification.getPost().getPostID(),
                                    notification.getCommentID(),
                                    notification.getCommunity().getCommunityID()
                            );
                            notificationViewStates.add(notificationViewState);
                        }
                        notificationList.setValue(notificationViewStates);
                    }

                    @Override
                    public void onGetRealtimeFailure(String errorMsg) {

                    }
                });
    }

    public void markAsRead(String notificationID){
        notificationRepository.markAsRead(currentUser.getValue().getUserId(), notificationID);
    }

    public void markAllAsRead(){
        notificationRepository.markAllAsRead(currentUser.getValue().getUserId());

    }

    public void clearListener(){
        notificationRepository.removeNotificationListener();
    }
}
