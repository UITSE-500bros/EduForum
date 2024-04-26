package com.example.eduforum.activity.ui.auth;

public class SignUpViewState {
    public String email;
    public String password;
    public String passwordCheck;
    public String fullName;
    public String schoolYear;
    public String gender;
    public String department;

    public SignUpViewState(String email, String password, String passwordCheck, String fullName, String schoolYear, String gender, String department) {
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.fullName = fullName;
        this.schoolYear = schoolYear;
        this.gender = gender;
        this.department = department;
    }
}
