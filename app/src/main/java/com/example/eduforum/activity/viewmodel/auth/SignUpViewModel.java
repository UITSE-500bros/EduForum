package com.example.eduforum.activity.viewmodel.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.activity.repository.auth.ISignUpCallback;
import com.example.eduforum.activity.repository.auth.SignUpRepository;
import com.example.eduforum.activity.ui.auth.SignUpViewState;
import com.example.eduforum.activity.util.FlagsList;

import java.time.LocalDate;

public class SignUpViewModel extends ViewModel {
    private final SignUpRepository signUpRepository;
    private final MutableLiveData<SignUpViewState> userLiveData;
    private final MutableLiveData<String> selectedDepartment;
    private final MutableLiveData<String> selectedGender;
    private final MutableLiveData<String> errorMessage;
    private final MutableLiveData<Boolean> navigateToEmailVerification;

//    private final MutableLiveData<Boolean> errorLiveData;


    public SignUpViewModel() {
        // environment-repository
        signUpRepository = SignUpRepository.getInstance();


        // initialize livedata
        userLiveData = new MutableLiveData<>();
        userLiveData.setValue(new SignUpViewState());
        selectedDepartment = new MutableLiveData<>();
//        errorLiveData = new MutableLiveData<>();
        selectedGender = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
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

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void setUser(SignUpViewState state) {
        userLiveData.setValue(state);
    }



    public void onSignUpClicked() {
        if(!isSignUpDataValid()) return;

        User newUser = mapUIStateToUser(userLiveData.getValue());

        signUpRepository.register(newUser, new ISignUpCallback() {
            @Override
            public void onSignUpSuccess() {
                navigateToEmailVerification.postValue(true);
            }

            @Override
            public void onSignUpFailure(String errorMsg) {
                if(errorMsg.equals(FlagsList.ERROR_REGISTER_EMAIL_EXISTED)){
                    errorMessage.postValue("Email đã tồn tại");
                } else {
                    errorMessage.postValue("Đã có lỗi xảy ra");
                }
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
        user.setProfilePicture(String.valueOf(UIState.profilePicture));
        return user;
    }

    public boolean isSignUpDataValid() {
        SignUpViewState state = userLiveData.getValue();
        state.gender = selectedGender.getValue();
        state.department = selectedDepartment.getValue();
        if (state == null) return false;
        //check email
        if(state.email.isEmpty() || !state.email.matches("^[0-9]{8}@gm\\.uit\\.edu\\.vn$")){
            errorMessage.setValue("Email không hợp lệ");
            return false;
        }
        //check full name
        if(state.fullName.isEmpty()){
            errorMessage.setValue("Họ tên không được để trống");
            return false;
        }
        //check password
        if(state.password.isEmpty() || state.password.length() < 8){
            errorMessage.setValue("Mật khẩu phải có ít nhất 8 ký tự");
            return false;
        }

        boolean flag1 = false, flag2 = false, flag3 = false;
        for(int i = 0; i < state.password.length(); i++){
            if(Character.isDigit(state.password.charAt(i))){
                flag1 = true;
            }
            if(Character.isLetter(state.password.charAt(i))){
                flag2 = true;
            }
            char c = state.password.charAt(i);
            if(c >= 33 && c <= 46 || c == 64){
                flag3 = true;
            }
        }

        if(!flag1 || !flag2 || !flag3){
            errorMessage.setValue("Mật khẩu phải chứa ít nhất 1 chữ cái, 1 số và 1 ký tự đặc biệt");
            return false;
        }
        if(state.passwordCheck.isEmpty() || !state.passwordCheck.equals(state.password)){
            errorMessage.setValue("Mật khẩu không khớp");
            return false;
        }
        //check phone number
        if(state.phoneNumber.isEmpty() || !state.phoneNumber.matches("^[0-9]{10}$")){
            errorMessage.setValue("Số điện thoại không hợp lệ");
            return false;
        }
        //check school year
        int year = Integer.parseInt(state.schoolYear);
        if(state.schoolYear.isEmpty() || year > LocalDate.now().getYear()){
            errorMessage.setValue("Năm học không hợp lệ");
            return false;
        }
        //check department
        if(state.department.isEmpty()) {
            errorMessage.setValue("Chưa chọn khoa");
            return false;
        }
        //check gender
        if(state.gender.isEmpty()) {
            errorMessage.setValue("Chưa chọn giới tính");
            return false;
        }

        if (state.profilePicture == null) {
            errorMessage.setValue("Chưa chọn ảnh đại diện");
            return false;
        }
        return true;
    }
}
