package com.example.eduforum.activity.viewmodel.community;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.eduforum.activity.model.community_manage.Community;

import com.example.eduforum.activity.repository.community.CommunityRepository;
import com.example.eduforum.activity.repository.community.ICommunityCallBack_C;
import com.example.eduforum.activity.ui.main.fragment.CreateCommunityViewState;

import java.util.List;

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
        communityRepository.getCommunity(communityId, new ICommunityCallBack_C() {
            @Override
            public void onRoleAdmin(List<Community> communityList) {

            }

            @Override
            public void getCommunityInfo(Community community) {
                currentCommunity.setValue(new CreateCommunityViewState(community.getName(), community.getDescription(), community.getDepartment(), null, communityId));
            }
        });
    }
    public LiveData<String> getCommunityId() {
        return communityId;
    }
}
