package com.example.eduforum.activity.viewmodel.auth;

import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.repository.ISignUpRepository;
import com.example.eduforum.activity.repository.SignUpRepository;
import com.example.eduforum.activity.repository.TestSignUpRepository;
import com.example.eduforum.activity.util.FlagsList;

public class SignUpViewModel extends ViewModel {
    private ISignUpRepository signUpRepository;


    public SignUpViewModel() {
        if (FlagsList.APPLICATION_ENVIRONMENT == "development") {
            signUpRepository = new TestSignUpRepository();
        } else {
            signUpRepository = new SignUpRepository();
        }
    }
}
