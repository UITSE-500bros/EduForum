package com.example.eduforum.activity.viewmodel.main.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.activity.repository.user.IUpdate;
import com.example.eduforum.activity.repository.user.UserRepository;

public class ProfileUserSettingViewModel extends ViewModel {
    private MutableLiveData<User> userLiveData;
    private MutableLiveData<Boolean> isUpdateSuccess;
    private MutableLiveData<String> errorMessage;
    private UserRepository userRepository;
    public ProfileUserSettingViewModel() {
        userLiveData = new MutableLiveData<>();
        userLiveData.setValue(new User());
        isUpdateSuccess = new MutableLiveData<>();
        isUpdateSuccess.setValue(false);
        errorMessage = new MutableLiveData<>();
        userRepository = UserRepository.getInstance();
    }
    public void setUserLiveData(User user) {
        userLiveData.setValue(user);
    }
    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }
    public LiveData<Boolean> getIsUpdateSuccess() {
        return isUpdateSuccess;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
    public void updateUserProfile(){
        if(!isValidated()) return;

        userRepository.updateProfile(userLiveData.getValue(), new IUpdate() {
            @Override
            public void onUpdateSuccess(User newUser) {
                userLiveData.setValue(newUser);
                isUpdateSuccess.setValue(true);
            }

            @Override
            public void onUpdateFailed(String message) {
                errorMessage.setValue(message);
                isUpdateSuccess.setValue(false);
            }
        });
    }
    private boolean isValidated() {
        User user = userLiveData.getValue();
        if(user == null) return false;
        if(user.getName().isEmpty()) {
            errorMessage.setValue("Tên không được để trống");
            return false;
        }
        if(user.getPhoneNumber().isEmpty()) {
            errorMessage.setValue("Số điện thoại không được để trống");
            return false;
        }
        if(!user.getPhoneNumber().matches("^[0-9]{10}$")){
            errorMessage.setValue("Số điện thoại không hợp lệ");
            return false;

        }
        if(user.getDepartment().isEmpty()) {
            errorMessage.setValue("Khoa không được để trống");
            return false;
        }
        return true;
    }
}
