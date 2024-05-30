package com.example.eduforum.activity.viewmodel.main.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eduforum.activity.repository.user.IPassword;
import com.example.eduforum.activity.repository.user.UserRepository;

public class NewPassViewModel extends ViewModel {
    UserRepository userRepository;
    MutableLiveData<String> errorMessage;
    MutableLiveData<Boolean> isSuccess;
    public NewPassViewModel(){
        userRepository = UserRepository.getInstance();
        errorMessage = new MutableLiveData<>();
        isSuccess = new MutableLiveData<>();
        isSuccess.setValue(false);
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
    public LiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }
    public void changePassword(String oldPassword, String newPassword, String newPasswordConfirmed) {
        if(!isPasswordValidated(oldPassword, newPassword, newPasswordConfirmed)) return;
        userRepository.changePassword(oldPassword, newPassword, new IPassword() {
            @Override
            public void onPasswordChanged() {
                isSuccess.setValue(true);
            }

            @Override
            public void onPasswordChangeFailed(String message) {
                errorMessage.setValue(message);
                isSuccess.setValue(false);
            }

    });
    }
    private boolean isPasswordValidated(String oldPassword, String newPassword, String newPasswordConfirmed) {
        if(oldPassword.isEmpty() || newPassword.isEmpty() || newPasswordConfirmed.isEmpty()) {
            errorMessage.setValue("Vui lòng điền đầy đủ thông tin");
            return false;
        }
        if(!newPassword.equals(newPasswordConfirmed)) {
            errorMessage.setValue("Mật khẩu mới không khớp");
            return false;
        }

        if(newPassword.length() < 8){
            errorMessage.setValue("Mật khẩu phải có ít nhất 8 ký tự");
            return false;
        }

        boolean flag1 = false, flag2 = false, flag3 = false;
        for(int i = 0; i < newPassword.length(); i++){
            if(Character.isDigit(newPassword.charAt(i))){
                flag1 = true;
            }
            if(Character.isLetter(newPassword.charAt(i))){
                flag2 = true;
            }
            char c = newPassword.charAt(i);
            if(c >= 33 && c <= 46 || c == 64){
                flag3 = true;
            }
        }

        if(!flag1 || !flag2 || !flag3){
            errorMessage.setValue("Mật khẩu mới phải chứa ít nhất 1 chữ cái, 1 số và 1 ký tự đặc biệt");
            return false;
        }

        if(newPassword.equals(oldPassword)){
            errorMessage.setValue("Mật khẩu mới không được trùng với mật khẩu cũ");
            return false;
        }
        return true;
    }
}
