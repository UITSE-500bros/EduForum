package com.example.eduforum.activity.repository;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eduforum.activity.model.User;
import com.example.eduforum.activity.model.community_manage.Community;
import com.example.eduforum.activity.model.community_manage.CommunityConcreteBuilder;
import com.example.eduforum.activity.util.FlagsList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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

    private ListenerRegistration registration;

    private final FirebaseAuth currentUser;

    public CommunityRepository() {
        db = FirebaseFirestore.getInstance();
        communitiesID = new ArrayList<>();
        communities = new ArrayList<>();
        currentUser = FirebaseAuth.getInstance();


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
                        Log.d(FlagsList.DEBUG_COMMUNITY_FLAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        //callback
                        community.setCommunityId(documentReference.getId());
                        //add user to community
                        String id = currentUser.getUid();

                        addingUserIntoCommunityMember(community, id);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(FlagsList.DEBUG_COMMUNITY_FLAG, "Error adding document", e);
                        //callback
                    }
                });


    }
    public void deleteCommunity(Community community) {
        db.collection("Community").document(community.getCommunityId()).delete();
    }
    public void updateCommunity(Community community) {
        db.collection("Community").document(community.getCommunityId()).set(community);
    }

    public void thamGia(String communityJoinId ,String userId){
        db.collection("Community")
            .whereEqualTo("inviteCode", communityJoinId)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            addingUserIntoCommunityMember(document.toObject(Community.class), userId);
                        }
                    } else {
                        //callback thông báo mã không tồn tại, sai mã
                        Log.w(FlagsList.DEBUG_COMMUNITY_FLAG, task.getException());
                    }
                }
            });

    }// need callback
    public void addingUserIntoCommunityMember(Community communityId,String userId) {
        //db.collection("CommunityMember").document(userId).document("Community").set(communityId);
        //callback thông báo tham gia thành công
    }

    public void observeDocument(String collectionPath, String documentId, CommunityChangeListener listener) {
        registration = db.collection("CommunityMember")
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
                                if (db.collection("CommunityMember")
                                        .document(dc.getDocument().getId())
                                        .collection("User")
                                        .document()
                                        .get().isSuccessful()) {
                                    Log.w(FlagsList.DEBUG_COMMUNITY_FLAG, "User already in community");
                                    Community community = dc.getDocument().toObject(Community.class);
                                    communities.add(community);
                                }
                            }
                        }
                    }
                });
    }

}
