package com.example.eduforum.activity.viewmodel.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VerificationWaitingViewModel extends ViewModel {
    private final MutableLiveData<Boolean> backToLogin;

    public VerificationWaitingViewModel() {
        this.backToLogin = new MutableLiveData<>();
    }
    public void onBackToLoginClicked() {
        backToLogin.setValue(true);
    }
    public LiveData<Boolean> getBackToLogin() {
        return backToLogin;
    }
}
