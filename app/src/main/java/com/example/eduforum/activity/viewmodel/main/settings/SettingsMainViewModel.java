package com.example.eduforum.activity.viewmodel.main.settings;

import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.repository.user.UserRepository;

public class SettingsMainViewModel extends ViewModel {
    UserRepository userRepository;
    public SettingsMainViewModel(){
        userRepository = UserRepository.getInstance();
    }
    public void signOut() {
//        userRepository.signOut();
    }
}
