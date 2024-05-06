package com.example.eduforum.activity.repository;

import com.example.eduforum.activity.model.community_manage.Community;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public interface ICommunityChangeListener {
    void onDocumentChange(DocumentSnapshot snapshot);
    void onError(Exception exception);
    void onDocumentNotFound();

    void onCommunityChange(List<Community> communities);
}
