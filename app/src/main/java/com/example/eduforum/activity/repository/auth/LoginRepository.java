package com.example.eduforum.activity.repository.auth;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.eduforum.activity.util.FlagsList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class LoginRepository {
    private static LoginRepository instance;

    protected FirebaseAuth mAuth;
    protected FirebaseFirestore db;
    protected FirebaseStorage storage;

    public LoginRepository() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        configFirebaseEmulator();
    }

    public static synchronized LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
        }
        return instance;
    }

    public void login(String email, String password, ILoginCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(FlagsList.DEBUG_LOGIN_FLAG, "signInWithEmail:success");
                            FirebaseUser mUser = mAuth.getCurrentUser();
                            assert mUser != null;
                            if (mUser.isEmailVerified()) {
                                // Navigation to community
                                callback.onLoginSuccess();
                            } else {
                                Log.w(FlagsList.DEBUG_LOGIN_FLAG, "signInWithEmail:success but email is not verified");
                                callback.onLoginFailed(FlagsList.ERROR_LOGIN_EMAIL_NOT_VERIFIED);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(FlagsList.DEBUG_LOGIN_FLAG, "signInWithEmail:failure", task.getException());
                            callback.onLoginFailed(FlagsList.ERROR_LOGIN_WRONG_CREDENTIALS);
                        }
                    }
                });
    }

    public void sendResetPasswordEmail(String email, ILoginCallback callback) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(FlagsList.DEBUG_LOGIN_FLAG, "Reset password email sent.");
                            callback.onLoginSuccess();
                        } else {
                            Log.w(FlagsList.DEBUG_LOGIN_FLAG, "Reset password email error.", task.getException());
                            callback.onLoginFailed("error");
                        }
                    }
                });
    }

    private void configFirebaseEmulator() {
        mAuth.useEmulator("10.0.2.2", 9099);
        db.useEmulator("10.0.2.2", 8080);
        storage.useEmulator("10.0.2.2", 9199);
    }

}
