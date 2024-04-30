package com.example.eduforum.activity.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eduforum.activity.model.community_manage.Community;
import com.example.eduforum.activity.model.community_manage.CommunityConcreteBuilder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseAppLifecycleListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommunityRepository {
    protected FirebaseFirestore db;
    List<String> communitiesID;
    List<Community> communities;

    private ListenerRegistration registration;

    private FirebaseAuth currentUser;

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
                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    if(db.collection("CommunityMember").document(dc.getDocument().getId()).collection("UserID").document(currentUser.getUid()).get().isSuccessful()) {
                                        Log.d("TAG", "New community: " + dc.getDocument().getData());
                                        Community community = dc.getDocument().toObject(Community.class);
                                        communities.add(community);
                                    }
                                    break;
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
        db.collection("Community").add(community).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Log.d("TAG", "DocumentSnapshot added with ID: " + task.getResult());
                } else {
                    Log.w("TAG", "Error adding document", task.getException());
                }
            }
        });

        //addingUserIntoAdmin();
    }
    public void deleteCommunity(Community community) {
        db.collection("Community").document(community.getCommunityId()).delete();
    }
    public void updateCommunity(Community community) {
        db.collection("Community").document(community.getCommunityId()).set(community);
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
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });
        return communitiesID;//callback
    }
    public void thamGia(){
        //TODO
    }
    public void addingUserIntoCommunity(String communityId, String userId) {
        Map<String, Object> data = new HashMap<>();
        data.put("userIds", Arrays.asList(userId));
        db.collection("CommunityMember").document(communityId).collection("UserID").document(userId).set(data);
    }

//    public void addingUserIntoAdmin(String communityId, String userId) {
//        Map<String, Object> data = new HashMap<>();
//        data.put("userIds", Arrays.asList(userId));
//        db.collection("CommunityMember").document(communityId).collection("UserID").document(userId).set(data);
//    }

    public List<Community> getAllCommunity(List<String> communityID , CommunityCallBack callback) {
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
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });
        return communities;//callback
    }

}
