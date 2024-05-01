package com.example.eduforum.activity.viewmodel.main;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.model.community_manage.Community;
import com.example.eduforum.activity.model.community_manage.CommunityBuilder;
import com.example.eduforum.activity.model.community_manage.CommunityConcreteBuilder;
import com.example.eduforum.activity.repository.CommunityRepository;
import com.example.eduforum.activity.repository.CommunityTestRepository;
import com.example.eduforum.activity.repository.ICommunityCallBack;
import com.example.eduforum.activity.repository.ICommunityCallBack_A;
import com.example.eduforum.activity.repository.ICommunityCallBack_B;
import com.example.eduforum.activity.repository.ICommunityCallBack_C;
import com.example.eduforum.activity.repository.LoginRepository;
import com.example.eduforum.activity.repository.LoginTestRepository;
import com.example.eduforum.activity.ui.main.fragment.CreateCommunityViewState;
import com.example.eduforum.activity.ui.main.fragment.JoinCommunityViewState;
import com.example.eduforum.activity.util.FlagsList;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class HomeViewModel extends ViewModel{
    private final MutableLiveData<String> communityCategory;
    private final MutableLiveData<String> errorMsg;

    private final MutableLiveData<Boolean> createCommunityDialogIsClosed;

    private final MutableLiveData<Boolean> joinCommunityDialogIsClosed;
    private final MutableLiveData<CreateCommunityViewState> commuLiveData;

    private final MutableLiveData<JoinCommunityViewState> joinCommunityLiveData;
    private final MutableLiveData<Boolean> isCreateCommunitySuccess;
    private final MutableLiveData<Boolean> isJoinCommunitySuccess;
    private final MutableLiveData<List<CreateCommunityViewState>> joinedCommunityList;
    private final MutableLiveData<List<CreateCommunityViewState>> isAdminCommunityList;

    CommunityRepository communityRepository;

    LoginRepository loginRepository;

    public HomeViewModel() {
        communityCategory= new MutableLiveData<>();
        errorMsg = new MutableLiveData<>();
        createCommunityDialogIsClosed = new MutableLiveData<>();
        joinCommunityDialogIsClosed = new MutableLiveData<>();
        commuLiveData = new MutableLiveData<>();
        commuLiveData.setValue(new CreateCommunityViewState());
        if (FlagsList.APPLICATION_ENVIRONMENT.equals("production")) {
            communityRepository = new CommunityRepository();
            loginRepository = new LoginRepository();
        } else {
            communityRepository = new CommunityTestRepository();
            loginRepository = new LoginTestRepository();

        }

        isCreateCommunitySuccess = new MutableLiveData<>();
        isJoinCommunitySuccess = new MutableLiveData<>();
        joinCommunityLiveData = new MutableLiveData<>();
        joinCommunityLiveData.setValue(new JoinCommunityViewState());
        joinedCommunityList = new MutableLiveData<>();
        isAdminCommunityList = new MutableLiveData<>();
        joinedCommunityList.setValue(new ArrayList<>());
        isAdminCommunityList.setValue(new ArrayList<>());
    }
    public LiveData<List<CreateCommunityViewState>> getJoinedCommunityList() {
        return joinedCommunityList;
    }
    public LiveData<List<CreateCommunityViewState>> getIsAdminCommunityList() {
        return isAdminCommunityList;
    }
    public LiveData<JoinCommunityViewState> getJoinCommuLiveData() {
        return joinCommunityLiveData;
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

    public LiveData<Boolean> getCreateCommunityDialogIsClosed() {
        return createCommunityDialogIsClosed;
    }
    public LiveData<Boolean> getJoinCommunityDialogIsClosed() {
        return joinCommunityDialogIsClosed;
    }

    public LiveData<Boolean> getIsCreateCommunitySuccess() {
        return isCreateCommunitySuccess;
    }

    public LiveData<Boolean> getIsJoinCommunitySuccess() {
        return isJoinCommunitySuccess;
    }

    public CommunityRepository getCommunityRepository() {
        return communityRepository;
    }
    public LoginRepository getLoginRepository() {
        return loginRepository;
    }

    public void setCommunityCategory(String category) {
        communityCategory.setValue(category);
    }

    public void setErrorMsg(String error) {
        errorMsg.setValue(error);
    }
    public void setCommuLiveData(CreateCommunityViewState commu) {
        commuLiveData.setValue(commu);
    }

    public void setCreateCommunityDialogIsClosed(Boolean closed) {
        createCommunityDialogIsClosed.setValue(closed);
    }
    public void setJoinCommunityDialogIsClosed(Boolean closed) {
        joinCommunityDialogIsClosed.setValue(closed);
    }

    public void setIsCreateCommunitySuccess(Boolean success) {
        isCreateCommunitySuccess.setValue(success);
    }
    public void setIsJoinCommunitySuccess(Boolean success) {
        isJoinCommunitySuccess.setValue(success);
    }
    public void setJoinCommuLiveData(JoinCommunityViewState joinCommu) {
        joinCommunityLiveData.setValue(joinCommu);
    }
    public void setJoinedCommunityList(List<CreateCommunityViewState> joinedCommunityList) {
        this.joinedCommunityList.setValue(joinedCommunityList);
    }
    public void setIsAdminCommunityList(List<CreateCommunityViewState> isAdminCommunityList) {
        this.isAdminCommunityList.setValue(isAdminCommunityList);
    }

    public void setCommunityRepository(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    public void setLoginRepository(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    //-----------------------------------------------------------


    public void onConfirmCreateCommunityButtonClicked(){
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

        communityRepository.createCommunity(commu, new ICommunityCallBack() {
            @Override
            public void onCreateCommunitySuccess(String communityId) {
                isCreateCommunitySuccess.setValue(true);
                commuLiveData.setValue(new CreateCommunityViewState(commuLiveData.getValue().getName(), commuLiveData.getValue().getDescription(), commuLiveData.getValue().getCategory(), null, communityId));
                updateIsAdminCommunityList(commuLiveData.getValue());
            }

            @Override
            public void onCreateCommunityFailure(String errorMsg) {
                if(errorMsg.equals(FlagsList.ERROR_COMMUNITY_FAILED_TO_CREATE)){
                    setErrorMsg("Không thể tạo cộng đồng");
                }
                else{
                    setErrorMsg("Có lỗi xảy ra");
                }
            }


        });


        createCommunityDialogIsClosed.setValue(true);
    }


    public void onCancelCreateCommunityButtonClicked(){
        createCommunityDialogIsClosed.setValue(true);
    }

    public void onConfirmJoinCommunityButtonClicked(){
        JoinCommunityViewState joinCommuState = joinCommunityLiveData.getValue();
        if(joinCommuState.getCommunityId() == null || joinCommuState.getCommunityId().isEmpty()){
            errorMsg.setValue("Mã cộng đồng không thể trống");
            return;
        }
        communityRepository.thamGia(joinCommuState.getCommunityId(), FirebaseAuth.getInstance().getUid(), new ICommunityCallBack_A() {
            @Override
            public void onJoinCommunitySuccess() {
                CreateCommunityViewState newCommunity = new CreateCommunityViewState();
                newCommunity.setCommunityID(joinCommuState.getCommunityId());
                updateJoinedCommunityList(newCommunity);
            }

            @Override
            public void onJoinCommunityFailure(String errorMsg) {
                if (errorMsg.equals(FlagsList.ERROR_COMMUNITY_CODE_NOT_EXIST)) {
                    setErrorMsg("Cộng đồng không tồn tại");
                }
                else{
                    setErrorMsg("Không thể tham gia cộng đồng");
                }
            }


        });
        joinCommunityDialogIsClosed.setValue(true);
    }

    private void updateJoinedCommunityList(CreateCommunityViewState newCommunity){
        List<CreateCommunityViewState> list = joinedCommunityList.getValue();
        list.add(newCommunity);
        joinedCommunityList.setValue(list);
    }
    private void updateIsAdminCommunityList(CreateCommunityViewState newCommunity){
        List<CreateCommunityViewState> list = isAdminCommunityList.getValue();
        list.add(newCommunity);
        isAdminCommunityList.setValue(list);
    }
    public void onCancelJoinCommunityButtonClicked(){
        joinCommunityDialogIsClosed.setValue(true);
    }

    private Community mapUIStateToCommunity(CreateCommunityViewState UIState){
        if(UIState == null) return null;
        CommunityBuilder builder = new CommunityConcreteBuilder();
        return builder.setName(UIState.getName())
                .setDepartment(UIState.getCategory())
                .setDescription(UIState.getDescription())
                .build();
    }

    public void fetchJoinedCommunityList(){
        communityRepository.isMember(FirebaseAuth.getInstance().getUid(), new ICommunityCallBack_B() {
            @Override
            public void onRoleMember(List<Community> communityList) {
                joinedCommunityList.setValue(convertToViewStateList(communityList));
            }


        });
    }

    public void fetchIsAdminCommunityList(){
        communityRepository.isAdmin(FirebaseAuth.getInstance().getUid(), new ICommunityCallBack_C() {
            @Override
            public void onRoleAdmin(List<Community> communityList) {
                isAdminCommunityList.setValue(convertToViewStateList(communityList));
            }


        });
    }
    private List<CreateCommunityViewState> convertToViewStateList(List<Community> communities) {
        List<CreateCommunityViewState> viewStates = new ArrayList<>();
        for (Community community : communities) {
            viewStates.add(new CreateCommunityViewState(community.getName(), community.getDescription(), community.getDepartment(), community.getProfileImage(), community.getCommunityId()));
        }
        return viewStates;
    }
}
