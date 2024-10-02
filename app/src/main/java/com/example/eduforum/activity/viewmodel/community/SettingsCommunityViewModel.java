package com.example.eduforum.activity.viewmodel.community;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.model.community_manage.Community;
import com.example.eduforum.activity.repository.community.CommunityRepository;
import com.example.eduforum.activity.repository.community.ILeaveCommunityCallback;
import com.example.eduforum.activity.repository.community.INotificationStatus;

public class SettingsCommunityViewModel extends ViewModel {
    CommunityRepository communityRepository;
    MutableLiveData<String>  errorMessage;
    MutableLiveData<Boolean> isLeaveSuccess;
    MutableLiveData<String> communityId;
    MutableLiveData<String> userId;
    MutableLiveData<Boolean> isNotified;
    public SettingsCommunityViewModel(){
        communityRepository = CommunityRepository.getInstance();
        errorMessage = new MutableLiveData<>();
        isLeaveSuccess = new MutableLiveData<>();
        isLeaveSuccess.setValue(false);
        communityId = new MutableLiveData<>();
        userId = new MutableLiveData<>();
        isNotified = new MutableLiveData<>();
        isNotified.setValue(true);
    }
    public void setCommunityId(String communityId) {
        this.communityId.setValue(communityId);

        communityRepository.getNotificationStatus(communityId, new INotificationStatus() {
            @Override
            public void onNotificationStatusSuccess(Boolean status) {
                isNotified.setValue(status);
            }

            @Override
            public void onNotificationStatusFailure(String errorMsg) {
                isNotified.setValue(true);
            }
        });
    }
    public void setUserId(String userId) {
        this.userId.setValue(userId);
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
    public LiveData<Boolean> getIsLeaveSuccess() {
        return isLeaveSuccess;
    }
    public LiveData<Boolean> getIsNotified() {
        return isNotified;
    }
    public void leaveCommunity() {
        communityRepository.leaveCommunity(userId.getValue(), communityId.getValue(), new ILeaveCommunityCallback() {
            @Override
            public void onLeaveCommunitySuccess() {
                isLeaveSuccess.setValue(true);
            }

            @Override
            public void onLeaveCommunityFailure(String message) {
                if(message.equals("lastAdmin")) {
                    errorMessage.setValue("Không thể rời khỏi cộng đồng khi bạn là người quản trị cuối cùng");
                }
                else if(message.equals("networkError")){
                    errorMessage.setValue("Lỗi mạng");
                }
                else errorMessage.setValue(message);
                isLeaveSuccess.setValue(false);
            }
        });
    }
    public void toggleNotificationStatus(Boolean isNotified) {
        communityRepository.toggleNotification(communityId.getValue(), userId.getValue(), isNotified);
        this.isNotified.setValue(isNotified);
    }
    public void deleteCommunity() {
        Community community = new Community();
        community.setCommunityId(communityId.getValue());
        communityRepository.deleteCommunity(community);
    }
}
