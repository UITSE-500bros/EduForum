package com.example.eduforum.activity.repository.noti;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.eduforum.activity.model.noti_manage.Notification;
import com.example.eduforum.activity.repository.community.NewPost;
import com.example.eduforum.activity.repository.post.PostRepository;
import com.example.eduforum.activity.util.FlagsList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationRepository {
    private static NotificationRepository instance;
    private final FirebaseFirestore db;
    private final FirebaseStorage storage;
    private final FirebaseFunctions mFunctions;
    private final FirebaseAuth currentUser;
    private ListenerRegistration registration;


    public NotificationRepository() {
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance();
        mFunctions = FirebaseFunctions.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public static synchronized NotificationRepository getInstance() {
        if (instance == null) {
            instance = new NotificationRepository();
        }
        return instance;
    }

//    private void getTotalNotification(String userId, INotificationCallback callback) {
//        Query newQuery = db.collection("Notification")
//                .whereEqualTo("userId", currentUser.getUid())
//                .whereEqualTo("isReaf", false);
//        List<String> data = new ArrayList<>();
//
//        newQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
//                    Notification newNotification = document.toObject(Notification.class);
//
//                    data.add(newNotification.getNotificationID());
//                    Log.d(FlagsList.DEBUG_COMMUNITY_FLAG, document.getId() + " => " + document.getData());
//                }
//                callback.onCallback(data);
//            }
//        });
//    }

    public void observeNotification(String userID, INotificationCallback callback) {
        registration = db.collection("User")
                .document(userID)
                .collection("Notification")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.w(FlagsList.DEBUG_NOTIFICATION_FLAG, "Listen failed.", error);
                        callback.onGetRealtimeFailure(error.getMessage());
                        return;
                    }
                    List<Notification> data = new ArrayList<>();
                    for (QueryDocumentSnapshot document : value) {
                        Notification newNotification = document.toObject(Notification.class);
                        newNotification.setNotificationID(document.getId());
                        data.add(newNotification);
                        Log.d(FlagsList.DEBUG_NOTIFICATION_FLAG, document.getId() + " => " + document.getData());
                    }
                    callback.onGetRealtimeSuccess(data);
                });
    }

    public void removeNotificationListener() {
        if (registration != null) {
            registration.remove();
        }
    }


}
