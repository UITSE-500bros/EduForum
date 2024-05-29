package com.example.eduforum.activity.repository.user;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.activity.repository.user.dto.UpdateProfileDTO;
import com.example.eduforum.activity.util.FlagsList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRepository {
    private static UserRepository instance;
    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private final FirebaseFirestore db;
    private final FirebaseAuth mAuth;
    private UserRepository() {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }
    public void signOut() {
        mAuth.signOut();
    }
//    public void changePassword() {
//        FirebaseUser user = mAuth.getCurrentUser();
//        if (user != null) {
//            mAuth.sendPasswordResetEmail(user.getEmail());
//        }
//    }

    public void changePassword(String oldPassword, String newPassword, IPassword callback) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            callback.onPasswordChangeFailed("Network error. Please try again.");
            return;
        }
        String email = user.getEmail();
        assert email != null;
        AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(FlagsList.DEBUG_USER_FLAG, "User re-authenticated.");
                            // Proceed with password change
                            user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(FlagsList.DEBUG_USER_FLAG, "Password updated successfully.");
                                        callback.onPasswordChanged();
                                    } else {
                                        Log.e(FlagsList.DEBUG_USER_FLAG, "Failed to update password.");
                                        callback.onPasswordChangeFailed("Network error. Please try again.");
                                    }
                                }
                            });
                        } else {
                            Log.e(FlagsList.DEBUG_USER_FLAG, "Re-authentication failed.");
                            callback.onPasswordChangeFailed("Incorrect old password.");
                        }
                    }
                });
    }

    public void updateProfile(User user, IUserCallback callback) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            UpdateProfileDTO updateProfileDTO = new UpdateProfileDTO(user);
            db.collection("User")
                    .document(user.getUserId())
                    .update(updateProfileDTO.toMap())
                    .addOnSuccessListener(aVoid -> {
                        Log.d(FlagsList.DEBUG_USER_FLAG, "DocumentSnapshot successfully updated!");
//                        callback.onUpdateUserSuccess();
                    });

        }
    }

    public void getCurrentUser(IUserCallback callback) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            DocumentReference docRef = db.collection("User").document(currentUser.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(FlagsList.DEBUG_USER_FLAG, "Current user data: " + document.getData());
                            User user = document.toObject(User.class);
                            assert user != null;
                            user.setUserId(document.getId());
                            callback.onGetUserSuccess(user);
                        } else {
                            Log.d(FlagsList.DEBUG_USER_FLAG, "No such user exists!");
                            callback.onGetUserFailure(FlagsList.ERROR_USER_NOT_FOUND);
                        }
                    } else {
                        Log.d(FlagsList.DEBUG_USER_FLAG, "Cannot get user, get failed with ", task.getException());
                        callback.onGetUserFailure(FlagsList.ERROR_USER);
                    }
                }
            });
        }
    }

}
