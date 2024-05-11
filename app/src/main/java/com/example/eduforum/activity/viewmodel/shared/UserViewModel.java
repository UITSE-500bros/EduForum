package com.example.eduforum.activity.viewmodel.shared;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.activity.repository.user.IUserCallback;
import com.example.eduforum.activity.repository.user.UserRepository;

public class UserViewModel extends ViewModel {
    private final UserRepository userRepository;
    private final MutableLiveData<User> currentUser;
    public UserViewModel() {
        userRepository = UserRepository.getInstance();
        currentUser = new MutableLiveData<>();
        getCurrentUser();
    }

    public LiveData<User> getCurrentUserLiveData() {
        return currentUser;
    }

    public void setCurrentUserLiveData(User user) {
        currentUser.setValue(user);
    }

    private void getCurrentUser() {
        userRepository.getCurrentUser(new IUserCallback() {
            @Override
            public void onGetUserSuccess(User user) {
                currentUser.setValue(user);
            }
            @Override
            public void onGetUserFailure(String errorCode) {
                currentUser.setValue(null);
            }
        });
    }
}
