package com.example.eduforum.activity.viewmodel.community.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.model.community_manage.Community;
import com.example.eduforum.activity.model.community_manage.CommunityBuilder;
import com.example.eduforum.activity.model.community_manage.CommunityConcreteBuilder;
import com.example.eduforum.activity.repository.community.CommunityRepository;
import com.example.eduforum.activity.repository.community.dto.IUpdateCommunityCallback;
import com.example.eduforum.activity.ui.main.fragment.CreateCommunityViewState;

public class ProfileCommunityViewModel extends ViewModel {
    private MutableLiveData<CreateCommunityViewState> communityViewState;
    private MutableLiveData<String> errorMessage;
    private MutableLiveData<Boolean> isSuccess;

    CommunityRepository communityRepository;
    public ProfileCommunityViewModel(){
        communityViewState = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        communityRepository = CommunityRepository.getInstance();
        isSuccess = new MutableLiveData<>();
        isSuccess.setValue(false);
    }

    public void updateCommunityInfo(){
        Community community = mapStateToCommunity(communityViewState.getValue());

        if(community == null) {
            errorMessage.setValue("Lỗi! Không thể cập nhật thông tin cộng đồng");
            isSuccess.setValue(false);
            return;
        }
        communityRepository.updateCommunity(community, new IUpdateCommunityCallback() {
            @Override
            public void onUpdateCommunitySuccess(Community newCommunity) {
                communityViewState.setValue(new CreateCommunityViewState(newCommunity));
                isSuccess.setValue(true);
            }

            @Override
            public void onUpdateCommunityFailure(String errorMsg) {
                errorMessage.setValue(errorMsg);
                isSuccess.setValue(false);
            }
        });
    }
    Community mapStateToCommunity(CreateCommunityViewState UIState){
        if(UIState == null) return null;
        CommunityBuilder builder = new CommunityConcreteBuilder();
        return builder.setName(UIState.getName())
                .setCommunityId(UIState.getCommunityID())
                .setDepartment(UIState.getCategory())
                .setDescription(UIState.getDescription())
                .setProfileImage(UIState.getCommuAvt())
                .build();
    }
    public LiveData<Boolean> getIsSuccess() {
        return isSuccess;
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
