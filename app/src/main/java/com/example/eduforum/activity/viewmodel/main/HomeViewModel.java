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

    // Fields
    private final MutableLiveData<String> communityCategory;
    private final MutableLiveData<String> errorMsg;

    private final MutableLiveData<Boolean> createCommunityDialogIsClosed;

    private final MutableLiveData<Boolean> joinCommunityDialogIsClosed;
    private final MutableLiveData<CreateCommunityViewState> newCommunityLiveData;

    private final MutableLiveData<JoinCommunityViewState> joinCommunityLiveData;

    private final MutableLiveData<List<CreateCommunityViewState>> joinedCommunityList;
    private final MutableLiveData<List<CreateCommunityViewState>> isAdminCommunityList;

    CommunityRepository communityRepository;

    LoginRepository loginRepository;

    // Methods
    // Constructors- initializations
    public HomeViewModel() {
        communityCategory= new MutableLiveData<>();
        errorMsg = new MutableLiveData<>();
        createCommunityDialogIsClosed = new MutableLiveData<>();
        joinCommunityDialogIsClosed = new MutableLiveData<>();
        newCommunityLiveData = new MutableLiveData<>();
        newCommunityLiveData.setValue(new CreateCommunityViewState());
        if (FlagsList.APPLICATION_ENVIRONMENT.equals("production")) {
            communityRepository = new CommunityRepository();
        } else {
            communityRepository = new CommunityTestRepository();
        }
        loginRepository = LoginRepository.getInstance();

        joinCommunityLiveData = new MutableLiveData<>();
        joinCommunityLiveData.setValue(new JoinCommunityViewState());
        joinedCommunityList = new MutableLiveData<>();
        isAdminCommunityList = new MutableLiveData<>();
        joinedCommunityList.setValue(new ArrayList<>());
        isAdminCommunityList.setValue(new ArrayList<>());
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
    public void setNewCommunityLiveData(CreateCommunityViewState community) {
        newCommunityLiveData.setValue(community);
    }

    public void setCreateCommunityDialogIsClosed(Boolean closed) {
        createCommunityDialogIsClosed.setValue(closed);
    }
    public void setJoinCommunityDialogIsClosed(Boolean closed) {
        joinCommunityDialogIsClosed.setValue(closed);
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

    //--------------------------------------------------------------------------------------
    // Other methods

    // create community usecase
    public void onConfirmCreateCommunityButtonClicked(){
        CreateCommunityViewState commuState = newCommunityLiveData.getValue();
        // validate
        if(commuState.getName() == null || commuState.getName().isEmpty()){
            errorMsg.setValue("Tên cộng đồng không thể trống");
            return;
        }
        commuState.setCategory(communityCategory.getValue());
        if(commuState.getCategory() == null || commuState.getCategory().isEmpty()){
            errorMsg.setValue("Chưa chọn phân loại cho cộng đồng");
            return;
        }
        // create community object (map UI state to domain object)
        Community commu = mapUIStateToCommunity(commuState);
        if(commu==null){
            errorMsg.setValue("Có lỗi xảy ra");
            return;
        }
        // call repository
        communityRepository.createCommunity(commu, new ICommunityCallBack() {
            @Override
            public void onCreateCommunitySuccess(String communityId) {
                fetchIsAdminCommunityList();
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
        // close dialog
        createCommunityDialogIsClosed.setValue(true);
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
    public void fetchIsAdminCommunityList(){
        communityRepository.isAdmin(FirebaseAuth.getInstance().getUid(), new ICommunityCallBack_C() {
            @Override
            public void onRoleAdmin(List<Community> communityList) {
                isAdminCommunityList.setValue(convertToViewStateList(communityList));
            }


        });
    }
    public void onCancelCreateCommunityButtonClicked(){
        // close dialog
        createCommunityDialogIsClosed.setValue(true);
    }

    // --------------------------------
    // join community usecase
    public void onConfirmJoinCommunityButtonClicked(){
        JoinCommunityViewState joinCommuState = joinCommunityLiveData.getValue();
        // validate
        if(joinCommuState.getCommunityId() == null || joinCommuState.getCommunityId().isEmpty()){
            errorMsg.setValue("Mã cộng đồng không thể trống");
            return;
        }
        // call repository, pass the communityID of joinCommuState directly to repository
        communityRepository.thamGia(joinCommuState.getCommunityId(), FirebaseAuth.getInstance().getUid(), new ICommunityCallBack_A() {
            @Override
            public void onJoinCommunitySuccess() {
                fetchJoinedCommunityList();
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
        // close dialog
        joinCommunityDialogIsClosed.setValue(true);
    }
    public void onCancelJoinCommunityButtonClicked(){
        // close dialog
        joinCommunityDialogIsClosed.setValue(true);
    }
    public void fetchJoinedCommunityList(){
        communityRepository.isMember(FirebaseAuth.getInstance().getUid(), new ICommunityCallBack_B() {
            @Override
            public void onRoleMember(List<Community> communityList) {
                joinedCommunityList.setValue(convertToViewStateList(communityList));
            }


        });
    }

    // --------------------------------
    // other methods
    private List<CreateCommunityViewState> convertToViewStateList(List<Community> communities) {
        List<CreateCommunityViewState> viewStates = new ArrayList<>();
        for (Community community : communities) {
            viewStates.add(new CreateCommunityViewState(community.getName(), community.getDescription(), community.getDepartment(), community.getProfileImage(), community.getCommunityId()));
        }
        return viewStates;
    }
}
