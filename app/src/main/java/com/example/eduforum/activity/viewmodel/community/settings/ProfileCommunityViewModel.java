package com.example.eduforum.activity.viewmodel.community.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.repository.community.CommunityRepository;
import com.example.eduforum.activity.ui.main.fragment.CreateCommunityViewState;

public class ProfileCommunityViewModel extends ViewModel {
    private MutableLiveData<CreateCommunityViewState> communityViewState;
    private MutableLiveData<String> errorMessage;

    CommunityRepository communityRepository;
    public ProfileCommunityViewModel(){
        communityViewState = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        communityRepository = CommunityRepository.getInstance();
    }
    public void setCommunityViewState(CreateCommunityViewState communityViewState) {
        this.communityViewState.setValue(communityViewState);
    }
    public LiveData<CreateCommunityViewState> getCommunityViewState() {
        return communityViewState;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

}
