package com.example.eduforum.activity.viewmodel.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.model.community_manage.Community;
import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.activity.repository.community.CommunityRepository;
import com.example.eduforum.activity.repository.community.IExploreCallback;
import com.example.eduforum.activity.repository.community.IRequestCallback;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ExploreViewModel extends ViewModel {
    private MutableLiveData<List<Community>> communityList;
    private MutableLiveData<String> errorMessage;
    private MutableLiveData<User> currentUser;
    private CommunityRepository communityRepository;
    public ExploreViewModel(){
        communityList = new MutableLiveData<>();
        communityList.setValue(new ArrayList<>());
        errorMessage = new MutableLiveData<>();
        currentUser = new MutableLiveData<>();
        communityRepository = CommunityRepository.getInstance();

    }
    public void setCurrentUser(User user){
        currentUser.setValue(user);
        fetchCommunityList();
    }
    public void fetchCommunityList(){
        communityRepository.exploreCommunity(currentUser.getValue().getUserId(), new IExploreCallback(){

            @Override
            public void onGetCommunitySuccess(List<Community> communities) {
                communityList.setValue(communities);
            }

            @Override
            public void onGetCommunityFailure(String errorMsg) {
                errorMessage.setValue(errorMsg);
            }
        });
    }
    public void joinCommunity(String communityId){
        communityRepository.requestJoinCommunity(communityId, currentUser.getValue(), new IRequestCallback() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onFailure(String errorMsg) {
                errorMessage.setValue(errorMsg);
            }
        });
    }
    public LiveData<List<Community>> getCommunityList() {
        return communityList;
    }
    public void setCommunityList(List<Community> communityList) {
        this.communityList.setValue(communityList);
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage.setValue(errorMessage);
    }

}
