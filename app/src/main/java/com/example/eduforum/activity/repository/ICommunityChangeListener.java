package com.example.eduforum.activity.repository;

import com.example.eduforum.activity.model.community_manage.Community;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

public interface ICommunityChangeListener {
    void onCommunityFetch(List<Community> communities);

    void onCommunityChange(Community community);

    void onCreateNewCommunity(List<Community> communities);

}
