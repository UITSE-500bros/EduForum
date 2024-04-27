package com.example.eduforum.activity.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.eduforum.activity.model.User;
import com.example.eduforum.activity.util.FlagsList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpRepository {
    protected FirebaseAuth mAuth;
    protected FirebaseFirestore db;

    public SignUpRepository() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public void register(User user) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(FlagsList.DEBUG_REGISTER_FLAG, "createUserWithEmail:success");
                            FirebaseUser fUser = mAuth.getCurrentUser();
                            user.setUserId(mAuth.getUid());
                            // If not development environment then don't send email verification code
                            if (!FlagsList.APPLICATION_ENVIRONMENT.equals("development")) {
                                assert fUser != null;
                                fUser.sendEmailVerification()
                                        .addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()) {
                                                // TODO: send to the UI to notify that email verification has been sent
                                            }
                                        });
                            }

                            writeNewUserToFirestore(user, FlagsList.CONNECTION_RETRIES);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(FlagsList.DEBUG_REGISTER_FLAG, "createUserWithEmail:failure", task.getException());

                        }
                    }
                });
    }

    public void writeNewUserToFirestore(User user, int retries) {
        db.collection("User").document(user.getUserId())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(FlagsList.DEBUG_REGISTER_FLAG, "User successfully written to Firestore!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(FlagsList.DEBUG_REGISTER_FLAG, "Error writing user to Firestore: " + (retries-1) + " retries left, details: ", e);
                        if (retries > 0) {
                            writeNewUserToFirestore(user, retries-1);
                        } else {
                            Log.w(FlagsList.DEBUG_REGISTER_FLAG, "Cannot write user to Firestore, deleting user");
                            deleteUser();
                        }
                    }
                });
    }

    // TODO: xu ly truong hop deleteUser fail

    public void deleteUser() {
        FirebaseUser user = mAuth.getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(FlagsList.DEBUG_REGISTER_FLAG, "User account deleted.");
                        }
                    }
                });
    }



}
