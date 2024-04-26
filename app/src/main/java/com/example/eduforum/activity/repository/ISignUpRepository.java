package com.example.eduforum.activity.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.eduforum.activity.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public interface ISignUpRepository {
    void register(User user);


}
