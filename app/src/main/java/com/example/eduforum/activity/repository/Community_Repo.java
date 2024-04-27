package com.example.eduforum.activity.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.eduforum.activity.model.community_manage.Community;
import com.example.eduforum.activity.model.community_manage.CommunityConcreteBuilder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Community_Repo {
    protected FirebaseFirestore db;


    public Community_Repo() {
        db = FirebaseFirestore.getInstance();
    }

    public void createCommunity(String communityId, String communityName, String createdDate, String department) {
        CommunityConcreteBuilder communityConcreteBuilder = new CommunityConcreteBuilder();
        communityConcreteBuilder.setCommunityId(communityId);
        communityConcreteBuilder.setCommunityName(communityName);
        communityConcreteBuilder.setCreatedDate(createdDate);
        communityConcreteBuilder.setDepartment(department);
        Community community = communityConcreteBuilder.build();
        db.collection("Community").document(communityId).set(community);
    }
    public void deleteCommunity(String communityId) {
        db.collection("Community").document(communityId).delete();
    }
    public void updateCommunity(String communityId, String communityName, String createdDate, String department) {
        CommunityConcreteBuilder communityConcreteBuilder = (CommunityConcreteBuilder) new CommunityConcreteBuilder()
                                                                                        .setCommunityName(communityName)
                                                                                        .setCreatedDate(createdDate)
                                                                                        .setDepartment(department);
        Community community = communityConcreteBuilder.build();
        db.collection("Community").document(communityId).set(community);
    }
    public List<Community> getAllCommunity() {
        List<Community> communities = new ArrayList<>();
        db.collection("Community")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                communities.add(document.toObject(Community.class));
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
        return communities;
    }
}
