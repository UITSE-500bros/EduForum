package com.example.eduforum.activity.viewmodel.community;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.model.community_manage.Community;
import com.example.eduforum.activity.repository.community.CommunityRepository;
import com.example.eduforum.activity.repository.community.ILeaveCommunityCallback;

public class SettingsCommunityViewModel extends ViewModel {
    CommunityRepository communityRepository;
    MutableLiveData<String>  errorMessage;
    MutableLiveData<Boolean> isLeaveSuccess;
    MutableLiveData<String> communityId;
    MutableLiveData<String> userId;
    public SettingsCommunityViewModel(){
        communityRepository = CommunityRepository.getInstance();
        errorMessage = new MutableLiveData<>();
        isLeaveSuccess = new MutableLiveData<>();
        isLeaveSuccess.setValue(false);
        communityId = new MutableLiveData<>();
        userId = new MutableLiveData<>();
    }
    public void setCommunityId(String communityId) {
        this.communityId.setValue(communityId);
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
    public void deleteCommunity() {
        Community community = new Community();
        community.setCommunityId(communityId.getValue());
        communityRepository.deleteCommunity(community);
    }
}
