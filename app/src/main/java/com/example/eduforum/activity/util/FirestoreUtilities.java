package com.example.eduforum.activity.util;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.eduforum.activity.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreUtilities {
    public static void writeNewUserToFirestore(User user, FirebaseFirestore db, int retries) {
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
                            writeNewUserToFirestore(user, db, retries-1);
                        } else {
                            Log.w(FlagsList.DEBUG_REGISTER_FLAG, "Cannot write user to Firestore, deleting user");
                            deleteUser(user, db);
                        }
                    }
                });
    }

    // TODO: xu ly truong hop deleteUser fail

    public static void deleteUser(User user, FirebaseFirestore db) {
        db.collection("User").document(user.getUserId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(FlagsList.DEBUG_REGISTER_FLAG, "User successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(FlagsList.DEBUG_REGISTER_FLAG, "Error deleting user", e);
                    }
                });
    }
}
