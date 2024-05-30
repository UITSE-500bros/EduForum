package com.example.eduforum.activity.viewmodel.main.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.repository.user.ISignOut;
import com.example.eduforum.activity.repository.user.UserRepository;

public class SettingsMainViewModel extends ViewModel {
    UserRepository userRepository;
    MutableLiveData<String> errorMessage;
    MutableLiveData<Boolean> isSignOutSuccess;
    public SettingsMainViewModel(){
        userRepository = UserRepository.getInstance();
        errorMessage = new MutableLiveData<>();
        isSignOutSuccess = new MutableLiveData<>();
        isSignOutSuccess.setValue(false);
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
    public LiveData<Boolean> getIsSignOutSuccess() {
        return isSignOutSuccess;
    }
    public void signOut() {
       userRepository.signOut(new ISignOut() {
           @Override
           public void onSuccess() {
                isSignOutSuccess.setValue(true);
           }

           @Override
           public void onFailure() {
                errorMessage.setValue("Đăng xuất thất bại");
                isSignOutSuccess.setValue(false);
           }
       });
    }
}
