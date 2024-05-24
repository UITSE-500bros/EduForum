package com.example.eduforum.activity.viewmodel.community.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.model.community_manage.Community;
import com.example.eduforum.activity.model.community_manage.CommunityMember;
import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.activity.repository.community.CommunityRepository;
import com.example.eduforum.activity.repository.community.ICommunityCallBack_C;

import java.util.ArrayList;
import java.util.List;

public class AdminMemberListViewModel extends ViewModel {
    private MutableLiveData<List<CommunityMember>> memberList;
    private MutableLiveData<List<CommunityMember>> adminList;
    private MutableLiveData<String> communityId;
    private MutableLiveData<String> errorMessage;
    private MutableLiveData<Boolean> isAdmin;
    CommunityRepository communityRepository;
    public AdminMemberListViewModel(){
        memberList = new MutableLiveData<>();
        adminList = new MutableLiveData<>();
        memberList.setValue(new ArrayList<>());
        adminList.setValue(new ArrayList<>());
        errorMessage = new MutableLiveData<>();
        communityId = new MutableLiveData<>();
        isAdmin = new MutableLiveData<>();
        communityRepository = CommunityRepository.getInstance();
        // communityRepository.checkAdmin(communityId.getValue(), ...);
        // isAdmin set to true for testing
        isAdmin.setValue(true);
    }
    public void setCommunityId(String communityId) {
        this.communityId.setValue(communityId);
        refreshMemberAdminLists();
    }
    public void refreshMemberAdminLists() {
        communityRepository.getCommunityMember(communityId.getValue(), new ICommunityCallBack_C() {
            @Override
            public void onRoleAdmin(List<Community> communityList) {
            }

            @Override
            public void onGetCommunityMemberSuccess(List<User> userList, List<User> newAdminList) {
                memberList.setValue(convertUserListToCommunityMemberList(userList, false));
                adminList.setValue(convertUserListToCommunityMemberList(newAdminList, true));
            }
        });
    }
    public void manageAdmin(String memberId, Boolean makeAdmin){
        if(memberId==null) return;
        if(makeAdmin){
            communityRepository.makeAdmin(communityId.getValue(), memberId);
        }
        else{
            communityRepository.removeAdmin(communityId.getValue(), memberId);
        }
        refreshMemberAdminLists();
    }
    private List<CommunityMember> convertUserListToCommunityMemberList(List<User> userList, Boolean isAdminList){
        List<CommunityMember> communityMemberList = new ArrayList<>();
        for(User user: userList){
            communityMemberList.add(new CommunityMember(communityId.getValue(), user, isAdminList));
        }
        return communityMemberList;
    }
    public void deleteMember(String memberId){
        if(memberId==null) return;
        communityRepository.removeUser(communityId.getValue(), memberId);
        refreshMemberAdminLists();
    }
    public LiveData<List<CommunityMember>> getMemberList() {
        return memberList;
    }
    public LiveData<List<CommunityMember>> getAdminList() {
        return adminList;
    }
    public LiveData<Boolean> getIsAdmin() {
        return isAdmin;
    }
}
