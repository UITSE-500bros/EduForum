package com.example.eduforum.activity.viewmodel.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.repository.LoginRepository;
import com.example.eduforum.activity.repository.LoginTestRepository;
import com.example.eduforum.activity.ui.auth.LoginViewState;
import com.example.eduforum.activity.util.FlagsList;

public class LoginViewModel extends ViewModel {
    private final LoginRepository loginRepository;
    private final MutableLiveData<LoginViewState> credentials;
    public LoginViewModel() {
        if (FlagsList.APPLICATION_ENVIRONMENT.equals("development")) {
            loginRepository = new LoginTestRepository();
        } else {
            loginRepository = new LoginRepository();
        }

        credentials = new MutableLiveData<>();
        credentials.setValue(new LoginViewState());
    }

    public LiveData<LoginViewState> getCredentials() {
        return credentials;
    }

    public void setCredentials(LoginViewState viewState) {
        credentials.setValue(viewState);
    }

    public void onLoginClicked() {
        LoginViewState state = credentials.getValue();
        this.loginRepository.login(state.getEmail(), state.getPassword());
    }
}
