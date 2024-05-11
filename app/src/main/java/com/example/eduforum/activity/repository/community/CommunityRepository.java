package com.example.eduforum.activity.repository.community;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eduforum.activity.model.community_manage.Community;
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
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;


public class CommunityRepository {
    private static CommunityRepository instance;
    protected FirebaseFirestore db;
    List<String> communitiesID;
    List<Community> communities;

    private ListenerRegistration registration;

    private final FirebaseAuth currentUser;

    public static synchronized CommunityRepository getInstance() {
        if (instance == null) {
            instance = new CommunityRepository();
        }
        return instance;
    }
    public CommunityRepository() {
        db = FirebaseFirestore.getInstance();
        communitiesID = new ArrayList<>();
        communities = new ArrayList<>();
        currentUser = FirebaseAuth.getInstance();

    }
    // TODO: anh trí nhớ gọi removeListener() khi không cần theo dõi nữa
    public void removeListener() {
        if (registration != null) registration.remove();
    }

    public void createCommunity(Community community, ICommunityCallBack callBack) {
    List<String> admin = new ArrayList<>();
    admin.add(currentUser.getUid());
    community.setAdminList(admin);

    List<String> userList = new ArrayList<>();
    userList.add(currentUser.getUid());
    community.setUserList(userList);

    // Create a new document and get its ID
    DocumentReference newCommunityRef = db.collection("Community").document();
    community.setCommunityId(newCommunityRef.getId());

    newCommunityRef
        .set(community)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(FlagsList.DEBUG_COMMUNITY_FLAG, "DocumentSnapshot written with ID: " + newCommunityRef.getId());
                callBack.onCreateCommunitySuccess(newCommunityRef.getId());
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
                                List<String> userList = community.getUserList();
                                userList.add(userId);
                                db.collection("Community").document(community.getCommunityId())
                                        .update("userList", userList)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                callBack.onJoinCommunitySuccess(FlagsList.ERROR_COMMUNITY_SUCCESS_TO_JOIN);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                callBack.onJoinCommunityFailure(FlagsList.ERROR_COMMUNITY_FAILED_TO_JOIN);
                                            }
                                        });

                            }

                        } else {
                            // callback thông báo mã không tồn tại, sai mã
                            Log.w(FlagsList.DEBUG_COMMUNITY_FLAG, task.getException());
                            callBack.onJoinCommunityFailure(FlagsList.ERROR_COMMUNITY_CODE_NOT_EXIST);
                        }
                    }
                });
    }


    public void observeDocument(String userID, ICommunityChangeListener listener) {
        registration = db.collection("Community")
            .addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot snapshots,@Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w(FlagsList.DEBUG_COMMUNITY_FLAG, "listen:error", e);
                        return;
                    }
                    assert snapshots != null;
//                    for (DocumentChange dc : snapshots.getDocumentChanges()) {
//                        QueryDocumentSnapshot doc = dc.getDocument();
//
//                        Community community = doc.toObject(Community.class);
//                        if (doc.getMetadata().hasPendingWrites() && community.getAdminList().contains(userID)) {
//                            isAdminOf.add(community);
//                        }
//                        else if (dc.getType() == DocumentChange.Type.ADDED) {
//                            if (community.getAdminList().contains(userID)){
//                                isAdminOf.add(community);
//                            }
//                        }
//                        else if (community.getUserList().contains(userID)) {
//                            isMemberOf.add(community);
//                        }
//                    }
                    List<Community> isMemberOf = new ArrayList<>();
                    List<Community> isAdminOf = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : snapshots) {
                        Community community = doc.toObject(Community.class);
                        if (community.getAdminList().contains(userID)) {
                            isAdminOf.add(community);
                        } else if (community.getUserList().contains(userID)) {
                            isMemberOf.add(community);
                        }
                    }
                    listener.onCommunityFetch(isMemberOf);
                    listener.onCreateNewCommunity(isAdminOf);
                }
            });

    }

    public void getCommunity(String communityId, ICommunityCallBack_C callBackC){

        db.collection("Community")
                .document(communityId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                callBackC.getCommunityInfo(document.toObject(Community.class));
                            } else {
                                Log.d(FlagsList.DEBUG_COMMUNITY_FLAG, "No such document");
                            }
                        } else {
                            Log.d(FlagsList.DEBUG_COMMUNITY_FLAG, "get failed with ", task.getException());
                        }
                    }
                });
    }

}
