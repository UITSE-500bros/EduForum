package com.example.eduforum.activity.viewmodel.main;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.net.Uri;

public class CreateCommunityViewModel extends ViewModel {
    private final MutableLiveData<String> communityName;
    private final MutableLiveData<String> communityDescription;
    private final MutableLiveData<Uri> communityImage;
    private final MutableLiveData<String> communityCategory;
    private final MutableLiveData<String> errorMsg;

    private final MutableLiveData<Boolean> createNewCommunity;

    private final MutableLiveData<Boolean> closeDialog;
    private final String commuNameString;


    // MutableLiveData<CommunityRepository> ....

    public CreateCommunityViewModel() {
        communityName = new MutableLiveData<>();
        communityDescription = new MutableLiveData<>();
        communityImage = new MutableLiveData<>();
        communityCategory= new MutableLiveData<>();
        errorMsg = new MutableLiveData<>();
        createNewCommunity = new MutableLiveData<>();
        closeDialog = new MutableLiveData<>();
    }

    public LiveData<String> getCommunityName() {
        return communityName;
    }

    public LiveData<String> getCommunityDescription() {
        return communityDescription;
    }

    public LiveData<Uri> getCommunityImage() {
        return communityImage;
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
    public void setCommunityName(String name) {
        communityName.setValue(name);
    }
    public void setCommunityDescription(String description) {
        communityDescription.setValue(description);
    }
    public void setCommunityImage(Uri image) {
        communityImage.setValue(image);
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

    public void setCloseDialog(Boolean closed) {
        closeDialog.setValue(closed);
    }
    public void onCreateCommunityButtonClicked(){
        if(communityName.getValue() == null || communityName.getValue().isEmpty()){
            errorMsg.setValue("Tên cộng đồng không thể trống");
            return;
        }
        if(communityCategory.getValue() == null || communityCategory.getValue().isEmpty()){
            errorMsg.setValue("Chưa chọn phân loại cho cộng đồng");
            return;
        }
        // Community commu = new Community(communityName.getValue(), communityDescription.getValue(), communityCategory.getValue(), communityImage.getValue());
        // commuRepository.createNewCommunity(commu)...

        closeDialog.setValue(true);
        createNewCommunity.setValue(true);
    }


    public void onCancelButtonClicked(){
        closeDialog.setValue(true);
    }

}
