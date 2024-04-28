package com.example.eduforum.activity.ui.auth;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eduforum.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout emailInput;
    private TextInputLayout passwordInput;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            return insets;
        });

        emailInput=findViewById(R.id.TIL_Email);
        passwordInput=findViewById(R.id.TIL_Password);
        loginButton=findViewById(R.id.login_btn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Objects.requireNonNull(emailInput.getEditText()).getText().toString().isEmpty()){
                    emailInput.setError("Email is required");
                    return;
                }

                if(Objects.requireNonNull(passwordInput.getEditText()).getText().toString().isEmpty()){
                    passwordInput.setError("Password is required");
                    return;
                }

            }
        });


    }

}