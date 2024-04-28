package com.example.eduforum.activity.viewmodel.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.model.Topic;
import com.example.eduforum.activity.model.User;
import com.example.eduforum.activity.repository.ISignUpCallback;
import com.example.eduforum.activity.repository.SignUpRepository;
import com.example.eduforum.activity.repository.SignUpTestRepository;
import com.example.eduforum.activity.ui.auth.SignUpViewState;
import com.example.eduforum.activity.util.FlagsList;

import org.checkerframework.checker.guieffect.qual.UI;

import java.util.List;

public class SignUpViewModel extends ViewModel {
    private final SignUpRepository signUpRepository;
    private final MutableLiveData<SignUpViewState> userLiveData;
    private final MutableLiveData<String> selectedDepartment;
    private final MutableLiveData<String> selectedGender;
    private final MutableLiveData<Boolean> navigateToEmailVerification;

//    private final MutableLiveData<Boolean> errorLiveData;


    public SignUpViewModel() {
        // environment-repository
        if (FlagsList.APPLICATION_ENVIRONMENT.equals("development")) {
            signUpRepository = new SignUpTestRepository();
        } else {
            signUpRepository = new SignUpRepository();
        }

        // initialize livedata
        userLiveData = new MutableLiveData<>();
        userLiveData.setValue(new SignUpViewState());
        selectedDepartment = new MutableLiveData<>();
//        errorLiveData = new MutableLiveData<>();
        selectedGender = new MutableLiveData<>();
        navigateToEmailVerification = new MutableLiveData<>();
    }

    // navigation
    public LiveData<Boolean> getNavigateToEmailVerification() {
        return navigateToEmailVerification;
    }

    // gender

    public LiveData<String> getSelectedGender() {
        return selectedGender;
    }

    public void setSelectedGender(String gender) {
        selectedGender.setValue(gender);
    }

    // department

    public LiveData<String> getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedDepartment(String department) {
        selectedDepartment.setValue(department);
    }

//    public LiveData<List<Topic>> getDepartmentList() {
//        MutableLiveData<List<Topic>> departments = new MutableLiveData<>();
//        topicRepository.getAllDepartments(new TopicCallback() {
//            @Override
//            public void onTopicLoaded(List<Topic> topics) {
//                departments.setValue(topics);
//            }
//
//            @Override
//            public void onError(Exception e) {
//                errorLiveData.setValue(true);
//            }
//        });
//        return departments;
//    }


    public LiveData<SignUpViewState> getUser() {
        return userLiveData;
    }

    public void setUser(SignUpViewState state) {
        userLiveData.setValue(state);
    }

    public void onSignUpClicked() {
        // TODO: Validate user input

        User newUser = mapUIStateToUser(userLiveData.getValue());

        signUpRepository.register(newUser, new ISignUpCallback() {
            @Override
            public void onSignUpSuccess() {
                navigateToEmailVerification.postValue(true);
            }

            @Override
            public void onSignUpFailure() {
                // Handle sign-up failure, e.g., show an error message
            }
        });
    }

    private User mapUIStateToUser(SignUpViewState UIState) {
        if (UIState == null) return null;
        UIState.gender = selectedGender.getValue();
        UIState.department = selectedDepartment.getValue();
        User user = new User();
        user.setEmail(UIState.email);
        user.setPassword(UIState.password);
        user.setName(UIState.fullName);
        user.setGender(UIState.gender);
        user.setSchoolYear(UIState.schoolYear);
        user.setDepartment(UIState.department);
        user.setPhoneNumber(UIState.phoneNumber);
        return user;
    }

}
