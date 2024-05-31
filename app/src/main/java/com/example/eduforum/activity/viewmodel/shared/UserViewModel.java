package com.example.eduforum.activity.viewmodel.shared;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.activity.repository.user.IUpdate;
import com.example.eduforum.activity.repository.user.IUserCallback;
import com.example.eduforum.activity.repository.user.UserRepository;

public class UserViewModel extends ViewModel {
    private final UserRepository userRepository;
    private final MutableLiveData<User> currentUser;
    public UserViewModel() {
        userRepository = UserRepository.getInstance();
        currentUser = new MutableLiveData<>();
    }

    public LiveData<User> getCurrentUserLiveData() {
        return currentUser;
    }

    public void setCurrentUserLiveData(User user) {
        currentUser.setValue(user);
    }
    public void updateProfile(User user) {
        userRepository.updateProfile(user, new IUpdate() {
            @Override
            public void onUpdateSuccess(User newUser) {
                currentUser.setValue(newUser);
            }
            @Override
            public void onUpdateFailed(String message) {
            }
        });
    }

    public void getCurrentUser(IUserCallback callback) {
        userRepository.getCurrentUser(new IUserCallback() {
            @Override
            public void onGetUserSuccess(User user) {
                currentUser.setValue(user);
                callback.onGetUserSuccess(user);
            }
            @Override
            public void onGetUserFailure(String errorCode) {
                currentUser.setValue(null);
                callback.onGetUserFailure(errorCode);
            }
        });
    }
}
