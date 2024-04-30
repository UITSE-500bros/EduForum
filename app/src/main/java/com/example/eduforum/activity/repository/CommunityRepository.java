package com.example.eduforum.activity.repository;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eduforum.activity.model.User;
import com.example.eduforum.activity.model.community_manage.Community;
import com.example.eduforum.activity.model.community_manage.CommunityConcreteBuilder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;


public class CommunityRepository {
    protected FirebaseFirestore db;
    List<String> communitiesID;
    List<Community> communities;

    private final ListenerRegistration registration;

    private final FirebaseAuth currentUser;

    public CommunityRepository() {
        db = FirebaseFirestore.getInstance();
        communitiesID = new ArrayList<>();
        communities = new ArrayList<>();
        currentUser = FirebaseAuth.getInstance();

        registration = db.collection("Community")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("TAG", "listen:error", e);
                            return;
                        }
                        assert snapshots != null;
                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                if (db.collection("CommunityMember").document(dc.getDocument().getId()).collection("UserID").document(currentUser.getUid()).get().isSuccessful()) {
                                    Log.d("TAG", "New community: " + dc.getDocument().getData());
                                    Community community = dc.getDocument().toObject(Community.class);
                                    communities.add(community);
                                }
                            }
                        }
                    }
                });
    }
    public void removeListener() {
        registration.remove();
    }
//    @Override
//    protected void onStop() {
//        super.onStop();
//        communityRepo.removeListener();
//    }
    public void createCommunity(Community community) {
        db.collection("Community")
                .add(community)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }
    public void deleteCommunity(Community community) {
        db.collection("Community").document(community.getCommunityId()).delete();
    }
    public void updateCommunity(Community community) {
        db.collection("Community").document(community.getCommunityId()).set(community);
    }

    public void thamGia(String communityJoinId,User user){
        db.collection("Community")
            .whereEqualTo("inviteCode", communityJoinId)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("Data", document.getId() + " => " + document.getData());
                            addingUserIntoCommunityMember(document.getId(), user);
                        }
                    } else {
                        Log.d("Error", "Error getting documents: ", task.getException());
                    }
                }
            });
    }
    public void addingUserIntoCommunityMember(String communityId, User user) {
        db.collection("CommunityMember").document(communityId).collection("UserMember").document().set(user);
    }

    public List<String> getAllStringCommunity(String userID , CommunityCallBack callback) {
        db.collection("CommunityMember").whereEqualTo("userId", userID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                communitiesID.add(document.getString("communityId"));
                            }
                            callback.onCommunitySuccess();
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                            callback.onCommunityFailure("Error getting documents: " + task.getException().getMessage()); // Call the failure method of the callback
                        }
                    }
                });
        return communitiesID;
    }

    public void getAllCommunity(List<String> communityID , CommunityCallBack callback) {
        db.collection("Community").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (communityID.contains(document.getId())) {
                                    communities.add(new CommunityConcreteBuilder().setCommunityId(document.getId())
                                            .setCommunityName(document.getString("communityName"))
                                            .setCreatedDate(String.valueOf(document.getDate("communityDate")))
                                            .setDepartment(document.getString("department"))
                                            .build());
                                }
                            }
                            callback.onCommunitySuccess();
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                            callback.onCommunityFailure("Error getting documents: " + task.getException().getMessage()); // Call the failure method of the callback
                        }
                    }
                });
    }
}