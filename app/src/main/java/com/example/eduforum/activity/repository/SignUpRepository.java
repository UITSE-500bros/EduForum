package com.example.eduforum.activity.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.eduforum.activity.model.User;
import com.example.eduforum.activity.util.FirestoreUtilities;
import com.example.eduforum.activity.util.FlagsList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpRepository implements ISignUpRepository {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

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
                            FirestoreUtilities.writeNewUserToFirestore(user, db, FlagsList.CONNECTION_RETRIES);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(FlagsList.DEBUG_REGISTER_FLAG, "createUserWithEmail:failure", task.getException());

                        }
                    }
                });
    }



}
