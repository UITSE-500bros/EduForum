package com.example.eduforum.activity.viewmodel.community;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.repository.community.CommunityRepository;
import com.example.eduforum.activity.ui.main.fragment.CreateCommunityViewState;

public class NewsFeedViewModel extends ViewModel {
    MutableLiveData<CreateCommunityViewState> currentCommunity;
    MutableLiveData<String> communityId;
    CommunityRepository communityRepository;
    public NewsFeedViewModel() {
        communityRepository = CommunityRepository.getInstance();
        communityId = new MutableLiveData<>();
        currentCommunity = new MutableLiveData<>();
    }
    public void setCurrentCommunity(CreateCommunityViewState community) {
        currentCommunity.setValue(community);
    }
    public LiveData<CreateCommunityViewState> getCurrentCommunity() {
        return currentCommunity;
    }
    public void setCommunityId(String communityId) {
        this.communityId.setValue(communityId);
    }
    public LiveData<String> getCommunityId() {
        return communityId;
    }
}
