package com.example.eduforum.activity.repository.auth;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.activity.repository.auth.dto.RegisterDTO;
import com.example.eduforum.activity.util.FlagsList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class SignUpRepository {
    private static SignUpRepository instance;
    protected FirebaseAuth mAuth;
    protected FirebaseFirestore db;
    protected FirebaseStorage storage;

    public SignUpRepository() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public static synchronized SignUpRepository getInstance() {
        if (instance == null) {
            instance = new SignUpRepository();
        }
        return instance;
    }

    public void register(User user, ISignUpCallback callback) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(FlagsList.DEBUG_REGISTER_FLAG, "createUserWithEmail:success");
                            FirebaseUser fUser = mAuth.getCurrentUser();
                            user.setUserId(mAuth.getUid());
                            assert fUser != null;
                            fUser.sendEmailVerification()
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            uploadProfilePicture(user);
                                            writeNewUserToFirestore(user, FlagsList.CONNECTION_RETRIES, callback);
                                        }
                                    });
//                            uploadProfilePicture(user);
//                            writeNewUserToFirestore(user, FlagsList.CONNECTION_RETRIES);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(FlagsList.DEBUG_REGISTER_FLAG, "createUserWithEmail:failure", task.getException());
                            String errorMessage = FlagsList.ERROR_REGISTER;
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                errorMessage = FlagsList.ERROR_REGISTER_EMAIL_EXISTED;
                            }
                            callback.onSignUpFailure(errorMessage);
                        }
                    }
                });
    }

    public void uploadProfilePicture(User user) {
        StorageReference usersRef = storage.getReference("User");
        Uri fileUri = Uri.parse(user.getProfilePicture());
        StorageReference userImgRef = usersRef.child(user.getUserId()+"/images/"+ UUID.randomUUID().toString());
        user.setProfilePicture(userImgRef.getPath());
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpeg")
                .build();

        UploadTask uploadTask = userImgRef.putFile(fileUri, metadata);

        // Register observers to listen for when the upload is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.w(FlagsList.DEBUG_REGISTER_FLAG, "Image upload failed: ", exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                Log.d(FlagsList.DEBUG_REGISTER_FLAG, "Image uploaded successfully!");
            }
        });
    }

    protected void writeNewUserToFirestore(User user, int retries, ISignUpCallback callback) {
        RegisterDTO regDTO = new RegisterDTO(user);
        db.collection("User").document(user.getUserId())
                .set(regDTO)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(FlagsList.DEBUG_REGISTER_FLAG, "User successfully written to Firestore!");
                        callback.onSignUpSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(FlagsList.DEBUG_REGISTER_FLAG, "Error writing user to Firestore: " + (retries-1) + " retries left, details: ", e);
                        if (retries > 0) {
                            writeNewUserToFirestore(user, retries-1, callback);
                        } else {
                            Log.w(FlagsList.DEBUG_REGISTER_FLAG, "Cannot write user to Firestore, deleting user");
                            callback.onSignUpFailure(FlagsList.ERROR_REGISTER);
                            deleteUser();
                        }
                    }
                });
    }

    // TODO: xu ly truong hop deleteUser fail

    protected void deleteUser() {
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
