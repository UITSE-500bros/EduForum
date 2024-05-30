package com.example.eduforum.activity.repository.user;


import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.eduforum.activity.model.user_manage.User;
import com.example.eduforum.activity.repository.user.dto.UpdateProfileDTO;
import com.example.eduforum.activity.util.FlagsList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class UserRepository {
    private static UserRepository instance;
    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private final FirebaseFirestore db;
    private final FirebaseStorage storage;
    private final FirebaseAuth mAuth;
    private UserRepository() {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
    }
    public void signOut(ISignOut callback) {
        if (mAuth.getCurrentUser() != null) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("user_" + mAuth.getCurrentUser().getUid())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Log.e(FlagsList.DEBUG_USER_FLAG, "Failed to unsubscribe from user topic.");
                                callback.onFailure();
                                return;
                            }
                            mAuth.signOut();
                            Log.d(FlagsList.DEBUG_USER_FLAG, "Unsubscribed from user topic.");
                            callback.onSuccess();
                        }
                    });
        }
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

    private void uploadProfilePicture(User user, IUpload callback) {
        StorageReference userRef = storage.getReference("User/" + user.getUserId() + "/images");
        Uri fileUri = user.getUploadPicture();
        if (fileUri == null) {
            callback.onUploadSuccess("default");
            return;
        }

        StorageReference userImgRef = userRef.child(UUID.randomUUID().toString());

        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpeg")
                .build();

        UploadTask uploadTask = userImgRef.putFile(fileUri, metadata);

        // Register observers to listen for when the upload is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                callback.onUploadFailed("Network Error! Please try again.");
                Log.w(FlagsList.DEBUG_USER_FLAG, "Image upload failed: ", exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                Log.d(FlagsList.DEBUG_USER_FLAG, "Image uploaded successfully!");
                callback.onUploadSuccess(userImgRef.getPath());
            }
        });

        // delete old image on cloud storage
        StorageReference oldImgRef = storage.getReference(user.getProfilePicture());
        oldImgRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(FlagsList.DEBUG_USER_FLAG, "Old image deleted successfully!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(FlagsList.DEBUG_USER_FLAG, "Error deleting old image", e);
            }
        });
    }

    /**
     * Update user profile
     * @param user User object with updated fields - remember to have the userId field set
     * @param callback Callback to handle success or failure
     */
    public void updateProfile(User user, IUpdate callback) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            uploadProfilePicture(user, new IUpload() {
                @Override
                public void onUploadSuccess(String url) {
                    user.setProfilePicture(url);
                    UpdateProfileDTO updateProfileDTO = new UpdateProfileDTO(user);
                    db.collection("User")
                            .document(user.getUserId())
                            .update(updateProfileDTO.toMap())
                            .addOnSuccessListener(aVoid -> {
                                Log.d(FlagsList.DEBUG_USER_FLAG, "User successfully updated!");
                                callback.onUpdateSuccess(user);
                            }).addOnFailureListener(e -> {
                                Log.w(FlagsList.DEBUG_USER_FLAG, "Error updating user", e);
                                callback.onUpdateFailed("Network Error! Please try again.");
                            });
                }

                @Override
                public void onUploadFailed(String message) {
                    Log.d(FlagsList.DEBUG_USER_FLAG, "Cannot update user, image upload failed: " + message);
                    callback.onUpdateFailed("Network Error! Please try again.");
                }
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
