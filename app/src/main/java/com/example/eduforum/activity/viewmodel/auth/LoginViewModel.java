package com.example.eduforum.activity.viewmodel.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.repository.LoginRepository;
import com.example.eduforum.activity.repository.LoginTestRepository;
import com.example.eduforum.activity.ui.auth.LoginViewState;
import com.example.eduforum.activity.util.FlagsList;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<String> emailError;
    private final MutableLiveData<String> passwordError;
    private final MutableLiveData<Boolean> loginSuccess;
    private final LoginRepository loginRepository;
    private final MutableLiveData<LoginViewState> credentials;
    public LoginViewModel() {
        if (FlagsList.APPLICATION_ENVIRONMENT.equals("development")) {
            loginRepository = new LoginTestRepository();
        } else {
            loginRepository = new LoginRepository();
        }
        emailError = new MutableLiveData<>();
        passwordError = new MutableLiveData<>();
        loginSuccess = new MutableLiveData<>();
        credentials = new MutableLiveData<>();
        credentials.setValue(new LoginViewState());
    }

    public LiveData<LoginViewState> getCredentials() {
        return credentials;
    }

    public void setCredentials(LoginViewState viewState) {
        credentials.setValue(viewState);
    }
    public LiveData<String> getEmailError() {
        return emailError;
    }
    public LiveData<String> getPasswordError() {
        return passwordError;
    }
    public LiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }


    public void onLoginClicked(String email, String password) {
        LoginViewState state = credentials.getValue();
        this.loginRepository.login(state.getEmail(), state.getPassword());

        boolean isValid = true;

        if(email==null || email.isEmpty()){
            emailError.setValue("Email is required");
            isValid = false;
            return;
        }
        else {
            emailError.setValue(null);
        }

        if(password==null || password.isEmpty()){
            passwordError.setValue("Password is required");
            isValid = false;
            return;
        }
        else{
            passwordError.setValue(null);
        }
        if (isValid) {
            performlogin(email, password);
        }

    }
    private void performlogin(String email, String password){
       loginSuccess.setValue(true);
    }
}
