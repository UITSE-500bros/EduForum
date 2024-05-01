package com.example.eduforum.activity.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eduforum.activity.model.community_manage.Community;
import com.example.eduforum.activity.util.FlagsList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;


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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    public void createCommunity(Community community, ICommunityCallBack callBack) {
        List<String> admin = new ArrayList<>();
        admin.add(currentUser.getUid());
        community.setAdminList(admin);
        db.collection("Community")
                .add(community)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(FlagsList.DEBUG_COMMUNITY_FLAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        community.setCommunityId(documentReference.getId());
                        callBack.onCreateCommunitySuccess(documentReference.getId());
                        // cloud function handles the add creator to group
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(FlagsList.DEBUG_COMMUNITY_FLAG, "Error adding document", e);
                        callBack.onCreateCommunityFailure(FlagsList.ERROR_COMMUNITY_FAILED_TO_CREATE);
                    }
                });

    }
    public void deleteCommunity(Community community) {
        db.collection("Community").document(community.getCommunityId()).delete();
    }
    public void updateCommunity(Community community) {
        db.collection("Community").document(community.getCommunityId()).set(community);
    }
    /*
    public void thamGia(String communityJoinId, String userId, ICommunityCallBack callBack){
        db.collection("Community")
            .whereEqualTo("inviteCode", communityJoinId)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            addingUserIntoCommunityMember(document.toObject(Community.class), userId, callBack);
                        }
                    } else {
                        // callback thông báo mã không tồn tại, sai mã
                        Log.w(FlagsList.DEBUG_COMMUNITY_FLAG, task.getException());
                        callBack.onCreateCommunityFailure(FlagsList.ERROR_COMMUNITY_CODE_NOT_EXIST);
                    }
                }
            });

    }
    public void addingUserIntoCommunityMember(Community community, String userId, ICommunityCallBack callBack) {
        Map<String, Object> data = new HashMap<>();
        data.put("communityID", community.getCommunityId());
        data.put("name", community.getName());
        // TODO: xem lai
//        data.put("profilePicture", community.getProfileImage());
        data.put("department", community.getDepartment());


        db.collection("CommunityMember")
                .document(userId)
                .update("communities", FieldValue.arrayUnion(data))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callBack.onCommunitySuccess();
                        Log.d(FlagsList.DEBUG_COMMUNITY_FLAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callBack.onCommunityFailure(FlagsList.ERROR_COMMUNITY_ADD_USER);
                        Log.w(FlagsList.DEBUG_COMMUNITY_FLAG, "Error updating document", e);
                    }
                });
    }
    */

    public void thamGia(String communityJoinId, String userId, ICommunityCallBack_A callBack){
        db.collection("Community")
                .whereEqualTo("inviteCode", communityJoinId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Community community = document.toObject(Community.class);
                                Map<String, Object> data = new HashMap<>();
                                data.put("communityID", community.getCommunityId());
                                data.put("name", community.getName());
                                data.put("department", community.getDepartment());

                                db.collection("CommunityMember")
                                        .document(userId)
                                        .update("communities", FieldValue.arrayUnion(data))
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                callBack.onCommunitySuccess();
                                                Log.d(FlagsList.DEBUG_COMMUNITY_FLAG, "DocumentSnapshot successfully updated!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                callBack.onCommunityFailure(FlagsList.ERROR_COMMUNITY_ADD_USER);
                                                Log.w(FlagsList.DEBUG_COMMUNITY_FLAG, "Error updating document", e);
                                            }
                                        });
                            }
                        } else {
                            // callback thông báo mã không tồn tại, sai mã
                            Log.w(FlagsList.DEBUG_COMMUNITY_FLAG, task.getException());
                            callBack.onCommunityFailure(FlagsList.ERROR_COMMUNITY_CODE_NOT_EXIST);
                        }
                    }
                });
    }


    public void observeDocument(String collectionPath, String documentId, ICommunityChangeListener listener) {
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

    


    public void isMember(String userId, ICommunityCallBack_B callBack){
        List<Community> communities = new ArrayList<>();
        db.collection("CommunityMember")
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Community community = document.toObject(Community.class);
                                communities.add(community);
                                callBack.onRoleMember(communities);
                            }
                        } else {
                            // callback thông báo mã không tồn tại, sai mã
                            Log.w(FlagsList.DEBUG_COMMUNITY_FLAG, task.getException());
                            callBack.onBeingMemberFailure(FlagsList.ERROR_COMMUNITY_CODE_NOT_EXIST);
                        }
                    }
                });
    }


    public void isAdmin(String userId, ICommunityCallBack callBack){
        List<Community> communities = new ArrayList<>();
        db.collection("CommunityMember")
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Community community = document.toObject(Community.class);
                                communities.add(community);
                                callBack.onRoleAdmin(communities);
                            }
                        } else {
                            // callback thông báo mã không tồn tại, sai mã
                            Log.w(FlagsList.DEBUG_COMMUNITY_FLAG, task.getException());
                            callBack.onCreateCommunityFailure(FlagsList.ERROR_COMMUNITY_CODE_NOT_EXIST);
                        }
                    }
                });
    }

}
