package com.example.eduforum.activity.viewmodel.main;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.model.community_manage.Community;
import com.example.eduforum.activity.model.community_manage.CommunityBuilder;
import com.example.eduforum.activity.model.community_manage.CommunityConcreteBuilder;
import com.example.eduforum.activity.repository.CommunityRepository;
import com.example.eduforum.activity.repository.ICommunityCallBack;
import com.example.eduforum.activity.ui.main.fragment.CreateCommunityViewState;
import com.example.eduforum.activity.util.FlagsList;

public class CreateCommunityViewModel extends ViewModel {
    private final MutableLiveData<String> communityCategory;
    private final MutableLiveData<String> errorMsg;

    private final MutableLiveData<Boolean> createNewCommunity;

    private final MutableLiveData<Boolean> closeDialog;
    private final MutableLiveData<CreateCommunityViewState> commuLiveData;


    private final MutableLiveData<String> createErrorMsg;
    private final MutableLiveData<Boolean> isCreateCommunitySuccess;
    private final MutableLiveData<Boolean> isJoinCommunitySuccess;

    CommunityRepository communityRepository;
    // MutableLiveData<CommunityRepository> ....

    public CreateCommunityViewModel() {
        communityCategory= new MutableLiveData<>();
        errorMsg = new MutableLiveData<>();
        createNewCommunity = new MutableLiveData<>();
        closeDialog = new MutableLiveData<>();
        commuLiveData = new MutableLiveData<>();
        commuLiveData.setValue(new CreateCommunityViewState());

        communityRepository = new CommunityRepository();
        createErrorMsg = new MutableLiveData<>();
        isCreateCommunitySuccess = new MutableLiveData<>();
        isJoinCommunitySuccess = new MutableLiveData<>();
    }

    public LiveData<CreateCommunityViewState> getCommuLiveData() {
        return commuLiveData;
    }



    public LiveData<String> getCommunityCategory() {
        return communityCategory;
    }

    public LiveData<String> getErrorMsg() {
        return errorMsg;
    }
    public LiveData<Boolean> getCreateNewCommunity() {
        return createNewCommunity;
    }

    public LiveData<Boolean> getCloseDialog() {
        return closeDialog;
    }

    public LiveData<String> getCreateErrorMsg() {
        return createErrorMsg;
    }

    public MutableLiveData<Boolean> getIsCreateCommunitySuccess() {
        return isCreateCommunitySuccess;
    }

    public MutableLiveData<Boolean> getIsJoinCommunitySuccess() {
        return isJoinCommunitySuccess;
    }

    public void setCommunityCategory(String category) {
        communityCategory.setValue(category);
    }

    public void setErrorMsg(String error) {
        errorMsg.setValue(error);
    }
    public void setCreateNewCommunity(Boolean ableToCreate) {
        createNewCommunity.setValue(ableToCreate);
    }
    public void setCommuLiveData(CreateCommunityViewState commu) {
        commuLiveData.setValue(commu);
    }

    public void setCloseDialog(Boolean closed) {
        closeDialog.setValue(closed);
    }
    public void onCreateCommunityButtonClicked(){
        CreateCommunityViewState commuState = commuLiveData.getValue();
        if(commuState.getName() == null || commuState.getName().isEmpty()){
            errorMsg.setValue("Tên cộng đồng không thể trống");
            return;
        }
        commuState.setCategory(communityCategory.getValue());
        if(commuState.getCategory() == null || commuState.getCategory().isEmpty()){
            errorMsg.setValue("Chưa chọn phân loại cho cộng đồng");
            return;
        }

        Community commu = mapUIStateToCommunity(commuState);
        if(commu==null){
            errorMsg.setValue("Có lỗi xảy ra");
            return;
        }
        // O day Nam oi
         communityRepository.createCommunity(commu, new ICommunityCallBack() {
                     @Override
                     public void onCommunitySuccess() {
                         isJoinCommunitySuccess.postValue(true);
                     }

                     @Override
                     public void onCommunityFailure(String errorMsg) {
                         if (errorMsg.equals(FlagsList.ERROR_COMMUNITY_ADD_USER)) {
                             createErrorMsg.postValue("Không thể tham gia cộng đồng");
                         }
                     }

                     @Override
                     public void onCreateCommunitySuccess() {
                         isCreateCommunitySuccess.postValue(true);
                     }

                     @Override
                     public void onCreateCommunityFailure(String errorMsg) {
                         if(errorMsg.equals(FlagsList.ERROR_COMMUNITY_CODE_NOT_EXIST)){
                               createErrorMsg.postValue("Community không tồn tại");
                         }else if(errorMsg.equals(FlagsList.ERROR_COMMUNITY_FAILED_TO_CREATE)){
                             createErrorMsg.postValue("Không thể tạo community");
                         }
                     }
                 });


                 closeDialog.setValue(true);
        createNewCommunity.setValue(true);
    }


    public void onCancelButtonClicked(){
        closeDialog.setValue(true);
    }

    private Community mapUIStateToCommunity(CreateCommunityViewState UIState){
        if(UIState == null) return null;
        CommunityBuilder builder = new CommunityConcreteBuilder();
        return builder.setName(UIState.getName())
                .setDepartment(UIState.getCategory())
                .setDescription(UIState.getDescription())
                .build();
    }
}
