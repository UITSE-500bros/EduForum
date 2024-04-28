package com.example.eduforum.activity.ui.auth;

public class LoginViewState {
    public String email;
    public String password;

    public LoginViewState() {}

    public LoginViewState(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
