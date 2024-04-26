package com.example.eduforum.activity.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.eduforum.activity.model.User;
import com.example.eduforum.activity.util.FirestoreUtilities;
import com.example.eduforum.activity.util.FlagsList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class TestSignUpRepository implements ISignUpRepository {
    private final int RETRIES = 3;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public TestSignUpRepository() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.useEmulator("10.0.2.2", 9099);
        db = FirebaseFirestore.getInstance();
        db.useEmulator("10.0.2.2", 8080);

    }

    public void register(User user) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(FlagsList.DEBUG_REGISTER_FLAG, "createUserWithEmail:success");
                            user.setUserId(mAuth.getUid());
                            FirestoreUtilities.writeNewUserToFirestore(user, db, FlagsList.CONNECTION_RETRIES);                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(FlagsList.DEBUG_REGISTER_FLAG, "createUserWithEmail:failure", task.getException());

                        }
                    }
                });
    }



}
