package com.example.eduforum.activity.viewmodel.main;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.model.community_manage.Community;
import com.example.eduforum.activity.model.community_manage.CommunityBuilder;
import com.example.eduforum.activity.model.community_manage.CommunityConcreteBuilder;
import com.example.eduforum.activity.repository.community.CommunityRepository;
import com.example.eduforum.activity.repository.community.ICommunityCallBack;
import com.example.eduforum.activity.repository.community.ICommunityCallBack_A;
import com.example.eduforum.activity.repository.community.ICommunityCallBack_B;
import com.example.eduforum.activity.repository.community.ICommunityCallBack_C;
import com.example.eduforum.activity.repository.auth.LoginRepository;
import com.example.eduforum.activity.repository.community.ICommunityChangeListener;
import com.example.eduforum.activity.ui.main.fragment.CreateCommunityViewState;
import com.example.eduforum.activity.ui.main.fragment.JoinCommunityViewState;
import com.example.eduforum.activity.util.FlagsList;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel{

    // Fields
    private final MutableLiveData<CreateCommunityViewState> newCommunityLiveData;
    private final MutableLiveData<JoinCommunityViewState> joinCommunityLiveData;
    private final MutableLiveData<List<CreateCommunityViewState>> joinedCommunityList;
    private final MutableLiveData<List<CreateCommunityViewState>> isAdminCommunityList;
    CommunityRepository communityRepository;
    LoginRepository loginRepository;

    // Methods
    // Constructors- initializations
    public HomeViewModel() {
        newCommunityLiveData = new MutableLiveData<>();
        newCommunityLiveData.setValue(new CreateCommunityViewState());
        communityRepository = CommunityRepository.getInstance();
        loginRepository = LoginRepository.getInstance();

        joinCommunityLiveData = new MutableLiveData<>();
        joinCommunityLiveData.setValue(new JoinCommunityViewState());
        joinedCommunityList = new MutableLiveData<>();
        isAdminCommunityList = new MutableLiveData<>();
        joinedCommunityList.setValue(new ArrayList<>());
        isAdminCommunityList.setValue(new ArrayList<>());

    }
    public void removeListener(){
        Log.d("HomeViewModel", "removeListener");
        communityRepository.removeListener();
    }
    public void setUpListener(){
        Log.d("HomeViewModel", "setUpListener");
        communityRepository.observeDocument(FirebaseAuth.getInstance().getUid(), new ICommunityChangeListener() {
            @Override
            public void onCommunityFetch(List<Community> communities) {
                joinedCommunityList.setValue(convertToViewStateList(communities));
            }

            @Override
            public void onCommunityChange(Community community) {

            }

            @Override
            public void onCreateNewCommunity(List<Community> communities) {
                isAdminCommunityList.setValue(convertToViewStateList(communities));
            }

        });
    }
    //-------------------------------------------------------------------------------
    // Getters and Setters
    public LiveData<List<CreateCommunityViewState>> getJoinedCommunityList() {
        return joinedCommunityList;
    }
    public LiveData<List<CreateCommunityViewState>> getIsAdminCommunityList() {
        return isAdminCommunityList;
    }
    public LiveData<JoinCommunityViewState> getJoinCommuLiveData() {
        return joinCommunityLiveData;
    }

    public LiveData<CreateCommunityViewState> getNewCommunityLiveData() {
        return newCommunityLiveData;
    }
    public void setNewCommunityLiveData(CreateCommunityViewState newCommunityLiveData) {
        this.newCommunityLiveData.setValue(newCommunityLiveData);
    }

    public void setJoinCommunityLiveData(JoinCommunityViewState joinCommunityLiveData) {
        this.joinCommunityLiveData.setValue(joinCommunityLiveData);
    }
    //--------------------------------------------------------------------------------------
    // Other methods

    // create community usecase
    public void onConfirmCreateCommunityButtonClicked(){
        CreateCommunityViewState commuState = newCommunityLiveData.getValue();
        // validate
        if(!IsCommunityUIStateValid(commuState)) return;
        // create community object (map UI state to domain object)
        Community commu = mapUIStateToCommunity(commuState);
        communityRepository.createCommunity(commu, new ICommunityCallBack() {
            @Override
            public void onCreateCommunitySuccess(String communityId) {
                closeCreateCommunityDialog();
            }
            @Override
            public void onCreateCommunityFailure(String errorMsg) {
                String errorMessage;
                if(errorMsg.equals(FlagsList.ERROR_COMMUNITY_FAILED_TO_CREATE)){
                    errorMessage = "Không thể tạo cộng đồng";
                }
                else{
                    errorMessage = "Đã có lỗi xảy ra";
                }
                commuState.setErrorMessage(errorMessage);
                newCommunityLiveData.setValue(commuState);
            }
        });
    }
    private Community mapUIStateToCommunity(CreateCommunityViewState UIState){
        if(UIState == null) return null;
        CommunityBuilder builder = new CommunityConcreteBuilder();
        return builder.setName(UIState.getName())
                .setDepartment(UIState.getCategory())
                .setDescription(UIState.getDescription())
                //.setProfileImage(UIState.getCommuAvt())
                .build();
    }

    public void onCancelCreateCommunityButtonClicked(){
        // close dialog
        closeCreateCommunityDialog();
    }

    // --------------------------------
    // join community usecase
    public void onConfirmJoinCommunityButtonClicked(){
        JoinCommunityViewState joinCommuState = joinCommunityLiveData.getValue();
        // validate
        if(joinCommuState.getCommunityId() == null || joinCommuState.getCommunityId().isEmpty()){
            joinCommuState.setErrorMessage("Mã cộng đồng không thể trống");
            joinCommunityLiveData.setValue(joinCommuState);
            return;
        }
        // call repository, pass the communityID of joinCommuState directly to repository
        communityRepository.thamGia(joinCommuState.getCommunityId(), FirebaseAuth.getInstance().getUid(), new ICommunityCallBack_A() {
            @Override
            public void onJoinCommunitySuccess(String successMsg) {
                closeJoinCommunityDialog();
            }

            @Override
            public void onJoinCommunityFailure(String errorMsg) {
                String errorMessage;
                if (errorMsg.equals(FlagsList.ERROR_COMMUNITY_CODE_NOT_EXIST)) {
                    errorMessage = "Mã cộng đồng không tồn tại";
                }
                else{
                    errorMessage = "Đã có lỗi xảy ra";
                }
                joinCommuState.setErrorMessage(errorMessage);
                joinCommunityLiveData.setValue(joinCommuState);
            }
        });
    }
    public void onCancelJoinCommunityButtonClicked(){
        // close dialog
        closeJoinCommunityDialog();
    }


    // --------------------------------
    // other methods
    private List<CreateCommunityViewState> convertToViewStateList(List<Community> communities) {
        List<CreateCommunityViewState> viewStates = new ArrayList<>();
        for (Community community : communities) {
            if(community.getCommunityId() == null) continue;
            viewStates.add(new CreateCommunityViewState(community.getName(), community.getDescription(), community.getDepartment(), community.getProfileImage(), community.getCommunityId()));
        }
        return viewStates;
    }

    private void closeCreateCommunityDialog() {
        CreateCommunityViewState commuState = newCommunityLiveData.getValue();
        commuState.setIsDialogClosed(true);
        newCommunityLiveData.setValue(commuState);
    }
    private void closeJoinCommunityDialog() {
        JoinCommunityViewState joinCommuState = joinCommunityLiveData.getValue();
        joinCommuState.setIsDialogClosed(true);
        joinCommunityLiveData.setValue(joinCommuState);
    }
    private boolean IsCommunityUIStateValid(CreateCommunityViewState state) {
        boolean isValid = true;
        if (state.getName() == null || state.getName().isEmpty()) {
            state.setErrorMessage("Tên cộng đồng không thể trống");
            isValid = false;
        }
        else if (state.getCategory() == null || state.getCategory().isEmpty()) {
            state.setErrorMessage("Hãy chọn một danh mục");
            isValid = false;
        }
        newCommunityLiveData.postValue(state);
        return isValid;
    }
}
