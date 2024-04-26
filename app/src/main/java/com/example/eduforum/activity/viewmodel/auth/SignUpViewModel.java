package com.example.eduforum.activity.viewmodel.auth;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.model.User;
import com.example.eduforum.activity.repository.ISignUpRepository;
import com.example.eduforum.activity.repository.SignUpRepository;
import com.example.eduforum.activity.repository.TestSignUpRepository;
import com.example.eduforum.activity.ui.auth.SignUpViewState;
import com.example.eduforum.activity.util.FlagsList;

public class SignUpViewModel extends ViewModel {
    private ISignUpRepository signUpRepository;
    private MutableLiveData<SignUpViewState> userLiveData;

    public SignUpViewModel() {
        if (FlagsList.APPLICATION_ENVIRONMENT == "development") {
            signUpRepository = new TestSignUpRepository();
        } else {
            signUpRepository = new SignUpRepository();
        }
        userLiveData = new MutableLiveData<>();
        userLiveData.setValue(new SignUpViewState());
    }

    public MutableLiveData<SignUpViewState> getUser() {
        return userLiveData;
    }

    public void setUser() {

    }

    public void onSignUpClicked() {
        // TODO: Validate user input

        User newUser = mapUIStateToUser(userLiveData.getValue());

        signUpRepository.register(newUser);
    }

    private User mapUIStateToUser(SignUpViewState UIState) {
        if (UIState == null) return null;
        User user = new User();
        user.setEmail(UIState.email);
        user.setPassword(UIState.password);
        user.setName(UIState.fullName);
//        user.setGender(UIState.gender);
//        user.setSchoolYear(UIState.schoolYear);
        user.setDepartment(UIState.department);
        user.setPhoneNumber(UIState.phoneNumber);
        return user;
    }

}
