package com.example.eduforum.activity.viewmodel.community.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.model.community_manage.CommunityMember;
import com.example.eduforum.activity.repository.community.CommunityRepository;

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
        refreshMemberList();
        refreshAdminList();
    }
    public void refreshMemberList() {
        // communityRepository.getMemberList(communityId.getValue(), ...);
    }
    public void refreshAdminList(){
        // communityRepository.getAdminList(communityId.getValue(), ...);
    }
    public void manageAdmin(String memberId, Boolean makeAdmin){
        if(memberId==null) return;
        if(makeAdmin){
            communityRepository.makeAdmin(communityId.getValue(), memberId);
        }
        else{
            communityRepository.removeAdmin(communityId.getValue(), memberId);
        }
        // temporary solution (Thy chua co callback), co the modify cac list thu cong
        refreshAdminList();
        refreshMemberList();
    }
    public void deleteMember(String memberId){
        if(memberId==null) return;
        communityRepository.removeUser(communityId.getValue(), memberId);
        // temporary solution (Thy chua co callback), callback chi can boolean -> modify thu cong, khong can fetch lai
        refreshMemberList();
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
