package com.example.eduforum.activity.viewmodel.community.settings;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.activity.repository.community.CommunityRepository;
import com.example.eduforum.activity.repository.community.IGetMemberApproval;

import java.util.ArrayList;
import java.util.List;

public class MemberRequestsViewModel extends ViewModel {
    private MutableLiveData<String> communityId;
    private MutableLiveData<List<User>> memberRequests;
    private MutableLiveData<String> errorMessage;
    CommunityRepository communityRepository;
    public MemberRequestsViewModel() {
        communityRepository = CommunityRepository.getInstance();
        communityId = new MutableLiveData<>();
        memberRequests = new MutableLiveData<>();
        memberRequests.setValue(new ArrayList<>());
        errorMessage = new MutableLiveData<>();

    }

    public void setCommunityId(String communityId) {
        this.communityId.setValue(communityId);
        refreshMemberRequests();
    }
    public void refreshMemberRequests(){
        communityRepository.getMemberApproval(communityId.getValue(), new IGetMemberApproval() {
            @Override
            public void onGetMemberApprovalSuccess(List<User> users) {
                if(users != null) {
                    memberRequests.setValue(users);
                    if(users.size()!=0 && users.get(0).getUserId()==null){
                        Log.d("MemberRequestViewModel", "Here");
                    }
                }
                else memberRequests.setValue(new ArrayList<>());
            }

            @Override
            public void onGetMemberApprovalFailure(String errorMsg) {
                errorMessage.setValue(errorMsg);
            }
        });
    }

    public LiveData<List<User>> getMemberRequests() {
        return memberRequests;
    }

    public void memberApproval(String userId, Boolean isApproved) {
        communityRepository.approveUser(communityId.getValue(), userId, isApproved);
        refreshMemberRequests();
    }
}
