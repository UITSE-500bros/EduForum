package com.example.eduforum.activity.ui.auth;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.eduforum.R;
import com.example.eduforum.activity.EduForum;
import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.activity.repository.user.IUserCallback;
import com.example.eduforum.activity.ui.main.MainActivity;
import com.example.eduforum.activity.ui.welcome.WelcomeActivity;
import com.example.eduforum.activity.util.FlagsList;
import com.example.eduforum.activity.viewmodel.auth.LoginViewModel;
import com.example.eduforum.activity.viewmodel.shared.UserViewModel;
import com.example.eduforum.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;
    private UserViewModel userViewModel;
    private ActivityLoginBinding binding;
    private SharedPreferences prefs = null;

    // Declare the launcher at the top of your Activity/Fragment:
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                    Snackbar.make(binding.getRoot(), "Bạn sẽ nhận được các thông báo mới nhất từ cộng đồng!", Snackbar.LENGTH_SHORT).show();
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                    Snackbar.make(binding.getRoot(), "Ứng dụng sẽ không hiển thị thông báo", Snackbar.LENGTH_SHORT).show();

                }
            });

    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
//                Snackbar.make(binding.getRoot(), "Ứng dụng sẽ hiển thị thông báo", Snackbar.LENGTH_SHORT).show();

            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
//        else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
//              display an educational UI explaining to the user the features that will be enabled
//            //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
//            //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
//            //       If the user selects "No thanks," allow the user to continue without notifications.
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.getSignedInUser().observe(this, this::updateUI);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        EduForum app = (EduForum) getApplication();
        userViewModel = app.getSharedViewModel(UserViewModel.class);

        prefs = getSharedPreferences("com.example.eduforum", MODE_PRIVATE);
        if (prefs.getBoolean("firstRun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            Intent i = new Intent(this, WelcomeActivity.class);
            startActivity(i);
            prefs.edit().putBoolean("firstRun", false).apply();
            finish();

        }


        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        askNotificationPermission();

//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w("TEST", "Fetching FCM registration token failed", task.getException());
//                            return;
//                        }
//
//                        // Get new FCM registration token
//                        String token = task.getResult();
//
//                        // Log and toast
////                        String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d("TEST", token);
//                        Toast.makeText(LoginActivity.this, "OK", Toast.LENGTH_SHORT).show();
//                    }
//                });

        // navigate to sign up
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), SignUpActivity.class);
                startActivity(i);
            }
        });

        // navigate to forgot password
        binding.btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ForgotPassActivity.class);
                startActivity(i);
            }
        });

        TextInputLayout emailInput = binding.TILEmail;
        TextInputLayout passwordInput = binding.TILPassword;

        viewModel.getEmailError().observe(this, emailError -> {
            if(emailError != null){
                emailInput.setError(emailError);
            }
        });

        viewModel.getPasswordError().observe(this, passwordError -> {
            if(passwordError != null){
                passwordInput.setError(passwordError);
            }
        });

        viewModel.getIsEmailVerified().observe(this, isEmailVerified -> {
            if(isEmailVerified){
                Intent intent = new Intent(this, MainActivity.class);
                userViewModel.getCurrentUser(new IUserCallback() {
                    @Override
                    public void onGetUserSuccess(User user) {
                        FirebaseMessaging.getInstance().subscribeToTopic("user_" + user.getUserId())
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Snackbar.make(binding.getRoot(), "Đã có lỗi xảy ra, vui lòng chờ giây lát!", Snackbar.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    @Override
                    public void onGetUserFailure(String errorMsg) {
                        Snackbar.make(binding.getRoot(), "Đã có lỗi xảy ra, vui lòng chờ giây lát!", Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });

        viewModel.getLoginErrorMsg().observe(this, msg -> {
            Snackbar.make(binding.getRoot(), msg, Snackbar.LENGTH_SHORT).show();
        });


    }

    private void updateUI(FirebaseUser user) {
        if (user != null && user.isEmailVerified()) {
//            Log.d("TEST", user.getEmail());
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}